package com.app.airopulse.service;

import com.app.airopulse.domain.event.FlightStatusChangedEvent;
import com.app.airopulse.domain.handler.FlightSummaryEventHandler;
import com.app.airopulse.dto.FlightLocationUpdateRequest;
import com.app.airopulse.dto.FlightStatusUpdateRequest;
import com.app.airopulse.integration.FlightSummaryClient;
import com.app.airopulse.model.Flight;
import com.app.airopulse.model.FlightStatus;
import com.app.airopulse.model.GeoLocation;
import com.app.airopulse.model.Route;
import com.app.airopulse.repository.InMemoryFlightRepository;
import org.springframework.stereotype.Service;
import com.app.airopulse.dto.FlightCreateRequest;

import java.util.Collection;

@Service
public class FlightService {

    private final InMemoryFlightRepository repository;
    private final FlightSummaryClient summaryClient;
    private final FlightSummaryEventHandler summaryEventHandler;


    public FlightService(InMemoryFlightRepository repository, FlightSummaryClient summaryClient, FlightSummaryEventHandler summaryEventHandler) {
        this.repository = repository;
        this.summaryClient = summaryClient;
        this.summaryEventHandler = summaryEventHandler;
    }

    public Flight createFlight(FlightCreateRequest flightCreateRequest) {

        if (repository.exists(flightCreateRequest.flightId())) {
            throw new IllegalArgumentException("Flight already exists: " + flightCreateRequest.flightId());
        }

        Flight flight = new Flight(
                flightCreateRequest.flightId(),
                flightCreateRequest.airline(),
                new Route(flightCreateRequest.source(), flightCreateRequest.destination()),
                flightCreateRequest.departureTime(),
                flightCreateRequest.arrivalTime()
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




    public Flight updateStatus(String flightId, FlightStatusUpdateRequest request) {

        Flight flight = getFlight(flightId);
        FlightStatus oldStatus = flight.getStatus();

        repository.updateStatus(flightId, request.status());

        FlightStatus newStatus = request.status();

        FlightStatusChangedEvent event =
                new FlightStatusChangedEvent(flight, oldStatus, newStatus);

        summaryEventHandler.handle(event);

        return flight;

    }


    public Flight updateLocation(String flightId, FlightLocationUpdateRequest request) {

        Flight flight = getFlight(flightId);

        flight.updateLocation(
                new GeoLocation(request.latitude(), request.longitude())
        );

        return flight;
    }

    public Collection<Flight> getFlightsByRoute(String source, String destination) {
        return repository.findByRoute(new Route(source, destination));
    }

    public Collection<Flight> getFlightsByStatus(FlightStatus status) {
        return repository.findByStatus(status);
    }

    public Collection<Flight> getTopDelayedFlights(int limit) {
        return repository.getTopDelayedFlights(limit);
    }



}
