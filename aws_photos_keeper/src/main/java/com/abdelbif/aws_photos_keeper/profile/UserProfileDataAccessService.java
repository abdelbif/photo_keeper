package com.abdelbif.aws_photos_keeper.profile;

import com.abdelbif.aws_photos_keeper.datastore.FakeUserProfileDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserProfileDataAccessService {

    private  final FakeUserProfileDataStore fakeUserProfileDataStore;
    @Autowired
    public UserProfileDataAccessService(FakeUserProfileDataStore fakeUserProfileDataStore) {
        this.fakeUserProfileDataStore = fakeUserProfileDataStore;
    }

    List<UserProfile> getUserProfiles(){
        //System.out.println("MOI"+ fakeUserProfileDataStore.getUserProfiles());
        return fakeUserProfileDataStore.getUserProfiles();
    }
}
