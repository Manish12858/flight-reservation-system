package com.manish.flightcheckin.controllers;

import com.manish.flightcheckin.integration.ReservationRestClient;
import com.manish.flightcheckin.integration.dto.Reservation;
import com.manish.flightcheckin.integration.dto.ReservationUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CheckInController {

    @Autowired
    ReservationRestClient restClient;
    @RequestMapping("/showStartCheckIn")
    public String showStartCheckIn(){
        return "startCheckIn";
    }

    @RequestMapping("/startCheckIn")
    public String startCheckIn(@RequestParam("reservationId") int reservationId, Model model){
        Reservation reservation=restClient.findReservation(reservationId);
        model.addAttribute("reservation",reservation);
        return "displayReservationDetails";
    }

    @RequestMapping("/completeCheckIn")
    public String completeCheckIn(@RequestParam("reservationId") int reservationId,
                                  @RequestParam("numberOfBags") int numberOfBags,
                                  Model model) {

        // Create request object
        ReservationUpdateRequest request = new ReservationUpdateRequest();
        request.setId(reservationId);
        request.setCheckedIn(true);
        request.setNumberOfBags(numberOfBags);

        // Call REST API (FlightReservation service)
        Reservation updatedReservation = restClient.updateReservation(request);

        // Add success message to UI
        model.addAttribute("msg", "Check-In completed successfully for Reservation ID: " + reservationId);

        return "checkInConfirmation";
    }

}
