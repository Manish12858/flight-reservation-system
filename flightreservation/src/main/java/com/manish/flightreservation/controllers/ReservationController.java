package com.manish.flightreservation.controllers;


import com.manish.flightreservation.dtos.ReservationRequest;
import com.manish.flightreservation.entities.Flight;
import com.manish.flightreservation.entities.Reservation;
import com.manish.flightreservation.repos.FlightRepository;
import com.manish.flightreservation.repos.ReservationRepository;
import com.manish.flightreservation.services.ReservationService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReservationController {


    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    FlightRepository flightRepository;

    @Autowired
    ReservationService reservationService;

    private static final Logger LOGGER=  LoggerFactory.getLogger(ReservationController.class);

    @GetMapping("/showCompleteReservation")
    public String showCompleteReservation(@RequestParam("flightId") int flightId, ModelMap modelMap) {
        LOGGER.info("showCompleteReservation() invoked with flightId: " + flightId);
         Flight flight =flightRepository.findById(flightId).get();
         modelMap.addAttribute("flight", flight);
         return "completeReservation";
    }

    @PostMapping("/completeReservation")
    public String completeReservation(ReservationRequest request, ModelMap modelMap) {

        LOGGER.info("completeReservation()  " + request);

        Reservation reservation = reservationService.bookFlight(request);

        //  DEBUG LOG
        LOGGER.info("Reservation returned: " + reservation);

        //  NULL CHECK (IMPORTANT)
        if (reservation == null) {
            modelMap.addAttribute("msg", "Error: Reservation could not be created.");
            return "reservationConfirmation";
        }

        modelMap.addAttribute("reservation", reservation);
        modelMap.addAttribute("msg",
                "Reservation created successfully. Your Booking ID is: " + reservation.getId());

        return "reservationConfirmation";
    }

    @GetMapping("/downloadItinerary")
    public void downloadItinerary(@RequestParam("reservationId") int id,
                                  HttpServletResponse response) {

        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=itinerary.pdf");

            reservationService.generateItinerary(id, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
