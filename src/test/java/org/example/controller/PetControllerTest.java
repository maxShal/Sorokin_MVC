package org.example.controller;

import org.example.entity.Pet;
import org.example.entity.User;
import org.example.mapper.PetDtoMapper;
import org.example.model.PetDto;
import org.example.model.UserDto;
import org.example.service.PetService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PetControllerTest {
    @Autowired
    private PetService petService;
    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc mvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private PetDtoMapper mapper;


    @Test
    void getPetById() throws Exception {
        Pet pet = new Pet(1L, "name", 1L);
        User user = new User(1L, "Max", "test@example.com", 20, new ArrayList<>());
        userService.createUser(user);
        petService.createPet(pet);

        String petJson = objectMapper.writeValueAsString(pet);
        var jsonResponse = mvc.perform(get("/api/pets/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var petDto = objectMapper.readValue(jsonResponse, Pet.class);
        Assertions.assertEquals(petDto.getName(), pet.getName());
    }

    @Test
    void getAllPets() throws Exception {
        Pet pet1 = new Pet(1L, "name1", 1L);
        Pet pet2 = new Pet(2L, "name2", 1L);
        User user = new User(1L, "Max", "test@example.com", 20, new ArrayList<>());
        userService.createUser(user);
        petService.createPet(pet1);
        petService.createPet(pet2);

        List<Pet> petsDtoList = List.of(pet1, pet2);

        var jsonResponse = mvc.perform(get("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<Pet> petsDto = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
        Assertions.assertEquals(2, petsDto.size());
        Assertions.assertEquals(petsDtoList, petsDto);

    }

    @Test
    void createPet() throws Exception{
        PetDto pet = new PetDto(1L, "name", 1L);
        User user = new User(1L, "Max", "test@example.com", 20, new ArrayList<>());
        userService.createUser(user);

        String petJson = objectMapper.writeValueAsString(pet);
        var jsonResponse = mvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var petDto = objectMapper.readValue(jsonResponse, PetDto.class);
        Assertions.assertEquals(petDto.getName(), pet.getName());
    }

    @Test
    void putPet() throws Exception{
        PetDto pet = new PetDto(1L, "name", 1L);
        User user = new User(1L, "Max", "test@example.com", 20, new ArrayList<>());
        userService.createUser(user);
        Pet pe = mapper.toDto(pet);
        petService.createPet(pe);

        String petJson = objectMapper.writeValueAsString(pe);
        var jsonResponse = mvc.perform(put("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var petDto = objectMapper.readValue(jsonResponse, Pet.class);
        Assertions.assertEquals(petDto.getName(), pet.getName());
    }

    @Test
    void deletePetById() throws Exception{

        Pet pet = new Pet(1L, "name", 1L);
        User user = new User(1L, "Max", "test@example.com", 20, new ArrayList<>());
        userService.createUser(user);
        petService.createPet(pet);

        String petJson = objectMapper.writeValueAsString(pet);
        var jsonResponse = mvc.perform(delete("/api/pets/{id}?userId={userId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        String responseBody = jsonResponse.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.isEmpty());
    }

    @Test
    void deletePetByName() throws Exception{
        Pet pet = new Pet(1L, "name", 1L);
        User user = new User(1L, "Max", "test@example.com", 20, new ArrayList<>());
        userService.createUser(user);
        petService.createPet(pet);

        String petJson = objectMapper.writeValueAsString(pet);
        var jsonResponse = mvc.perform(delete("/api/pets//by-name/{name}?userId={userId}", "name", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        String responseBody = jsonResponse.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.isEmpty());
    }
}