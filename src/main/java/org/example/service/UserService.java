package org.example.service;

import org.example.entity.User;
import org.example.exception.NotFoundException;
import org.example.exception.NotValidException;
import org.example.model.PetDto;
import org.example.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService
{
    private HashMap<Long, User> userHashMap = new HashMap<>();

    public HashMap<Long, User> getUserHashMap() {
        return userHashMap;
    }

    public User getUserById(Long id)
    {
        if (id == null || id < 0) {
            throw new NotValidException("User id must be positive");
        }
        if(!userHashMap.containsKey(id)){
            throw new NotFoundException("User not found with id: " + id);
        }
        return userHashMap.getOrDefault(id, null);
    }

    public List<User> getAllUsers()
    {
        return new ArrayList<>(userHashMap.values());
    }

    public User createUser(User userDto)
    {
        if(userHashMap.containsKey(userDto.getId())){
            throw new NotValidException("Already exists");
        }
        if (userDto.getId() == null || userDto.getId() < 0) {
            throw new NotValidException("User id must be positive");
        }
        if (userDto.getName() == null || userDto.getName().isBlank()) {
            throw new NotValidException("User name is required");
        }
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new NotValidException("Email name is required");
        }
        if (userDto.getAge() == null || userDto.getAge() < 0) {
            throw new NotValidException("Age id must be positive");
        }
        User user = new User(userDto.getId(), userDto.getName(), userDto.getEmail(), userDto.getAge(), userDto.getPets());
        userHashMap.put(user.getId(), user);
        return user;
    }

    public User putUser(User userDto)
    {
        if (userDto.getId() == null || userDto.getId() < 0) {
            throw new NotValidException("User id must be positive");
        }
        if(!userHashMap.containsKey(userDto.getId())){
            throw new NotFoundException("User not found with id: " + userDto.getId());
        }
        if (userDto.getName() == null || userDto.getName().isBlank()) {
            throw new NotValidException("User name is required");
        }
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new NotValidException("Email name is required");
        }
        if (userDto.getAge() == null || userDto.getAge() < 0) {
            throw new NotValidException("Age id must be positive");
        }
        User user = userHashMap.get(userDto.getId());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPets(userDto.getPets());
        userHashMap.put(userDto.getId(),user);
        return user;
    }

    public void deleteUserById(Long id)
    {
        if (id == null || id < 0) {
            throw new NotValidException("User id must be positive");
        }
        if(!userHashMap.containsKey(id)){
            throw new NotFoundException("User not found with id: " + id);
        }
        userHashMap.remove(id);
    }

    public void deleteUserByName(String name)
    {
        if (name == null || name.isBlank()) {
            throw new NotValidException("User name is required");
        }
        userHashMap.entrySet().removeIf(e -> e.getValue().getName().equals(name));
    }
}
