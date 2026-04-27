package com.manish.flightreservation.services;

import com.manish.flightreservation.entities.Users;
import com.manish.flightreservation.repos.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    public Users registerUser(Users user) {
        return usersRepository.save(user);
    }

    public Users findByEmail(String email,String password) {
        Users user = usersRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }


}
