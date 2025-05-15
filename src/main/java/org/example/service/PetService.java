package org.example.service;

import org.example.exception.NotFoundException;
import org.example.exception.NotValidException;
import org.example.model.PetDto;
import org.example.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {
    private HashMap<Long, PetDto> petHashMap = new HashMap<>();
    private UserService userService;

    @Autowired
    public PetService(UserService userService) {
        this.userService = userService;
    }

    public PetDto getPetById(Long id) {
        if (id == null || id < 0) {
            throw new NotValidException("Pet id must be positive");
        }
        if (!petHashMap.containsKey(id)) {
            throw new NotFoundException("Pet not found with id: " + id);
        }
        return petHashMap.getOrDefault(id, null);
    }

    public List<PetDto> getAllPets() {
        return new ArrayList<>(petHashMap.values());
    }

    public PetDto createPet(PetDto petDto) {
        if (petHashMap.containsKey(petDto.getId())) {
            throw new NotValidException("Is already exist");
        }
        if (petDto.getId() == null || petDto.getId() < 0) {
            throw new NotValidException("Pet id must be positive");
        }
        if (petDto.getName() == null || petDto.getName().isBlank()) {
            throw new NotValidException("Pet name is required");
        }
        if (petDto.getUserId() == null || petDto.getUserId() < 0) {
            throw new NotValidException("User id must be positive");
        }
        petHashMap.put(petDto.getId(), petDto);
        UserDto user = userService.getUserById(petDto.getUserId());
        user.getPets().add(petDto);
        userService.putUser(user);
        return petDto;
    }

    public PetDto putPet(PetDto petDto) {
        if (petDto.getId() == null || petDto.getId() < 0) {
            throw new NotValidException("Pet id must be positive");
        }
        if (petDto.getName() == null || petDto.getName().isBlank()) {
            throw new NotValidException("Pet name is required");
        }
        if (petDto.getUserId() == null || petDto.getUserId() < 0) {
            throw new NotValidException("User id must be positive");
        }
        PetDto pet = petHashMap.get(petDto.getId());
        pet.setName(petDto.getName());
        UserDto user = userService.getUserById(petDto.getUserId());
        //pet.setUserId(userId);
        petHashMap.put(petDto.getId(), pet);
        List<PetDto> updatePets = user.getPets()
                .stream()
                .map(p -> p.getId().equals(petDto.getId()) ? pet : p)
                .toList();
        user.setPets(updatePets);
        userService.putUser(user);

        return pet;
    }

    public void deletePetById(Long id, Long userId) {
        if (id == null || id < 0) {
            throw new NotValidException("Pet id must be positive");
        }
        petHashMap.remove(id);
        UserDto user = userService.getUserById(userId);
        user.getPets().removeIf(petDto -> petDto.getId().equals(id));
        userService.putUser(user);

    }

    public void deletePetByName(String name, Long userId) {
        if (name == null || name.isBlank()) {
            throw new NotValidException("Pet name is required");
        }
        petHashMap.entrySet().removeIf(e -> e.getValue().getName().equals(name) && e.getValue().getUserId().equals(userId));
        UserDto user = userService.getUserById(userId);
        user.getPets().removeIf(petDto -> petDto.getName().equals(name));
        userService.putUser(user);
    }
}
