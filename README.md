# AiroPulse

**AiroPulse – Real-Time Flight State Engine with LLM-Based Summarization
**

AiroPulse is a backend-focused system designed to track live flight states and generate real-time, human-readable operational summaries using an LLM-powered microservice. The project emphasizes **production-style backend engineering**, **efficient data structures**, **clean system design**, and **responsible AI integration**.

This project is intentionally scoped to resemble an internal platform component rather than a consumer-facing application.

---

## Problem Statement

Modern airline and logistics systems operate on continuously changing state: flights depart, get delayed, rerouted, or cancelled due to operational and environmental factors. While raw flight data is abundant, backend systems often struggle with:

- Efficiently tracking and querying **real-time flight state**
- Supporting **low-latency access patterns** (by route, status, delay)
- Converting raw operational data into **clear, human-readable summaries**
- Integrating AI capabilities without tightly coupling them to core systems

Most demo projects focus on CRUD or UI, while real-world backend systems focus on **state management, performance, and service boundaries**.

**AiroPulse** addresses this by:

- Maintaining an in-memory, optimized flight state engine
- Exposing query-driven REST APIs
- Offloading AI-based summarization to a dedicated LLM microservice
- Separates flight state management from AI summarization to avoid coupling operational logic with model behavior.

### Design Constraints

- System is optimized for low-latency read-heavy workloads.
- Core logic remains independent of persistence.
- AI integration must not impact flight state reliability.
- Services must remain independently deployable.

---

## High-Level Architecture

AiroPulse is designed as a small, service-oriented system with clear responsibilities.

```
                    ┌────────────────────────┐
                    │  Flight Data Source    │
                    │ (Mock / External API) │
                    └───────────┬──────────┘
                                │
                                ▼
                    ┌────────────────────────┐
                    │  Flight Tracker Service│
                    │  (Java / Spring Boot) │
                    │                        │
                    │ - In-memory flight     │
                    │   state engine         │
                    │ - DSA-optimized        │
                    │   queries              │
                    │ - REST APIs            │
                    └───────────┬──────────┘
                                │
              Flight State Change│Summary Request
                                │
                                ▼
                    ┌────────────────────────┐
                    │ LLM Summary Service    │
                    │ (FastAPI / Python)    │
                    │                        │
                    │ - Structured prompts  │
                    │ - Stateless design    │
                    │ - Replaceable LLM     │
                    └───────────┬──────────┘
                                │
                                ▼
                    ┌────────────────────────┐
                    │  REST / API Clients    │
                    │  (Internal / External)│
                    └────────────────────────┘
```

## Architectural Evolution

The system was implemented incrementally:

1. Phase 1 – Core flight domain modeling and state management
2. Phase 2 – DSA-driven in-memory indexing for optimized queries
3. Phase 3 – Repository restructuring into a mono-repo
4. Phase 4 – LLM microservice integration with failure isolation

## Each phase focused on improving either correctness, performance, or extensibility.

## Key Design Principles

- **State over CRUD**: The system focuses on managing evolving flight state rather than simple database operations.
- **DSA-first design**: In-memory structures are chosen based on access patterns and performance needs.
- **Service separation**: Core flight tracking is decoupled from AI logic.
- **Replaceable AI layer**: The LLM service can be swapped or upgraded without impacting the core system.
- **Production mindset**: Clear APIs, failure handling, and containerized deployment.

---

## Core Components (Brief)

### Flight Tracker Service (Java)

- Maintains active flight state in memory
- Supports efficient queries by route, status, and delay
- Exposes REST APIs for downstream consumers

### LLM Summary Service

- Generates concise operational summaries from structured flight data
- Designed as a stateless microservice
- Invoked only on meaningful flight state changes

---

## Performance Characteristics

| Query Type             | Time Complexity |
| ---------------------- | --------------- |
| Flight by ID           | O(1)            |
| Flights by Route       | O(k)            |
| Flights by Status      | O(k)            |
| Insert into Delay Heap | O(log n)        |

---

## Failure Handling Strategy

- LLM calls are protected with connection and read timeouts.
- Summary generation failures do not block flight state updates.
- Fallback summaries are attached when AI service is unavailable.
- Core engine operates independently of AI availability.

---

## Scope Control

This project intentionally does **not** include:

- Frontend UI or dashboards
- Authentication or user management
- Real airline API integrations
- Cloud-provider-specific infrastructure

The focus is on **backend correctness, performance, and design clarity**.

---

## Future Extensions (Not Implemented)

- WebSocket-based real-time updates
- Event-driven architecture using Kafka
- Persistent storage for historical flight analytics
- Alerting and anomaly detection

---

## Trade-offs

- In-memory storage prioritizes simplicity over durability.
- Synchronous LLM calls were chosen for clarity; async/event-driven design can be added later.
- No persistence layer included in current scope.

---

## Running the Project

### Flight Tracker Service

cd airopulse-flight-tracker  
mvn spring-boot:run

### LLM Service

cd airopulse-llm-service  
python -m venv venv  
source venv/Scripts/activate  
pip install -r requirements.txt  
uvicorn app.main:app --reload --port 8000

---

## Why This Project

AiroPulse is designed to explore backend system design patterns including state management, read-optimized indexing, and AI service integration.
