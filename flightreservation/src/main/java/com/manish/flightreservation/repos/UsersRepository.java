package com.manish.flightreservation.repos;

import com.manish.flightreservation.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByEmail(String email);
}
