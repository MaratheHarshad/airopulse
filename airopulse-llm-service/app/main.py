from app.models import FlightSummaryRequest
from app.models import FlightSummaryResponse
from fastapi import FastAPI, HTTPException
from app.llm_provider import LLMProvider


app = FastAPI()
llm_provider = LLMProvider()


@app.post("/summarize", response_model=FlightSummaryResponse)
async def summarize(request: FlightSummaryRequest):


   try:
        summary = llm_provider.generate_summary(request.dict())
        return FlightSummaryResponse(summary=summary)

   except Exception as e:
    import traceback
    traceback.print_exc()
    raise HTTPException(status_code=500, detail=str(e))
