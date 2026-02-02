package com.app.airopulse.controller;

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
            @RequestParam String flightId,
            @RequestParam String airline,
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam long departureTime,
            @RequestParam long arrivalTime
    ) {
        return flightService.createFlight(
                flightId,
                airline,
                source,
                destination,
                departureTime,
                arrivalTime
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
