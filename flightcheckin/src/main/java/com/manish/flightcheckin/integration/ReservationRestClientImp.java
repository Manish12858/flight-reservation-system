package com.manish.flightcheckin.integration;

import com.manish.flightcheckin.integration.dto.Reservation;
import com.manish.flightcheckin.integration.dto.ReservationUpdateRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class ReservationRestClientImp implements ReservationRestClient {
    private static final String RESERVATION_REST_URL="http://localhost:8080/reservations";

    public Reservation findReservation(int id){

        RestTemplate restTemplate = new RestTemplate();
        Reservation reservation= restTemplate.getForObject(RESERVATION_REST_URL + "/"+id, Reservation.class);

        return reservation;
    }

    public Reservation updateReservation(ReservationUpdateRequest request){
        RestTemplate restTemplate = new RestTemplate();
        Reservation reservation=  restTemplate.postForObject(RESERVATION_REST_URL, request, Reservation.class);
        return reservation;
    }
}
