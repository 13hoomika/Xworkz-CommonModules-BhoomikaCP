package com.xworkz.userapp.entity;

import com.xworkz.userapp.constants.LocationEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
@Setter
@Entity
@ToString
@Table(name = "commons_modules_bhoomikacp")
//@NamedQuery(name = "getPasswordByEmail", query = "SELECT entity FROM UserEntity entity WHERE entity.email = :email")
@NamedQuery(name = "getAllUsersQuery", query = "FROM UserEntity")
@NamedQuery(name = "getUserById", query = "FROM UserEntity WHERE id=:id")
@NamedQuery(name = "getUsersByEmail", query = "FROM UserEntity WHERE email=:emailId")
@NamedQuery(name = "deleteUserByEmail", query = "DELETE FROM UserEntity user WHERE user.email=:email")
@NamedQuery(name = "deleteUser", query = "DELETE FROM UserEntity user WHERE user.id=:id")

@NamedQuery(name = "emailExist", query = "SELECT COUNT(u) FROM UserEntity u WHERE LOWER(u.email) = LOWER(:email)")
@NamedQuery(name = "phExist", query = "SELECT COUNT(u) FROM UserEntity u WHERE u.phNumber = :phNumber")
@NamedQuery(name = "getNameCount", query = "Select count(user.name) from UserEntity user where user.name = :setName")


public class UserEntity extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    @Column(name = "ph_number")
    private String phNumber;
    private int age;
    private String gender;
    private String doB;
//    @Enumerated(EnumType.STRING)
    private String location;
    private String pwd;
    @Column(name = "invalid_logInCount")
    private int invalidLogInCount; // =-1;
    @Column(name = "account_locked")
    private boolean accountLocked = false;
    @Column(name="last_logIn", columnDefinition = "TIMESTAMP(6)")
    private Instant lastLogIn;
//    @Column(name = "profile_img")
//    private String profileImg;
    @Column(name = "profile_data")
    private String profileData;
}
