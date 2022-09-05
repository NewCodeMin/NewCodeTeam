package com.NewCodeTeam.Comercializadora.Service;



import com.NewCodeTeam.Comercializadora.model.Profile;
import com.NewCodeTeam.Comercializadora.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    public Optional<Profile> getProfileById(Long id) {
        return profileRepository.findById(id);
    }

    public Profile saveOrUpdateProfile(Profile profile){
        return profileRepository.save(profile);
    }

    public boolean deleteProfile(Long id) {
        profileRepository.deleteById(id);
        if (this.profileRepository.findById(id).isPresent()) {
            return false;
        }
        return true;
    }



}
