package com.manish.flightreservation.controllers;

import com.manish.flightreservation.dtos.ReservationUpdateRequest;
import com.manish.flightreservation.entities.Reservation;
import com.manish.flightreservation.repos.ReservationRepository;
import com.manish.flightreservation.util.EmailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ReservationRestController {
    private static final Logger LOGGER= LoggerFactory.getLogger(ReservationController.class);

   @Autowired
    private ReservationRepository reservationRepository;

   @RequestMapping("/reservations/{id}")
   public Reservation findReservation(@PathVariable("id") int id) {
       LOGGER.info("Finding reservation by id {}", id);
       return reservationRepository.findById(id).orElse(null);
   }


   @RequestMapping("/reservations")
   public Reservation updateReservation(@RequestBody ReservationUpdateRequest request) {
       LOGGER.info("Updating reservation {}", request);
       Reservation reservation = reservationRepository.findById(request.getId()).orElse(null);
       assert reservation != null;
       reservation.setNumberOfBags(request.getNumberOfBags());
       reservation.setCheckedIn(request.getCheckedIn());
       LOGGER.info("Saving Reservation: "+reservation);
       return reservationRepository.save(reservation);
   }

   
}
