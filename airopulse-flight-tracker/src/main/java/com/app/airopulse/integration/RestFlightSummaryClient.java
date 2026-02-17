package com.app.airopulse.integration;

import com.app.airopulse.integration.dto.FlightSummaryRequest;
import com.app.airopulse.integration.dto.FlightSummaryResponse;
import com.app.airopulse.model.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestFlightSummaryClient implements FlightSummaryClient {

    private final RestTemplate restTemplate;
    private final String llmBaseUrl;
    private static final Logger log =
            LoggerFactory.getLogger(RestFlightSummaryClient.class);



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


        try {
            log.info("Calling LLM service for flight {}", flight.getFlightId());

            FlightSummaryResponse response =
                    restTemplate.postForObject(
                            llmBaseUrl + "/summarize",
                            request,
                            FlightSummaryResponse.class
                    );

            log.info("LLM response received for flight {}", flight.getFlightId());

            return response != null ? response.summary() : "Summary unavailable";

        } catch (Exception e) {
            log.error("LLM service call failed for flight {}", flight.getFlightId(), e);
            throw e;
        }
    }
}