import os
import requests
from dotenv import load_dotenv

load_dotenv()


class LLMProvider:

    def __init__(self):
        self.api_key = os.getenv("GEMINI_API_KEY")
        if not self.api_key:
            raise RuntimeError("GEMINI_API_KEY not set")

        self.base_url = (
            "https://generativelanguage.googleapis.com/v1beta/models/"
            "gemini-2.5-flash:generateContent"
        )

    def generate_summary(self, flight_data: dict) -> str:

        prompt = f"""
        Generate a concise operational summary for the following flight.

        Flight ID: {flight_data['flightId']}
        Airline: {flight_data['airline']}
        Route: {flight_data['source']} to {flight_data['destination']}
        Status: {flight_data['status']}

        Keep it short and professional.
        """

        url = f"{self.base_url}?key={self.api_key}"

        payload = {
            "contents": [
                {
                    "parts": [
                        {"text": prompt}
                    ]
                }
            ]
        }

        response = requests.post(url, json=payload)
        response.raise_for_status()

        data = response.json()

        return data["candidates"][0]["content"]["parts"][0]["text"]
