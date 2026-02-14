package com.app.airopulse.integration.dto;

public record FlightSummaryRequest(
        String flightId,
        String airline,
        String source,
        String destination,
        String status,
        Long departureTime,
        Long arrivalTime
) {}