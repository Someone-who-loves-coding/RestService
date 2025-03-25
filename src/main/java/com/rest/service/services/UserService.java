package com.rest.service.services;

import com.rest.service.models.User;
import com.rest.service.repositories.CRUDRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final CRUDRepo repo;

    @Autowired
    public UserService(CRUDRepo repo) {
        this.repo = repo;
    }

    public List<User> getUsers() {
        return repo.findAll();
    }

    public User getUser(Long id) {
        return repo.findById(id).orElse(null);
    }

    public User getUserByUser(String user) {
        return repo.findUserByUser(user);
    }

    public User createUser(User user) {
        return repo.save(user);
    }

    public User updateUser(Long id, User user) {
        Optional<User> existingUser = repo.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setUser(user.getUser()); // Update only relevant fields
            return repo.save(updatedUser);
        }
        return null; // Handle not found case better in the controller
    }

    public boolean deleteUser(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
