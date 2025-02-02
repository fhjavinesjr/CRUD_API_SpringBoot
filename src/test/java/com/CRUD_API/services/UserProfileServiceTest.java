package com.CRUD_API.services;

import com.CRUD_API.controller.UserProfileController;
import com.CRUD_API.dtos.UserProfileDTO;
import com.CRUD_API.entitymodels.UserProfile;
import com.CRUD_API.repositories.UserProfileRepository;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserProfileServiceTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileService userProfileService;

    private UserProfile userProfile;
    private UserProfileDTO userProfileDTO;

    @BeforeEach
    public void setup() {
        userProfile = new UserProfile("John Doe", "john@example.com", "Male", LocalDate.of(1994, 9, 7), 30);
        userProfile.setId(1L);
        userProfileDTO = new UserProfileDTO("John Doe", "john@example.com", "Male", LocalDate.of(1994, 9, 7), 30);
    }

    @Test
    public void testGetAllUsers() {
        Mockito.when(userProfileRepository.findAll()).thenReturn(Collections.singletonList(userProfile));

        List<UserProfileDTO> result = userProfileService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());

        verify(userProfileRepository, Mockito.times(1)).findAll();

    }

    @Test
    public void testGetUserById_Success() {
        Mockito.when(userProfileRepository.findById(1L)).thenReturn(Optional.of(userProfile));

        UserProfileDTO result = userProfileService.getUserById(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());

        verify(userProfileRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testGetUserById_NotFound() {
        Mockito.when(userProfileRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userProfileService.getUserById(1L);
        });

        assertEquals("User not found", exception.getMessage());

        verify(userProfileRepository, Mockito.times(1)).findById(1L);

    }

    @Test
    public void testCreateUser() {
        List<UserProfileDTO> userProfileDTOList = Collections.singletonList(userProfileDTO);

        Mockito.when(userProfileRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        List<UserProfileDTO> result = userProfileService.createUser(userProfileDTOList);

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());

        verify(userProfileRepository, Mockito.times(1)).save(any(UserProfile.class));
    }

    @Test
    public void testUpdateUser_Success() {
        Mockito.when(userProfileRepository.findById(1L)).thenReturn(Optional.of(userProfile));
        Mockito.when(userProfileRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        UserProfileDTO updatedUserDTO = new UserProfileDTO("John Updated",
                "john_updated@example.com", "Male", LocalDate.of(1994, 9, 7), 30);
        UserProfileDTO result = userProfileService.updateUser(1L, updatedUserDTO);

        assertEquals("John Updated", result.getName());
        assertEquals("john_updated@example.com", result.getEmail());

        verify(userProfileRepository, Mockito.times(1)).findById(1L);

    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userProfileRepository).deleteById(1L);

        userProfileService.deleteUser(1L);

        verify(userProfileRepository, Mockito.times(1)).deleteById(1L);
    }

}
