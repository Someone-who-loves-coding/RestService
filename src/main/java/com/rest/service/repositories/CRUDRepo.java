package com.rest.service.repositories;

import com.rest.service.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CRUDRepo extends JpaRepository<User, Long> {
    User findUserByUser(String user);
}
