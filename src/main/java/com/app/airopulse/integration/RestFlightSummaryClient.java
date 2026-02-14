package com.app.airopulse.integration;

import com.app.airopulse.integration.dto.FlightSummaryRequest;
import com.app.airopulse.integration.dto.FlightSummaryResponse;
import com.app.airopulse.model.Flight;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestFlightSummaryClient implements FlightSummaryClient {

    private final RestTemplate restTemplate;
    private final String llmBaseUrl;
    

    public RestFlightSummaryClient(
            RestTemplate restTemplate,
            @Value("${llm.service.url}") String llmBaseUrl
    ) {
        this.restTemplate = restTemplate;
        this.llmBaseUrl = llmBaseUrl;
    }

    @Override
    public String generateSummary(Flight flight) {

        FlightSummaryRequest request = new FlightSummaryRequest(
                flight.getFlightId(),
                flight.getAirline(),
                flight.getRoute().source(),
                flight.getRoute().destination(),
                flight.getStatus().name(),
                flight.getDepartureTime(),
                flight.getArrivalTime()
        );

        FlightSummaryResponse response =
                restTemplate.postForObject(
                        llmBaseUrl + "/summarize",
                        request,
                        FlightSummaryResponse.class
                );

        return response != null ? response.summary() : "Summary unavailable";
    }
}