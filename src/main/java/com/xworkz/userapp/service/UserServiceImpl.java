package com.xworkz.userapp.service;

import com.xworkz.userapp.dto.UserDto;
import com.xworkz.userapp.entity.UserEntity;
import com.xworkz.userapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    // Generate a random password
    @Override
    public String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@$!%*?&";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) { // Generate an 8-character password
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }

    // Encrypt the password
//    @Override
//    public String encryption(String password) {
//        StringBuilder encrypt = new StringBuilder();
//        for (char ch : password.toCharArray()) {
//            encrypt.append((char) (ch + 3)); // Shift each character by 3
//        }
//        return encrypt.toString();
//    }
//
//    // Decrypt the password
//    @Override
//    public String matchPassword(String encryptedPassword) {
//        StringBuilder decrypt = new StringBuilder();
//        for (char ch : encryptedPassword.toCharArray()) {
//            decrypt.append((char) (ch - 3)); // Shift each character back by 3
//        }
//        return decrypt.toString();
//    }
    @Override
    public Boolean saveUserWithGeneratedPassword(UserDto dto, Model model) {
        log.info("Service saveUserWithGeneratedPassword started");

        // Validate fields
        String validationError = validateFields(dto);
        if (validationError != null) {
            model.addAttribute("error", validationError);
            return false;
        }
//        // Check if email already exists
//        if (repository.existByEmail(dto.getEmail())) {
//            model.addAttribute("error", "Email already exists");
//            return false;
//        }
//
//        // Check if phone number already exists
//        if (repository.existByPhNumber(dto.getPhNumber())) {
//            model.addAttribute("error", "Phone number already exists");
//            return false;
//        }

        // Generate a random password
        String generatedPassword = generateRandomPassword();
        log.info("Generated Password: {}", generatedPassword);

        // Set the generated password in the DTO
        dto.setPwd(generatedPassword);

        // Encrypt the password before saving
//        String encryptedPassword = encryption(dto.getPwd());
        String encryptedPassword = passwordEncoder.encode(dto.getPwd());
        dto.setPwd(encryptedPassword);

        // Convert DTO to Entity and save
        UserEntity entity = new UserEntity();
        try {
            log.info("Converting DTO to Entity");
            entity.setInvalidLogInCount(-1);
            BeanUtils.copyProperties(dto, entity);
            // Send the generated password to the user's email
            if (sendEmail(dto.getEmail(), generatedPassword)) {
                entity.setCreatedBy(dto.getName());
                repository.save(entity);
                entity.setInvalidLogInCount(-1);
                log.info("service getting entity saved in repo : {}", entity);
//                log.info("Service saveUserWithGeneratedPassword ended");
                return true;
            } else {
                model.addAttribute("error", "Failed to send email with the generated password.");
                return false;
            }
        } catch (BeansException e) {
            model.addAttribute("error", "Error copying properties: " + e.getMessage());
            log.info("Service saveUserWithGeneratedPassword ended");
            return false;
        }
    }
//    @Override
////    @Transactional
//    public Boolean validateAndSaveUser(UserDto dto, Model model) {
//        log.info("Service validateAndSaveUser started");
//
//        String validationError = validateFields(dto);
//        if (validationError != null) {
//            model.addAttribute("error", validationError);
//            return false;
//        }
//
//        // Hash the password before saving
//        String hashedPassword = passwordEncoder.encode(dto.getPwd());
//        dto.setPwd(hashedPassword);
    ////        dto.setConfirmPwd(hashedPassword);
