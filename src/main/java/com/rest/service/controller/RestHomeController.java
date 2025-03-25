package com.rest.service.controller;

import com.rest.service.models.User;
import com.rest.service.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "User Management", description = "Endpoints for managing users")
public class RestHomeController {

    private final UserService userService;

    @Autowired
    public RestHomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @Operation(summary = "Get all users", description = "Fetches the list of all registered users")
    public ResponseEntity<List<User>> users() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Get user by ID", description = "Fetches details of a specific user by their ID")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUser(id);
        return (user != null) ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }


    @PostMapping("/users")
    @Operation(summary = "Create a new user", description = "Adds a new user to the database")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if (userService.getUserByUser(user.getUser()) == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/users/{id}")
    @Operation(summary = "Update user", description = "Updates an existing user by ID")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/users/{id}")
    @Operation(summary = "Partially update user", description = "Partially updates user details by ID")
    public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userService.getUser(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        if (user.getUser() != null) {
            existingUser.setUser(user.getUser());
        }
        return ResponseEntity.ok(userService.createUser(existingUser));
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user by ID")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.getUser(id) != null) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
