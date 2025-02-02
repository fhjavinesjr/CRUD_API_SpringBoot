package com.CRUD_API.controller;

import com.CRUD_API.dtos.UserProfileDTO;
import com.CRUD_API.entitymodels.UserProfile;
import com.CRUD_API.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    @Autowired
    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/")
    public List<UserProfileDTO> getAllUsers() {
        return userProfileService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserProfileDTO getUserById(@PathVariable Long id) {
        return userProfileService.getUserById(id);
    }

    @PostMapping("/create")
    public List<UserProfileDTO> createUser(@RequestBody List<UserProfileDTO> userProfileDTOList) {
        return userProfileService.createUser(userProfileDTOList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDTO> updateUserById(@PathVariable Long id, @RequestBody UserProfileDTO userProfileDTO) {
        UserProfileDTO updateUser = userProfileService.updateUser(id, userProfileDTO);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userProfileService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
