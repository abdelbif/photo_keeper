package com.abdelbif.aws_photos_keeper.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user-profile")
@CrossOrigin
public class UserProfileController {
    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    //GETTING THE USER
    @GetMapping
    public List<UserProfile> getUserProfiles(){
        return userProfileService.getUserProfiles();
    }

    //POSTING THE USER
    @PostMapping(
            path = "/{userProfileId}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadUserProfileImage (@PathVariable("userProfileId") UUID userProfileId,
                                        @RequestParam("file")  MultipartFile file) {
        //System.out.print("this is User ID: " +userProfileId);
        try {
            userProfileService.uploadUserProfileImage(userProfileId, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
