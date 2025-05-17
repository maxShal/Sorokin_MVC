package org.example.controller;

import org.example.entity.Pet;
import org.example.mapper.PetDtoMapper;
import org.example.model.PetDto;
import org.example.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    @Autowired
    private final PetService petService;
    private final PetDtoMapper mapper;


    public PetController(PetService petService, PetDtoMapper mapper) {
        this.petService = petService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDto>  getPetById(@PathVariable Long id)
    {
        return new ResponseEntity<>(mapper.toDto(petService.getPetById(id)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PetDto>> getAllPets() {
        List<Pet> pets = petService.getAllPets();
        List<PetDto> petDtos = pets.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(petDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PetDto> createPet(@Valid @RequestBody Pet pet)
    {
        return new ResponseEntity<>(mapper.toDto(petService.createPet(pet)),HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PetDto> putPet(@Valid @RequestBody Pet pet)
    {
        return new ResponseEntity<>(mapper.toDto(petService.putPet(pet)),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetById(@PathVariable Long id, @RequestParam Long userId)
    {
        petService.deletePetById(id, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/by-name/{name}")
    public ResponseEntity<Void> deletePetByName(@PathVariable String name, @RequestParam Long userId)
    {
        petService.deletePetByName(name, userId);
        return ResponseEntity.noContent().build();
    }
}
