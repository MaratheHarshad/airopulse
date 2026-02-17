package com.app.airopulse.domain.handler;

import com.app.airopulse.domain.event.FlightStatusChangedEvent;
import com.app.airopulse.integration.FlightSummaryClient;
import com.app.airopulse.model.FlightStatus;
import org.springframework.stereotype.Component;

@Component
public class FlightSummaryEventHandler {

    private final FlightSummaryClient summaryClient;

    public FlightSummaryEventHandler(FlightSummaryClient summaryClient) {
        this.summaryClient = summaryClient;
    }

    public void handle(FlightStatusChangedEvent event) {

        if (!isSummaryTrigger(event.getOldStatus(), event.getNewStatus())) {
            return;
        }

        try {
            String summary =
                    summaryClient.generateSummary(event.getFlight());

            event.getFlight().setLatestSummary(summary);

        } catch (Exception e) {
            event.getFlight().setLatestSummary(
                    "Flight " + event.getFlight().getFlightId() +
                    " is currently " + event.getNewStatus() +
                    ". AI summary unavailable."
            );
        }
    }

    private boolean isSummaryTrigger(FlightStatus oldStatus,
                                     FlightStatus newStatus) {

        if (oldStatus == newStatus) return false;

        return switch (newStatus) {
            case DELAYED, CANCELLED, LANDED -> true;
            default -> false;
        };
    }
}
