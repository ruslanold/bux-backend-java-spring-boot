package com.project.myproject.controller;

import com.project.myproject.dto.PasswordResetRequestDto;
import com.project.myproject.dto.*;
import com.project.myproject.entity.User;
import com.project.myproject.service.IEmailService;
import com.project.myproject.service.IUserService;
import com.project.myproject.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IEmailService emailService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping(value = "/{id}")
    public UserDto getUser(@PathVariable long id){
        return userService.getUser(id);
    }

    @GetMapping(value = "/account")
    public UserAccountDto getCurrentUserAccount(Principal principal){
        return userService.getUserAccountByUsername(principal.getName());
    }

    @GetMapping(value = "/logout")
    public void logout(Principal principal){

    }

    @GetMapping(value = "/reset/password")
    public boolean isValidPasswordResetToken(@RequestParam String code){
        return userService.isValidPasswordResetToken(code);
    }
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDto register(@RequestBody @Valid UserRegister userRegister){
        return userService.createUser(userRegister);
    }

    @PostMapping(value = "/authentication")
    public AuthResponseDto login(@RequestBody AuthRequestDto authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        String token = jwtService.generateToken(authRequest.getUsername());
        UserAccountDto userAccountDto = userService.getUserAccountByUsername(authRequest.getUsername());
        AuthResponseDto authResponseDto = new AuthResponseDto(token, userAccountDto);
        return authResponseDto;
    }

    @PostMapping(value = "/reset/password")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void resetPasword(HttpServletRequest request, @RequestBody PasswordResetRequestDto passwordResetRequestDto){

        User user = userService.getUserByEmail(passwordResetRequestDto.getEmail());

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(token, user);

        String path = "http://localhost:3000";
        emailService.constructResetTokenEmail(path, request.getLocale(), token, user);

    }


    @PutMapping(value = "/reset/password")
    public void updatePassword(@RequestBody PasswordUpdateRequestDto passwordUpdateRequest){
        passwordUpdateRequest.getPassword();
    }


    @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserImageDto uploadUserImg(@PathVariable long id, MultipartFile file){
        return userService.uploadUserImg(id, file);
    }

    @PutMapping(value = "/{id}")
    public User updateUser(@PathVariable long id, @RequestBody User user){
        return userService.updateUser(id, user);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id){
        userService.deleteUser(id);
    }

    @DeleteMapping(value = "/{id}/image")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletedUserImg(@PathVariable long id){
        userService.deleteUserImg(id);
    }

/*    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
       webDataBinder.addValidators(new MovieValidator());
    }*/
}
