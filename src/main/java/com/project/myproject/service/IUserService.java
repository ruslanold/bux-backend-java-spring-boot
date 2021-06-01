package com.project.myproject.service;

import com.project.myproject.dto.UserAccountDto;
import com.project.myproject.dto.UserDto;
import com.project.myproject.dto.UserImageDto;
import com.project.myproject.dto.UserRegister;
import com.project.myproject.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface IUserService extends UserDetailsService {
    List<User> getUsers();
    UserDto getUser(long id);
    User getUserById(long id);
    User getUserByUsername(String username);
    UserAccountDto getUserAccountByUsername(String username);
    UserDto createUser(UserRegister userRegister);
    User updateUser(long id, User user);
    UserImageDto uploadUserImg(long id, MultipartFile file);
    void deleteUser(long id);
    void deleteUserImg(long id);
}