//
//        // Convert DTO to Entity and save
//        UserEntity entity = new UserEntity();
//        try {
//            log.info("Converting DTO to Entity");
//            BeanUtils.copyProperties(dto, entity);
//            repository.save(entity);
//            log.info("service getting entity saved in repo : {}" + entity);
//            log.info("Service validateAndSaveUser ended");
//            return true;
//        } catch (BeansException e) {
//            model.addAttribute("error", "Error copying properties: " + e.getMessage());
//            log.info("Service validateAndSaveUser ended");
//            return false;
//        }
//    }

    @Override
    public String validateFields(UserDto dto) {
        log.info("validateUserDetails started");

        // Regular Expressions
        String NAME_REGEX = "^[A-Z][a-z]*(?:\\s[A-Z][a-z]*)*$";
        String PHONE_REGEX = "^[9876]\\d{9}$";
//        String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
//        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";// allows kash@gm.com
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@(gmail|yahoo|outlook)\\.(com|net|org|edu|gov)$";
        String AGE_REGEX = "^(1[2-9]|[2-9]\\d|1[0-4]\\d|150)$";



        if (dto == null) {
            log.info("validateUserDetails ended");
            return "User details cannot be null.";
        }

        if (dto.getName() == null || !Pattern.matches(NAME_REGEX, dto.getName())) {
            log.warn("Invalid Name: {}", dto.getName());
            return "Invalid Name. Name must start with an uppercase letter and can contain multiple words.";
        }

        if (dto.getPhNumber() == null || !Pattern.matches(PHONE_REGEX, dto.getPhNumber())) {
            log.warn("Invalid Phone Number: {}", dto.getPhNumber());
            return "Invalid Phone Number. Must be 10 digits and start with 9, 8, 7, or 6.";
        }

        if (dto.getEmail()==null  || !Pattern.matches(EMAIL_REGEX, dto.getEmail())) {
            log.warn("Invalid Email:{}", dto.getEmail());
            return "Invalid Email.";
        }

        if (dto.getAge() == 0 || !Pattern.matches(AGE_REGEX, String.valueOf(dto.getAge()))) {
            log.warn("Invalid Age: {}", dto.getAge());
            return "Invalid Age. Age must be between 12 and 150.";
        }

        /*if (dto.getPwd() == null || !Pattern.matches(PASSWORD_REGEX, dto.getPwd())) {
            log.warn("Invalid Password: {}" + dto.getPwd());
            return "Invalid Password. Must be at least 8 characters long, include one uppercase letter, one lowercase letter, one digit, and one special character.";
        }

        if (!dto.getPwd().equals(dto.getConfirmPwd())) {
            log.warn("Password mismatch: {}" + dto.getPwd() + " != " + dto.getConfirmPwd());
            return "Password and Confirm Password must be the same.";
        }*/

        log.info("validateUserDetails ended");
        return null;
    }

//    @Override
//    public UserDto getPasswordByEmail(String email, String enteredPassword) {
//        log.info("Service getPasswordByEmail started");
    ////        UserDto userDto = new UserDto();
