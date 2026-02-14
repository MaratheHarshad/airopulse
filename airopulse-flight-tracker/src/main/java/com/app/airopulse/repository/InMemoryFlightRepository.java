package com.app.airopulse.repository;

import com.app.airopulse.model.Flight;
import com.app.airopulse.model.FlightStatus;
import com.app.airopulse.model.Route;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryFlightRepository {

    private final Map<String, Flight> flights = new ConcurrentHashMap<>();

    private final Map<String, Flight> flightsById = new ConcurrentHashMap<>();

    private final Map<Route, Set<String>> flightsByRoute = new ConcurrentHashMap<>();
    private final Map<FlightStatus, Set<String>> flightsByStatus = new ConcurrentHashMap<>();


    private final PriorityQueue<Flight> delayedFlights =
            new PriorityQueue<>(
                    Comparator.comparingLong(
                            (Flight f) -> f.getArrivalTime() - f.getDepartureTime()
                    ).reversed()
            );



    public void save(Flight flight) {
//        System.out.println(flight);
//        flights.put(flight.getFlightId(), flight);
        flightsById.put(flight.getFlightId(), flight);
        indexFlight(flight);
    }

    public Optional<Flight> findById(String flightId) {
        System.out.println(flightId);
        System.out.println(flightsById);
        return Optional.ofNullable(flightsById.get(flightId));
    }

    public Collection<Flight> findAll() {
        return flightsById.values();
    }

    public boolean exists(String flightId) {
        return flightsById.containsKey(flightId);
    }

    public void delete(String flightId) {
        flightsById.remove(flightId);
    }

    private void indexFlight(Flight flight) {

        flightsByRoute
                .computeIfAbsent(flight.getRoute(), r -> ConcurrentHashMap.newKeySet())
                .add(flight.getFlightId());

        flightsByStatus
                .computeIfAbsent(flight.getStatus(), s -> ConcurrentHashMap.newKeySet())
                .add(flight.getFlightId());

        if (flight.getStatus() == FlightStatus.DELAYED) {
            delayedFlights.offer(flight);
        }
    }

    private void removeFromStatusIndex(String flightId, FlightStatus oldStatus) {
        Set<String> ids = flightsByStatus.get(oldStatus);
        if (ids != null) {
            ids.remove(flightId);
        }
    }


    public void updateStatus(String flightId, FlightStatus newStatus) {

        Flight flight = flightsById.get(flightId);
        if (flight == null) return;

        FlightStatus oldStatus = flight.getStatus();
        if (oldStatus == newStatus) return;

        removeFromStatusIndex(flightId, oldStatus);

        flight.updateStatus(newStatus);

        flightsByStatus
                .computeIfAbsent(newStatus, s -> ConcurrentHashMap.newKeySet())
                .add(flightId);

        if (newStatus == FlightStatus.DELAYED) {
            delayedFlights.offer(flight);
        }
    }

    public Collection<Flight> findByRoute(Route route) {
        return flightsByRoute
                .getOrDefault(route, Set.of())
                .stream()
                .map(flightsById::get)
                .toList();
    }

    public Collection<Flight> findByStatus(FlightStatus status) {
        return flightsByStatus
                .getOrDefault(status, Set.of())
                .stream()
                .map(flightsById::get)
                .toList();
    }

    public List<Flight> getTopDelayedFlights(int limit) {
        return delayedFlights.stream()
                .limit(limit)
                .toList();
    }





}
