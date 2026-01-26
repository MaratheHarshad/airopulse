package com.app.airopulse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flights")
public class FlighController {

    @GetMapping("/")
    public String getFlightStatus() {
        return "Flight status endpoint is working!";
    }
}
