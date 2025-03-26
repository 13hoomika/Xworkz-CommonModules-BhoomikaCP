package com.xworkz.userapp.controller;

import com.xworkz.userapp.dto.UserDto;
import com.xworkz.userapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RequestMapping("/")
@Controller
public class UpdateProfileController {
    // process the Request & Navigate the Request

    @Autowired
    UserService userService;

    public UpdateProfileController() {
        log.info("UpdateProfileController obj is created");
    }

//    @RequestMapping("getProfile")
//    public String getProfileByEmail(@RequestParam("email") String email, Model model){
//        System.out.println("controller getProfileByEmail started");
//        UserDto dto = userService.getUserByEmail(email);
//        System.out.println("Data Controller got from service: "+dto);
//        model.addAttribute("profile",dto);
//        System.out.println("controller getProfile ended returning updateProfile.jsp");
//        return "updateProfile.jsp";
//    }


    /*@RequestMapping("updateProfile")
    public String updateProfileEmail( @ModelAttribute UserDto dto, Model model, HttpSession session,@RequestParam("profileData") MultipartFile multipartFile) throws IOException {
        System.out.println("\n=========== Update Img ==================");


//        List<LocationEnum> locationList = new ArrayList<>(Arrays.asList(LocationEnum.values()));
        log.info("\n Controller updateProfileEmail started for email: {}", dto.getEmail());

        // Fetch the existing user from the database
        UserDto existingUser = userService.getUserByEmail(dto.getEmail());

        if (existingUser == null) {
            log.warn("User not found for email: {}", dto.getEmail());
            model.addAttribute("updateProfileMsg", "User not found!");
            return "updateProfile.jsp";
        }

        // Preserve age and gender if not provided in the form
        dto.setAge(existingUser.getAge()); // Keep the old age
        dto.setGender(existingUser.getGender()); // Keep the old gender

        byte[] bytes = multipartFile.getBytes();
        System.out.println("fileName==" + multipartFile.getBytes());
        System.out.println("fileName==" + multipartFile.getOriginalFilename());
        Path path = Paths.get("C:\\commons\\" + dto.getName());
        Files.write(path,bytes);
        String fileName = path.getFileName().toString();
        System.out.println("fileName==" + fileName);

        try {
            boolean userUpdated = userService.updateProfile(dto);
            log.info("\n User update status: {}", userUpdated);

            if (userUpdated) {
                model.addAttribute("updateProfileMsg", "User details updated successfully");
                log.info("\nUser details updated successfully for email: {}", dto.getEmail());

                // Update session with new details
                UserDto updatedUser = userService.getUserByEmail(dto.getEmail());
                session.setAttribute("loggedInUser", updatedUser);
            } else {
                model.addAttribute("updateProfileMsg", "User details update failed");
                log.warn("User details update failed for email: {}", dto.getEmail());
            }
        } catch (Exception e) {
            model.addAttribute("updateProfileMsg", "An error occurred: " + e.getMessage());
            log.error("Exception while updating profile for email: {}. Error: {}", dto.getEmail(), e.getMessage(), e);
        }

        log.info("Controller updateProfileEmail ended, returning to updateProfile.jsp");
        return "updateProfile";
    }*/

    @RequestMapping("updateProfile")
    public String updateProfileEmail(
            @ModelAttribute UserDto dto,
            @RequestParam("multipartFile") MultipartFile multipartFile,
            Model model,
            HttpSession session) {

        log.info("Controller updateProfileEmail started for email: {}", dto.getEmail());

        // Fetch existing user from DB
        UserDto existingUser = userService.getUserByEmail(dto.getEmail());
        if (existingUser == null) {
            model.addAttribute("updateProfileMsg", "User not found!");
            return "updateProfile";
        }

        // Preserve age and gender if not provided
        dto.setAge(existingUser.getAge());
        dto.setGender(existingUser.getGender());

        try {
            // Handling file upload
            if (multipartFile != null && !multipartFile.isEmpty()) {
                byte[] bytes = multipartFile.getBytes();
                String originalFilename = multipartFile.getOriginalFilename();// Get the original filename and extension
                String extension = originalFilename.substring(originalFilename.lastIndexOf(".")); // Get file extension
                String newFileName = dto.getName() + "_" + System.currentTimeMillis() + extension;// Create unique filename
                Path path = Paths.get("D:\\06 GO19ROM Aug19\\Project Phase\\Common Module\\Uploaded Profile Images Xworkz_CommonModule_1\\" + newFileName);
                Files.write(path, bytes);
                dto.setProfileData(newFileName);

                /*byte[] bytes = multipartFile.getBytes();
                System.out.println("file bytes= " + multipartFile.getBytes());
                System.out.println("file original name = " + multipartFile.getOriginalFilename());
                Path path = Paths.get("C:\\commons\\" + dto.getName());
                Files.write(path,bytes);
                String fileName = path.getFileName().toString();
                System.out.println("fileName = " + fileName);*/

                //works
//                byte[] bytes = multipartFile.getBytes();
//                System.out.println("file bytes= " + multipartFile.getBytes());
//                String originalFilename =  multipartFile.getOriginalFilename();
//                System.out.println("file original name = " + originalFilename);
//                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//                Path path = Paths.get("D:\\\\06 GO19ROM Aug19\\\\Project Phase\\\\Common Module\\\\FileUpload Images\\\\" + dto.getName()+ "_" + System.currentTimeMillis()+extension);
//                Files.write(path,bytes);
//                String fileName = path.getFileName().toString();
//                System.out.println("fileName = " + fileName);
//                dto.setProfileData(fileName);

            } else {
                // Keep the old image if no new file is uploaded
                dto.setProfileData(existingUser.getProfileData());
            }

            boolean userUpdated = userService.updateProfile(dto);
            if (userUpdated) {
                model.addAttribute("updateProfileMsg", "User details updated successfully");
                session.setAttribute("loggedInUser", dto);
            } else {
                model.addAttribute("updateProfileMsg", "User details update failed");
            }
        } catch (Exception e) {
            model.addAttribute("updateProfileMsg", "An error occurred: " + e.getMessage());
            log.error("Exception while updating profile for email: {}. Error: {}", dto.getEmail(), e.getMessage(), e);
        }
        return "updateProfile";
    }

    /*@GetMapping("view")
    public void download(HttpServletResponse response, @RequestParam String fileName) throws IOException {
        response.setContentType("image/jpg");
        File file = new File("D:\\06 GO19ROM Aug19\\Project Phase\\Common Module\\Uploaded Profile Images Xworkz_CommonModule_1\\" + fileName);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        ServletOutputStream out = response.getOutputStream();
        IOUtils.copy(in,out);
        response.flushBuffer();
    }*/
    @GetMapping("view")
    public String download(@RequestParam String fileName, HttpServletResponse response, Model model) throws IOException {
        String uploadDir = "D:\\06 GO19ROM Aug19\\Project Phase\\Common Module\\Uploaded Profile Images Xworkz_CommonModule_1\\";
        File file = new File(uploadDir + fileName);

        if (!file.exists() || !file.canRead()) {
            model.addAttribute("profileErrMsg", "No profile image uploaded!");
        }

        response.setContentType("image/jpeg");
        response.setHeader("Content-Disposition", "inline; filename=" + fileName);

        try (InputStream in = new BufferedInputStream(new FileInputStream(file));
             ServletOutputStream out = response.getOutputStream()) {
            IOUtils.copy(in, out);
            response.flushBuffer();
        }

        return "welcome";
    }

}
