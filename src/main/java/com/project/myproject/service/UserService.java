package com.project.myproject.service;

import com.project.myproject.dao.RoleRepository;
import com.project.myproject.dao.UserRepository;
import com.project.myproject.dto.*;
import com.project.myproject.entity.Role;
import com.project.myproject.entity.User;
import com.project.myproject.enums.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IStorageService storageService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //@Async
    //public void sendMsgToEmail(){}

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
    public UserAccountDto getUserAccountByUsername(String username) {
        User user = getUserByUsername(username);
        return convertToUserAccountDto(user);
    }


    @Override
    public UserDto createUser(UserRegister userRegister) {
        if(userRepository.existsByEmail(userRegister.getEmail())){
            throw new IllegalArgumentException("There is an account with that email adress: " + userRegister.getEmail());
        }
        User user = new User();
        user.setUsername(userRegister.getUsername());
        user.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        user.setEmail(userRegister.getEmail());
        user.setRoles(Arrays.asList(roleRepository.findByName(ERole.USER)));
        userRepository.saveAndFlush(user);
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
    public void deleteUserImg(long id) {
        User user = getUserById(id);
        storageService.deleteImageFromS3Bucket(user.getImage());
        user.setImage(null);
        userRepository.saveAndFlush(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username);
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
                user.getBalance().getTotal()
        );
    }

    public UserDto convertToUserDto(User user) {
        return new UserDto(user.getUsername(), user.getExp(), convertListRolesToString(user.getRoles()), convertToUserImageDto(user.getImage()) );
    }

}
