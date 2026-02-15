from app.models import FlightSummaryRequest
from app.models import FlightSummaryResponse
from fastapi import FastAPI


app = FastAPI()

@app.post("/summarize", response_model=FlightSummaryResponse)
async def summarize(request: FlightSummaryRequest):

    # Deterministic mock response
    summary = (
        f"Flight {request.flightId} operated by {request.airline} "
        f"from {request.source} to {request.destination} "
        f"is currently in status {request.status}."
    )

    return FlightSummaryResponse(summary=summary)