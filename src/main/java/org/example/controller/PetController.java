package org.example.controller;

import org.example.model.PetDto;
import org.example.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetService petService;


    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDto>  getPetById(@PathVariable Long id)
    {
        return new ResponseEntity<>(petService.getPetById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PetDto>>  getAllPet()
    {
        return new ResponseEntity<>(petService.getAllPets(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PetDto> createPet(@Valid @RequestBody PetDto pet)
    {
        return new ResponseEntity<>(petService.createPet(pet),HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PetDto> putPet(@Valid @RequestBody PetDto pet)
    {
        return new ResponseEntity<>(petService.putPet(pet),HttpStatus.OK);
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
