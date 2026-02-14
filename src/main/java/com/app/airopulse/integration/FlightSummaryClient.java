package com.app.airopulse.integration;

import com.app.airopulse.model.Flight;

public interface FlightSummaryClient {

    String generateSummary(Flight flight);
}