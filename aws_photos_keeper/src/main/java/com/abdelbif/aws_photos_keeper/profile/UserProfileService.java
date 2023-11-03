package com.abdelbif.aws_photos_keeper.profile;

import com.abdelbif.aws_photos_keeper.bucket.BucketName;
import com.abdelbif.aws_photos_keeper.filestore.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    List<UserProfile> getUserProfiles(){
        return userProfileDataAccessService.getUserProfiles();
    }

    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) throws IOException {
        //1 check if image is not empty
            if(file.isEmpty()){
                throw new IllegalStateException("Cannot store empty file [" + file.getSize() + "]");
            }
        //2 check if file is image
        if(!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(),
                ContentType.IMAGE_PNG.getMimeType(),
                ContentType.IMAGE_GIF.getMimeType()).contains(file.getContentType())){
            throw new IllegalStateException("File is not an image [" + file.getContentType());
        }
        //3 check if user exists in DB
        //System.out.println(userProfileId);
        UserProfile user = userProfileDataAccessService
                .getUserProfiles()
                .stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userProfileId)));
        //4 Grab metadata from file if any
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Type", String.valueOf(file.getSize()));

        //5 store image in s3 and update DB (userProfileImageLink) with s3 image link
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
        String filename = String.format("%s-%s", file.getName(), UUID.randomUUID());
        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
        }catch (IOException e){
            throw new IllegalStateException(e);
        }
    }
}
