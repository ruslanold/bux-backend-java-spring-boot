package com.project.myproject.service;

import com.project.myproject.dao.PasswordResetTokenRepository;
import com.project.myproject.dao.RoleRepository;
import com.project.myproject.dao.UserRepository;
import com.project.myproject.dto.*;
import com.project.myproject.entity.*;
import com.project.myproject.enums.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService{

    @Value("${defaultCommissionPercent}")
    private int defaultPercent;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IStorageService storageService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUser(long id) {
        return convertToUserDto(getUserById(id));
    }

    @Override
    public User getUserById(long id) {
        if (!userRepository.existsById(id)){
            throw new BadRequestException("No user with such id: " + id);
        }
        return userRepository.getOne(id);
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if(user == null){
            throw new BadRequestException("No user with such name: " + username);
        }
        return user;
    }



    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new BadRequestException("No user with such email: " + email);
        }
        return user;
    }

    @Override
    public UserAccountDto getUserAccountByUsername(String username) {
        User user = getUserByUsername(username);
        return convertToUserAccountDto(user);
    }


    @Override
    public UserDto createUser(UserRegister userRegister) {
        if(userRepository.existsByEmail(userRegister.getEmail())){
            throw new BadRequestException("There is an account with that email adress: " + userRegister.getEmail());
        }

        User user = new User();
        user.setUsername(userRegister.getUsername());
        user.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        user.setEmail(userRegister.getEmail());
        user.setRoles(Arrays.asList(roleRepository.findByName(ERole.USER)));

        if(userRegister.getReferrerId() != null){
            User referrer = userRepository.getOne(userRegister.getReferrerId());
            user.setReferrer(referrer);
        }

        userRepository.save(user);
        return convertToUserDto(user);
    }

    @Override
    public User updateUser(long id, User user) {
        if(!userRepository.existsById(id)){
            throw new BadRequestException("No user with such id: " + id);
        }
        user.setId(id);
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUser(long id) {
        if(!userRepository.existsById(id)){
            throw new BadRequestException("No user with such id: " + id);
        }
        userRepository.deleteById(id);
    }

    public void updateBalance(User user, BigDecimal money){
        BigDecimal balance = user.getBalance();
        BigDecimal newBalance = balance.add(money);
        user.setBalance(newBalance);
        userRepository.save(user);
    }

    public void updateBalances(String username, Task task){
        BigDecimal price = task.getPrice();

        //TODO: check user if null
        User user = userRepository.findByUsername(username);

        updateBalance(user, price);

        User referrer = user.getReferrer();

        if(referrer != null){
            Rank referrerRank = referrer.getRank();
            BigDecimal percentFromReferralOf1stLevel = referrerRank.getPercentFromReferralsOf1stLevelFromTasks();

            BigDecimal percentForReferrer = percentFromReferralOf1stLevel.divide(new BigDecimal(100));
            BigDecimal percentFromPrice = price.multiply(percentForReferrer);
            updateBalance(referrer, percentFromPrice);

            User referrerReferrer = referrer.getReferrer();
            if(referrerReferrer != null){

                Rank referrerReferrerRank = referrerReferrer.getRank();
                BigDecimal percentFromReferralsOf2stLevel = referrerReferrerRank.getPercentFromReferralsOf2stLevel();

                BigDecimal percentForReferrerReferrer = percentFromReferralsOf2stLevel.divide(new BigDecimal(100));
                BigDecimal percentFromPrice2 = price.multiply(percentForReferrerReferrer);
                updateBalance(referrerReferrer, percentFromPrice2);

            }

        }

    }

    @Override
    public UserImageDto uploadUserImg(long id, MultipartFile file) {
        String keyName = storageService.uploadImageToS3Bucket(file);
        User user = getUserById(id);
        user.setImage(keyName);
        userRepository.saveAndFlush(user);
        String url = storageService.getUrlFromS3(keyName).toString();
        return new UserImageDto(url);
    }

    @Override
    public void createPasswordResetTokenForUser(String token, User user) {

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);

        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public boolean isValidPasswordResetToken (String token){
        return passwordResetTokenRepository.findByToken(token) != null;
    }

    @Override
    public void updatePassword(String token, String password) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        if(passwordResetToken == null){
            throw new BadRequestException("Bad request");
        }

        int isTokenExpiriated = LocalDateTime.now().getSecond() - passwordResetToken.getExpiryDate().getSecond();
        if(isTokenExpiriated >= 0){
            throw new BadRequestException("Bad request");
        }

        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.saveAndFlush(user);
    }


    @Override
    public void deleteUserImg(long id) {
        User user = getUserById(id);
        storageService.deleteImageFromS3Bucket(user.getImage());
        user.setImage(null);
        userRepository.saveAndFlush(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("");
        }
        return user;
    }

    public String convertListRolesToString(List<Role> roles){
        List<ERole> eRoles = roles.stream().map( r -> r.getName()).collect(Collectors.toList());
        if(eRoles.contains(ERole.ADMIN)){
            return ERole.ADMIN.getDbValue();
        }
        if(eRoles.contains(ERole.MODERATOR)){
            return ERole.MODERATOR.getDbValue();
        }
        return ERole.USER.getDbValue();
    }

    public UserImageDto convertToUserImageDto(String urlImage) {
        if(urlImage == null) urlImage = "";
        return new UserImageDto(urlImage);
    }

    public UserAccountDto convertToUserAccountDto(User user) {
        return new UserAccountDto(
                user.getUsername(),
                user.getExp(),
                convertListRolesToString(user.getRoles()),
                convertToUserImageDto(user.getImage()),
                user.getBalance()
        );
    }

    public UserDto convertToUserDto(User user) {
        return new UserDto(user.getUsername(), user.getExp(), convertListRolesToString(user.getRoles()), convertToUserImageDto(user.getImage()) );
    }

}
