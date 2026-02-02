package com.app.airopulse.model;


public class Flight {

    private final String flightId;
    private final String airline;
    private final Route route;

    private long departureTime;
    private long arrivalTime;
    private FlightStatus status;
    private GeoLocation location;

    public Flight(String flightId,
                  String airline,
                  Route route,
                  long departureTime,
                  long arrivalTime) {

        this.flightId = flightId;
        this.airline = airline;
        this.route = route;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.status = FlightStatus.SCHEDULED;
    }

    public String getFlightId() {
        return flightId;
    }

    public String getAirline() {
        return airline;
    }

    public Route getRoute() {
        return route;
    }

    public long getDepartureTime() {
        return departureTime;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public GeoLocation getLocation() {
        return location;
    }

    // State transition methods (important)
    public void updateStatus(FlightStatus newStatus) {
        this.status = newStatus;
    }

    public void updateLocation(GeoLocation location) {
        this.location = location;
    }

    public void updateArrivalTime(long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
