package com.app.airopulse.controller;

import com.app.airopulse.dto.FlightCreateRequest;
import com.app.airopulse.dto.FlightLocationUpdateRequest;
import com.app.airopulse.dto.FlightStatusUpdateRequest;
import com.app.airopulse.model.Flight;
import com.app.airopulse.model.FlightStatus;
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



    @PatchMapping("/{id}/status")
    public Flight updateStatus(
            @PathVariable String id,
            @RequestBody FlightStatusUpdateRequest request
    ) {
        return flightService.updateStatus(id, request);
    }


    @PatchMapping("/{id}/location")
    public Flight updateLocation(
            @PathVariable String id,
            @RequestBody FlightLocationUpdateRequest request
    ) {
        return flightService.updateLocation(id, request);
    }

    @GetMapping("/by-route")
    public Collection<Flight> getByRoute(
            @RequestParam String source,
            @RequestParam String destination
    ) {
        return flightService.getFlightsByRoute(source, destination);
    }

    @GetMapping("/by-status")
    public Collection<Flight> getByStatus(
            @RequestParam FlightStatus status
    ) {
        return flightService.getFlightsByStatus(status);
    }
    @GetMapping("/delayed")
    public Collection<Flight> getDelayedFlights(
            @RequestParam(defaultValue = "5") int limit
    ) {
        return flightService.getTopDelayedFlights(limit);
    }





}
