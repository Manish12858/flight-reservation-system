package com.manish.flightreservation.services;

import com.manish.flightreservation.dtos.ReservationRequest;
import com.manish.flightreservation.entities.Reservation;
import jakarta.servlet.http.HttpServletResponse;

public interface ReservationService {
    Reservation bookFlight(ReservationRequest request);


    void generateItinerary(int reservationId, HttpServletResponse response);
}
