package org.example.mapper;

import org.example.entity.Pet;
import org.example.model.PetDto;
import org.springframework.stereotype.Component;

@Component
public class PetDtoMapper
{
    public Pet toDto(PetDto petDto)
    {
        Pet pet = new Pet();
        pet.setName(petDto.getName());
        pet.setId(petDto.getId());
        pet.setUserId(petDto.getUserId());
        return pet;
    }
}