//        UserEntity userEntity = repository.fetchPasswordByEmail(email);
//        log.info("fetched data from repo: {}"+ userEntity);
//
//        if (userEntity == null) {
//            throw new RuntimeException("User not found with email: " + email);
//        }
//
//        // Verify the entered password against the stored hashed password
//        if (passwordEncoder.matches(enteredPassword, userEntity.getPwd())) {
//            // Passwords match - return the UserDto
//            UserDto userDto = new UserDto();
//            try {
//                BeanUtils.copyProperties(userEntity, userDto);
//            } catch (BeansException e) {
//                throw new RuntimeException("Error copying properties: " + e.getMessage());
//            }
//            log.info("Service getPasswordByEmail ended returning dto");
//            return userDto;
//        } else {
//            throw new RuntimeException("Invalid password. Please try again.");
//        }
//
//    }

    @Override
    public UserDto getUserByEmail(String email) {
        log.info("\nStarted service getUserByEmail. Email: {}", email);

        UserDto userDto = new UserDto();
        UserEntity userEntity = repository.getUserByEmail(email);
        log.info("Data service got from repo: {}", userEntity);

        if (userEntity == null) {
            log.info("UserEntity is null for email: {}", email);
            return null;
        }else {
            try {
                BeanUtils.copyProperties(userEntity, userDto);
                log.info("Ended service getUserByEmail");
                return userDto;
            } catch (BeansException e) {
                log.warn("Error copying properties: {}", e.getMessage());
                return null;
            }
        }

    }

    @Override
    public boolean updateProfile(UserDto dto) {
        log.info("Started service updateProfile");
        boolean isUpdated = false;
        if (dto != null) {
            try {
                // Fetch the existing user by email
                UserEntity existingUser = repository.getUserByEmail(dto.getEmail());

                if (existingUser != null) {
                    // Update the user details
                    existingUser.setName(dto.getName());
                    existingUser.setPhNumber(dto.getPhNumber());
                    existingUser.setAge(dto.getAge());
                    existingUser.setGender(dto.getGender());
                    existingUser.setDoB(dto.getDoB());
                    existingUser.setLocation(dto.getLocation());
                    existingUser.setProfileData(dto.getProfileData());

                    existingUser.setUpdateBy(dto.getName());
                    existingUser.setUpdatedTime(LocalDateTime.now());

                    // If a new password is provided, encrypt and update it
                    if (dto.getPwd() != null && !dto.getPwd().isEmpty()) {
//                        String encryptedPassword = encryption(dto.getPwd());
                        String encryptedPassword = passwordEncoder.encode(dto.getPwd());
                        existingUser.setPwd(encryptedPassword);
                    }

//                    UserEntity entity = new UserEntity();
//                    BeanUtils.copyProperties(dto,entity); // Coping properties from DTO to the existing entity

                    isUpdated = repository.updateProfile(existingUser);
                    log.info("service got updated user details form repo: {}", existingUser);
                    log.info("\nservice got updated user from repo: {}", isUpdated);
                }
            } catch (BeansException e) {
                log.warn("Error updating profile: {}", e.getMessage());
                throw new RuntimeException("Error updating profile: " + e.getMessage());
            }
        }else {
            log.warn("User Details can not be empty");
        }
        log.info("Ended service updateProfile");
        return isUpdated;
    }

    @Override
    public boolean resetPassword(String email, String newPassword) {
        UserEntity user = repository.getUserByEmail(email);// Fetch the user by email
        if (user == null) {
            log.warn("User not found with email: {}", email);
            return false; // User not found
        }
        // Validate the new password
        String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (newPassword == null || !Pattern.matches(PASSWORD_REGEX, newPassword)) {
            log.warn("Invalid New Password: {}", newPassword);
            log.warn("Invalid Password. Must be at least 8 characters long, include one uppercase letter, one lowercase letter, one digit, and one special character.");
            return false;
        }

        /*String hashedPassword = passwordEncoder.encode(newPassword);// Hash the new password
        user.setPwd(hashedPassword);// Update the user's password
        return repository.updateProfile(user);*/// Save the updated user

        // Encrypt the new password
//        String encryptedPassword = encryption(newPassword);
        String encryptedPassword = passwordEncoder.encode(newPassword);// Hash the new password
        user.setPwd(encryptedPassword); // Update the user's password
        // Save the updated user
        //return repository.updateProfile(user);
        try {
            boolean isUpdated = repository.updateProfile(user);
            if (!isUpdated) {
                log.error("Failed to update user profile for email: {}", email);
                return false;
            }
            log.info("Password successfully reset for user with email: {}", email);
            return true;
        } catch (Exception e) {
            log.error("Error updating user profile for email: {}", email, e);
            return false;
        }
    }

    @Override
    public String validateAndLogIn(String email, String password) {
        log.info("validateAndLogIn in service started");

        // Fetch the user by email
        UserEntity entity = repository.getUserByEmail(email);
        if (entity == null) {
            return "Invalid email";
        }

        // Check if the account is locked
        if (entity.isAccountLocked()) {
            // Check if 24 hours have passed since the last login attempt
            if (Duration.between(entity.getLastLogIn(), Instant.now()).toMillis() >= 60000) {
                // Unlock the account after 24 hours
                entity.setAccountLocked(false);
                entity.setInvalidLogInCount(0);
                entity.setLastLogIn(null);
                repository.updateProfile(entity);
            } else {
                return "Account is locked. Try again after 24 hours.";
            }
        }

        // Decrypt the stored password
        //    String decryptedPassword = matchPassword(entity.getPwd());

        // Check if the password is correct
        if (passwordEncoder.matches(password, entity.getPwd())) {
            //    if (password.equals(decryptedPassword)) {
            // Reset invalid login count and last login time on successful login
            entity.setInvalidLogInCount(0);
            entity.setLastLogIn(null);
            repository.updateProfile(entity);
            return "isPresent";
        } else {
            // Increment invalid login count and update last login time
            int invalidLogInCount = entity.getInvalidLogInCount();
            invalidLogInCount++;
            entity.setInvalidLogInCount(invalidLogInCount);
            entity.setLastLogIn(Instant.now());

            // Lock the account if there are 3 or more failed attempts
            if (invalidLogInCount >= 3) {
                entity.setAccountLocked(true);
            }

            // Save the updated entity to the database
            repository.updateProfile(entity);

            // Return appropriate message based on the number of failed attempts
            if (invalidLogInCount == 1) {
                return "Password is incorrect. Two more attempts left.";
            } else if (invalidLogInCount == 2) {
                return "Password is incorrect. One attempt left.";
            } else if (invalidLogInCount >= 3) {
                return "Account is locked for 24 hours.";
            }
        }

        log.info("validateAndLogIn() in service ended");
        return "Invalid email";
    }

    public boolean sendEmail(String recipientEmail, String generatedPassword) {
        final String username = "bhoomikacp.xworkz@gmail.com";
        final String password = "zilw thew euxr dmsr";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Your Generated Password");
            message.setText("Dear User,\n\nYour generated password is: " + generatedPassword + "\n\nPlease change your password after logging in.\n\nBest regards,\nYour Application Team");

            Transport.send(message);

            log.info("Email sent successfully to: {}", recipientEmail);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUserByEmail(String email) {
        log.info("Deleting user with email: {}", email);

        // Check if the user exists
        if (repository.existByEmail(email)) {
            repository.deleteByEmail(email); // Delete the user
            log.info("User deleted successfully.");
            return true;
        } else {
            log.info("User not found with email: {}", email);
            return false;
        }
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Override
    public UserDto getUserById(int id) {
        UserDto userDto = new UserDto();
        UserEntity userEntity = repository.getUserById(id);
        BeanUtils.copyProperties(userEntity,userDto);

        return userDto;
    }
    @Override
    public boolean updateUser(UserDto dto) {
        UserEntity userEntity = new UserEntity();
        boolean isUpdated = false;
        if (dto != null) {
            BeanUtils.copyProperties(dto, userEntity);
            isUpdated = repository.updateUser(userEntity);

        }else log.info("Details can not be empty");
        return isUpdated;
    }

    @Override
    public List<UserDto> getAllUsers() {
        log.info("Started service getAllUsers");
        List<UserEntity> entityList = repository.getAllUsers();
        List<UserDto> dtoList = new ArrayList<>();

        if (entityList != null){
            for(UserEntity entity : entityList){
                UserDto dto = new UserDto();

                BeanUtils.copyProperties(entity,dto);
                dtoList.add(dto);
            }
        }
        log.info("Ended service getAllUsers");
        return  dtoList;
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Override
    public Boolean existByEmail(String email) {
        System.out.println("invoking existByEmail in service..........");
        Boolean emailExist = repository.existByEmail(email);
        System.out.println("is emailExist in service: " + emailExist);
        return emailExist;
    }

    @Override
    public Boolean existByPhNumber(String phNumber) {
        return repository.existByPhNumber(phNumber);
    }

    @Override
    public long getNameCount(String name) {
//        System.out.println(" invoking in the service method..........");
        long count = repository.getNameCount(name);
        System.out.println("Count of name from service: "+count);
        return count;
    }

}
