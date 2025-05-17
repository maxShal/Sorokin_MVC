package org.example.mapper;

import org.example.entity.Pet;
import org.example.model.PetDto;
import org.springframework.stereotype.Component;

@Component
public class PetDtoMapper
{
    public PetDto toDto(Pet pet)
    {
        PetDto petdto = new PetDto();
        petdto.setName(pet.getName());
        petdto.setId(pet.getId());
        petdto.setUserId(pet.getUserId());
        return petdto;
    }
}
