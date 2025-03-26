package com.xworkz.userapp.repository;

import com.xworkz.userapp.entity.UserEntity;

import java.util.List;

public interface UserRepository {
    Boolean save(UserEntity entity);

    UserEntity getUserById(int id);
    UserEntity getUserByEmail(String email);

    Boolean existByEmail(String email);
    Boolean existByPhNumber(String phNumber);

    boolean updateProfile(UserEntity updatedEntity);
    void deleteByEmail(String email);

    boolean updateUser(UserEntity userEntity);
    UserEntity fetchPasswordByEmail(String email);
    List<UserEntity> getAllUsers();
    void deleteById(int id);

    long getNameCount(String name);
}
