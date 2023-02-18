package com.rokib.jwt.application.repository;

import com.rokib.jwt.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Integer> {
    Optional<Object> findByEmail(String username);
}
