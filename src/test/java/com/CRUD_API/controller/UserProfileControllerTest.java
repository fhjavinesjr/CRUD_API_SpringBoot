package com.CRUD_API.controller;

import com.CRUD_API.dtos.UserProfileDTO;
import com.CRUD_API.services.UserProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UserProfileController.class)
public class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private UserProfileService userProfileService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllUsers() throws Exception {
        String birthDate1 = "07/09/1994";
        String birthDate2 = "09/10/1994";

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        LocalDate localDate1 = LocalDate.parse(birthDate1, dateTimeFormatter);
        LocalDate localDate2 = LocalDate.parse(birthDate2, dateTimeFormatter);

        List<UserProfileDTO> userList = Arrays.asList(
                new UserProfileDTO("John Doe", "john@example.com", "Male", localDate1, 32, "testAddress1", "single", "Filipino", "09534515906"),
                new UserProfileDTO("Jane Doe", "jane@example.com", "Female", localDate2, 30, "testAddress2", "single", "Filipino", "09534515906")
        );

        Mockito.when(userProfileService.getAllUsers()).thenReturn(userList);

        mockMVC.perform(get("/api/users/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(userList.size()))
                .andDo(print());

        verify(userProfileService , Mockito.times(1)).getAllUsers();

    }

    @Test
    public void testGetUserById() throws Exception {
        String birthDate1 = "07/09/1994";

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        LocalDate localDate1 = LocalDate.parse(birthDate1, dateTimeFormatter);

        UserProfileDTO user = new UserProfileDTO("John Doe", "john@example.com", "Male", localDate1, 32, "testAddress1","single", "Filipino", "09534515906");

        String strLocalDate1 = localDate1.format(dateTimeFormatter);

        Mockito.when(userProfileService.getUserById(1L)).thenReturn(user);

        mockMVC.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.birthDate").value(strLocalDate1))
                .andExpect(jsonPath("$.age").value(32))
                .andDo(print());

        // Verify that the getUserById method was called once with the correct argument
        verify(userProfileService, Mockito.times(1)).getUserById(1L);
    }


    @Test
    public void testCreateUser() throws Exception {
        String birthDate1 = "07/09/1994";

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        LocalDate localDate1 = LocalDate.parse(birthDate1, dateTimeFormatter);

        List<UserProfileDTO> userList = List.of(
                new UserProfileDTO("John Doe", "john@example.com", "Male", localDate1, 32, "testAddress1","single", "Filipino", "09534515906")
        );

        Mockito.when(userProfileService.createUser(any())).thenReturn(userList);

        mockMVC.perform(post("/api/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userList)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(userList.size()))
                    .andDo(print());

        verify(userProfileService, Mockito.times(1)).createUser(any());

    }

    @Test
    public void testUpdateUserById() throws Exception {
        String birthDate1 = "07/09/1994";

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        LocalDate localDate1 = LocalDate.parse(birthDate1, dateTimeFormatter);

        UserProfileDTO updateUser = new UserProfileDTO("John Updated", "john_updated@example.com", "Male", localDate1, 32, "testAddress1","single", "Filipino", "09534515906");

        Mockito.when(userProfileService.updateUser(eq(1L), any())).thenReturn(updateUser);

        mockMVC.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUser)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("John Updated"))
                    .andExpect(jsonPath("$.email").value("john_updated@example.com"))
                    .andDo(print());

        verify(userProfileService, Mockito.times(1)).updateUser(eq(1L), any());

    }

    @Test
    public void testDeleteUserById() throws Exception {
        doNothing().when(userProfileService).deleteUser(1L);

        mockMVC.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(userProfileService, Mockito.times(1)).deleteUser(1L);
    }

}
