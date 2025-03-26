package com.xworkz.userapp.dto;

import com.xworkz.userapp.constants.LocationEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {
    private Integer id;
    @NotNull(message = "Name can't be null")
    @Size(min = 3, max = 5, message = "Name must be between 3 and 5 characters")
    private String name;

    // Other fields with validation annotations
    @NotNull(message = "Email can't be null")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Phone number can't be null")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phNumber;
//    private String name;
//    private String email;
//    private Long phNumber;
    private int age;
    private String gender;
    private String doB;
    private String location;
    private String pwd;
    private String confirmPwd;
    private boolean accountLocked;

    private String profileData;
//    private transient MultipartFile multipartFile; // Don't save this in DB

}
