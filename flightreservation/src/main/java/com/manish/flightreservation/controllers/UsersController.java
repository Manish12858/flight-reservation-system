package com.manish.flightreservation.controllers;



import com.manish.flightreservation.entities.Reservation;
import com.manish.flightreservation.entities.Users;
import com.manish.flightreservation.repos.ReservationRepository;
import com.manish.flightreservation.services.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UsersController {

    @Autowired
    private UsersService service;

    @Autowired
    ReservationRepository reservationRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/showLogin")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/showRegister")
    public String showRegister() {
        return "register";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        ModelMap modelMap,
                        HttpSession session) {

        Users user = service.findByEmail(email, password);

        if (user != null) {

            //  store user in session
            session.setAttribute("user", user);

            return "redirect:/dashboard";

        } else {
            modelMap.addAttribute("msg", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, ModelMap modelMap) {

        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            return "redirect:/showLogin";
        }

        //  CORE LOGIC
        List<Reservation> reservations =
                reservationRepository.findByPassengerEmail(user.getEmail());

        modelMap.addAttribute("reservations", reservations);
        modelMap.addAttribute("user", user);

        return "dashboard";
    }



    @PostMapping("/register")
    public String register(@ModelAttribute Users user, ModelMap modelMap) {
        service.registerUser(user);
        modelMap.addAttribute("msg", "Registration successful. Please login.");

        return "login";
    }

}
