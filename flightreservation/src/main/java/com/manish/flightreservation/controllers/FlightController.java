package com.manish.flightreservation.controllers;


import com.manish.flightreservation.entities.Flight;
import com.manish.flightreservation.repos.FlightRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;



@Controller
public class FlightController {

   @Autowired
    FlightRepository flightRepository;

   private static final Logger LOGGER=  LoggerFactory.getLogger(FlightController.class);

    @GetMapping("/findFlights")
    public String showFindFlight() {

        return "findFlights";
    }

    @PostMapping("/findFlights")
    public String findFlights(@RequestParam("from") String from, @RequestParam("to") String to,
                              @RequestParam("departureDate") @DateTimeFormat(pattern = "MM-dd-yyyy")
                              Date departureDate, ModelMap modelMap ) {
        LOGGER.info("Inside findFlights() from: " + from + " to: " + to+ " departure Date: " + departureDate);
        List<Flight> flights=flightRepository.findByDepartureCityIgnoreCaseAndArrivalCityIgnoreCaseAndDateOfDeparture(from, to , departureDate);
        LOGGER.info("Flight found are: " + flights);
        modelMap.addAttribute("flights", flights);
        return "displayFlights";
    }


}
