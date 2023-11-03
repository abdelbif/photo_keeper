package com.abdelbif.aws_photos_keeper.datastore;

import com.abdelbif.aws_photos_keeper.profile.UserProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {
    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();
    static {
        USER_PROFILES.add(new UserProfile(UUID.randomUUID(), "jeanroody", null));
        USER_PROFILES.add(new UserProfile(UUID.randomUUID(), "emmambulu", null));
    }

    public static List<UserProfile> getUserProfiles() {
 //       for(int i=0 ; i<USER_PROFILES.size(); i++){
//            System.out.println("User Profiles Id: " + USER_PROFILES);
//        }
        return USER_PROFILES;
    }
}
