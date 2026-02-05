package com.app.airopulse.dto;

import com.app.airopulse.model.FlightStatus;

public record FlightStatusUpdateRequest(
        FlightStatus status
) {}
