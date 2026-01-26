package com.app.airopulse.repository;

import com.app.airopulse.model.Flight;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryFlightRepository {

    private final Map<String, Flight> flights = new ConcurrentHashMap<>();

    public void save(Flight flight) {
        flights.put(flight.getFlightId(), flight);
    }

    public Optional<Flight> findById(String flightId) {
        return Optional.ofNullable(flights.get(flightId));
    }

    public Collection<Flight> findAll() {
        return flights.values();
    }

    public boolean exists(String flightId) {
        return flights.containsKey(flightId);
    }

    public void delete(String flightId) {
        flights.remove(flightId);
    }
}
