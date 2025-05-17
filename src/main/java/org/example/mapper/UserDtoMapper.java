package org.example.mapper;

import org.example.entity.User;
import org.example.model.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper
{
    public UserDto userDto(User user)
    {
        UserDto userdto = new UserDto();
        userdto.setId(user.getId());
        userdto.setAge(user.getAge());
        userdto.setName(user.getName());
        userdto.setEmail(user.getEmail());
        userdto.setPets(user.getPets());
        return userdto;
    }
}
