package com.manish.flightreservation.repos;

import com.manish.flightreservation.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByPassengerEmail(String email);
}
