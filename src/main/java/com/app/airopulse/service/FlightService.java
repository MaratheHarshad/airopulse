package com.app.airopulse.service;

import com.app.airopulse.model.Flight;
import com.app.airopulse.model.Route;
import com.app.airopulse.repository.InMemoryFlightRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FlightService {

    private final InMemoryFlightRepository repository;

    public FlightService(InMemoryFlightRepository repository) {
        this.repository = repository;
    }

    public Flight createFlight(String flightId,
                               String airline,
                               String source,
                               String destination,
                               long departureTime,
                               long arrivalTime) {

        if (repository.exists(flightId)) {
            throw new IllegalArgumentException("Flight already exists: " + flightId);
        }

        Flight flight = new Flight(
                flightId,
                airline,
                new Route(source, destination),
                departureTime,
                arrivalTime
        );

        repository.save(flight);
        return flight;
    }

    public Flight getFlight(String flightId) {
        return repository.findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found: " + flightId));
    }

    public Collection<Flight> getAllFlights() {
        return repository.findAll();
    }
}
