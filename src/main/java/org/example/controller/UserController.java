package org.example.controller;

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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById( @PathVariable Long id)
    {
        return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers()
    {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user)
    {
        return new ResponseEntity<>(userService.createUser(user),HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDto> putUser(@Valid @RequestBody UserDto user)
    {
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

