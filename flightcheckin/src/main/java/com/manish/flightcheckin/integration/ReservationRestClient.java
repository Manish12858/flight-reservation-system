package com.manish.flightcheckin.integration;

import com.manish.flightcheckin.integration.dto.Reservation;
import com.manish.flightcheckin.integration.dto.ReservationUpdateRequest;

public interface ReservationRestClient {
    public Reservation findReservation(int id);
    public Reservation updateReservation(ReservationUpdateRequest request);
}
