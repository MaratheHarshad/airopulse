package com.app.airopulse.controller;

import com.app.airopulse.dto.FlightCreateRequest;
import com.app.airopulse.model.Flight;
import com.app.airopulse.service.FlightService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public Flight createFlight(
            @RequestBody FlightCreateRequest flightCreateRequest
            ) {
        return flightService.createFlight(
                flightCreateRequest
        );
    }

    @GetMapping("/{id}")
    public Flight getFlight(@PathVariable String id) {

        return flightService.getFlight(id);
    }

    @GetMapping
    public Collection<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }
}
