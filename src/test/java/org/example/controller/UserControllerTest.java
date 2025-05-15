package org.example.controller;

import org.example.model.UserDto;
import org.example.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    UserService userService;
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getUserById() throws Exception {
        UserDto user = new UserDto(1L, "Max", "test@example.com", 20, List.of());
        //String newUser = objectMapper.writeValueAsString(user);
        userService.createUser(user);

        var jsonResponse = mockMvc.perform(get("/api/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var userDtoResponse = objectMapper.readValue(jsonResponse, UserDto.class);
        Assertions.assertEquals(user.getName(), userDtoResponse.getName());
    }

    @Test
    void getUsers() throws Exception{
        UserDto user1 = new UserDto(1L, "Max", "test@example.com", 20, List.of());
        UserDto user2 = new UserDto(2L, "Maxim", "test@example.com", 20, List.of());
        userService.createUser(user1);
        userService.createUser(user2);

        List<UserDto> users = List.of(user1, user2);

        var jsonResponse = mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<UserDto> userDto = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

        Assertions.assertEquals(2, userDto.size());
        Assertions.assertEquals(users, userDto);

    }

    @Test
    void createUser() throws Exception {
        UserDto user = new UserDto(1L, "Max", "test@example.com", 20, List.of());
        String newUser = objectMapper.writeValueAsString(user);

        var jsonResponse = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON).content(newUser))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        var userDtoResponse = objectMapper.readValue(jsonResponse, UserDto.class);
        Assertions.assertEquals(user.getName(), userDtoResponse.getName());
    }

    @Test
    void putUser() throws Exception{

        UserDto user = new UserDto(1L, "Max", "test@example.com", 20, List.of());
        userService.createUser(user);

        UserDto user2 = new UserDto(1L, "Maxim", "test@example.com", 20, List.of());
        String newUser = objectMapper.writeValueAsString(user2);

        var jsonResponse = mockMvc.perform(put("/api/users")
                        .contentType(MediaType.APPLICATION_JSON).content(newUser))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var userDtoResponse = objectMapper.readValue(jsonResponse, UserDto.class);
        Assertions.assertEquals(user2.getName(), userDtoResponse.getName());
    }

    @Test
    void deleteUserById() throws Exception{
        UserDto user = new UserDto(1L, "Max", "test@example.com", 20, List.of());
        userService.createUser(user);
        String newUserJson = objectMapper.writeValueAsString(user);
        var userJson = mockMvc.perform(delete("/api/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        String responseBody = userJson.getResponse().getContentAsString();

        Assertions.assertTrue(responseBody.isEmpty());

    }

    @Test
    void deleteUserByName() throws Exception{
        UserDto user = new UserDto(1L, "Max", "test@example.com", 20, List.of());
        userService.createUser(user);
        String newUserJson = objectMapper.writeValueAsString(user);
        var userJson = mockMvc.perform(delete("/api/users/by-name/{name}", "Max")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isNoContent())
                .andReturn();

        String responseBody = userJson.getResponse().getContentAsString();

        Assertions.assertTrue(responseBody.isEmpty());
    }
}