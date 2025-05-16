package org.example.mapper;

import org.example.entity.User;
import org.example.model.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper
{
    public User userDto(UserDto user)
    {
        User userdto = new User();
        userdto.setId(user.getId());
        userdto.setAge(user.getAge());
        userdto.setName(user.getName());
        userdto.setEmail(user.getEmail());
        userdto.setPets(user.getPets());
        return userdto;
    }
}
