package org.example.controller;

import org.example.entity.User;
import org.example.mapper.UserDtoMapper;
import org.example.model.UserDto;
import org.example.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserDtoMapper mapper;

    public UserController(UserService userService, UserDtoMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById( @PathVariable Long id)
    {
        return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<User>> getUsers()
    {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto userDto)
    {
        var user = mapper.userDto(userDto);
        return new ResponseEntity<>(userService.createUser(user),HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> putUser(@Valid @RequestBody UserDto userDto)
    {
        var user = mapper.userDto(userDto);
        return new ResponseEntity<>(userService.putUser(user),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById( @PathVariable Long id)
    {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/by-name/{name}")
    public ResponseEntity<Void> deleteUserByName( @PathVariable String name)
    {
        userService.deleteUserByName(name);
        return ResponseEntity.noContent().build();
    }

}

