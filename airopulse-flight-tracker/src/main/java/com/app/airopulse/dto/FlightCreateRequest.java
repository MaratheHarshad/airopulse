package com.app.airopulse.dto;

public record FlightCreateRequest(
        String flightId,
        String airline,
        String source,
        String destination,
        long departureTime,
        long arrivalTime
) {}
