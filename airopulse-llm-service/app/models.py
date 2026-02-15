from pydantic import BaseModel


class FlightSummaryRequest(BaseModel):
    flightId: str
    airline: str
    source: str
    destination: str
    status: str
    departureTime: int
    arrivalTime: int


class FlightSummaryResponse(BaseModel):
    summary: str