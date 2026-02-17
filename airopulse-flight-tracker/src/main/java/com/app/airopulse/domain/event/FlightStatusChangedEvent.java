package com.app.airopulse.domain.event;

import com.app.airopulse.model.Flight;
import com.app.airopulse.model.FlightStatus;

public class FlightStatusChangedEvent {

    private final Flight flight;
    private final FlightStatus oldStatus;
    private final FlightStatus newStatus;

    public FlightStatusChangedEvent(Flight flight,
                                    FlightStatus oldStatus,
                                    FlightStatus newStatus) {
        this.flight = flight;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    public Flight getFlight() {
        return flight;
    }

    public FlightStatus getOldStatus() {
        return oldStatus;
    }

    public FlightStatus getNewStatus() {
        return newStatus;
    }
}