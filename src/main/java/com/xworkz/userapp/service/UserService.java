package com.xworkz.userapp.service;

import com.xworkz.userapp.dto.UserDto;
import org.springframework.ui.Model;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface UserService {

//    String encryption(String password);
//    String matchPassword(String encryptedPassword);
    String generateRandomPassword();
    Boolean saveUserWithGeneratedPassword(UserDto dto, Model model);
    String validateFields(UserDto dto);


//    Boolean validateAndSaveUser(UserDto dto, Model model);
//    UserDto getPasswordByEmail(String email, String enteredPassword);

    UserDto getUserByEmail(String email);
    UserDto getUserById(int id);
    boolean resetPassword(String email, String newPassword);
    String validateAndLogIn(String email, String password);
    boolean updateProfile(UserDto dto);
    boolean deleteUserByEmail(String email);


    List<UserDto> getAllUsers() throws InvocationTargetException, IllegalAccessException;
    boolean updateUser(UserDto dto);
    void deleteById(int id);

    long getNameCount(String name);
    Boolean existByEmail(String email);
    Boolean existByPhNumber(String phNumber);
}
