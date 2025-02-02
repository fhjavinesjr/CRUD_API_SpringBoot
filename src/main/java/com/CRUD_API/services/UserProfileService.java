package com.CRUD_API.services;

import com.CRUD_API.dtos.UserProfileDTO;
import com.CRUD_API.entitymodels.UserProfile;
import com.CRUD_API.repositories.UserProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public List<UserProfileDTO> getAllUsers() {
        List<UserProfileDTO> userProfileDTOList = new ArrayList<>();
        List<UserProfile> userProfileList = userProfileRepository.findAll();

        for(UserProfile userProfile : userProfileList) {
            UserProfileDTO userProfileDTO = new UserProfileDTO(userProfile.getName(),
                    userProfile.getEmail(),
                    userProfile.getGender(),
                    userProfile.getBirthDate(),
                    userProfile.getAge());

            userProfileDTOList.add(userProfileDTO);
        }

        return userProfileDTOList;
    }

    public UserProfileDTO getUserById(Long id) {
        UserProfile userProfile = userProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        return new UserProfileDTO(userProfile.getName(),
                userProfile.getEmail(),
                userProfile.getGender(),
                userProfile.getBirthDate(),
                userProfile.getAge());
    }

    @Transactional
    public List<UserProfileDTO> createUser(List<UserProfileDTO> userProfileDTOList) {
        for(UserProfileDTO userProfileDTO : userProfileDTOList) {
            UserProfile userProfile = new UserProfile(userProfileDTO.getName(),
                    userProfileDTO.getEmail(),
                    userProfileDTO.getGender(),
                    userProfileDTO.getBirthDate(),
                    userProfileDTO.getAge());

            userProfileRepository.save(userProfile);

            userProfileDTO.setAge(userProfile.getAge());
        }

        return userProfileDTOList;
    }

    @Transactional
    public UserProfileDTO updateUser(Long id, UserProfileDTO userProfileDTO) {
        UserProfileDTO existingUser = getUserById(id);
        existingUser.setName(userProfileDTO.getName());
        existingUser.setEmail(userProfileDTO.getEmail());
        existingUser.setGender(userProfileDTO.getGender());
        existingUser.setBirthDate(userProfileDTO.getBirthDate());

        UserProfile userProfile = new UserProfile(existingUser.getName(),
                existingUser.getEmail(), existingUser.getGender(),
                existingUser.getBirthDate(), existingUser.getAge());

        userProfile.setId(id);
        userProfileRepository.save(userProfile);

        return existingUser;
    }

    @Transactional
    public void deleteUser(Long id) {
         userProfileRepository.deleteById(id);
    }

}
