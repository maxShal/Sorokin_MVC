package org.example.service;
import org.example.model.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@AutoConfigureMockMvc
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc mvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getUserHashMap() throws Exception {
        UserDto user = new UserDto(1L, "Max", "test@example.com", 20, List.of());
        UserDto user_second = new UserDto(2L, "Iban", "test@example.com", 20, List.of());
        userService.createUser(user);
        userService.createUser(user_second);
        List<UserDto> userDtoList = List.of(user,user_second);
        var jsonResponse = mvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<UserDto> userDto = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
        Assertions.assertEquals(2, userDto.size());
        Assertions.assertEquals(userDto, userDtoList);
    }

    @Test
    void getUserById() throws Exception {
        UserDto user = new UserDto(1L, "Max", "test@example.com", 20, List.of());
        userService.createUser(user);
        String newUserJson = objectMapper.writeValueAsString(user);
        var jsonResponse = mvc.perform(get("/api/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var userDto = objectMapper.readValue(jsonResponse, UserDto.class);
        Assertions.assertEquals(userDto.getName(),user.getName());
    }


    @Test
    void createUser() throws Exception {
        UserDto user = new UserDto(1L, "Max", "test@example.com", 20, List.of());
        String newUserJson = objectMapper.writeValueAsString(user);

        var jsonResponse = mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var userDto = objectMapper.readValue(jsonResponse, UserDto.class);

        Assertions.assertEquals(userDto.getName(),user.getName());
    }

    @Test
    void putUser() throws Exception {
        UserDto user = new UserDto(1L, "Max", "test@example.com", 20, List.of());
        userService.createUser(user);

        UserDto updatedUser = new UserDto(1L, "Maxim", "test@example.com", 20, List.of());
        String updatedUserJson = objectMapper.writeValueAsString(updatedUser);

        var responseJson = mvc.perform(put("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var userDto = objectMapper.readValue(responseJson, UserDto.class);

        Assertions.assertEquals(updatedUser.getName(), userDto.getName());
    }


    @Test
    void deleteUserById() throws Exception {
        UserDto user = new UserDto(1L, "Max", "test@example.com", 20, List.of());
        userService.createUser(user);
        String newUserJson = objectMapper.writeValueAsString(user);
        var userJson = mvc.perform(delete("/api/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        String responseBody = userJson.getResponse().getContentAsString();

        Assertions.assertTrue(responseBody.isEmpty());

    }

    @Test
    void deleteUserByName() throws Exception {
        UserDto user = new UserDto(1L, "Max", "test@example.com", 20, List.of());
        userService.createUser(user);
        String newUserJson = objectMapper.writeValueAsString(user);
        var userJson = mvc.perform(delete("/api/users/by-name/{name}", "Max")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isNoContent())
                .andReturn();

        String responseBody = userJson.getResponse().getContentAsString();

        Assertions.assertTrue(responseBody.isEmpty());
    }
}