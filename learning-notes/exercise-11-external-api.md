# Exercise 11 — External API Integration

## Challenge
An OSGi service calls an external HTTP API (configurable URL) with timeouts and error handling
(404/500/timeout/bad JSON), logs failures, and returns **fallback** data so a failure never
breaks the page.

## Interview approach
"Isolate the external call behind a service with a **configurable base URL and timeouts**
(never hardcode either — a hung call with no timeout is an outage). Check the HTTP status
explicitly, and wrap everything so any failure is **caught, logged, and returns a safe
fallback**. Never return null; the page degrades gracefully."

## Files
- `services/ExternalProductService.java` + `impl/ExternalProductServiceConfig.java` + `impl/ExternalProductServiceImpl.java`
- `models/ExternalProductModel.java` — `@OSGiService` consumer
- `ui.apps/.../external-product/*`
- `ExternalProductServiceImplTest.java` — tests `toProduct` + `fallback` (no network)

## New concepts
- Connect + read timeouts (two independent timeouts)
- Explicit status handling (`HTTP_OK` vs everything else)
- Fallback / graceful degradation; never return null
- Logging levels: `warn` for 404-ish, `error` for exceptions
- Split I/O from mapping (`toProduct`, `fallback`) for testability

## Follow-up questions
- **Stop a failure breaking the page?** Timeouts + try/catch + fallback + null-safety (proven: id=999999 → fallback).
- **Why timeouts?** No timeout → hung call → thread-pool exhaustion → whole site stalls.
- **Caching?** Cache responses (TTL) to cut latency and shield the API.
- **HttpURLConnection vs HttpClient?** HttpClient = pooling/retries/config (prod); URLConnection = dependency-free.
- **More resilience?** Retry+backoff, circuit breaker, bulkheads.

## Best practices & memory hooks
- Memory hook: **"config the URL, cap with timeouts, check status, catch everything, log it, fall back."**
- Never hardcode URLs/timeouts; never return null from a view-facing service.
- Treat every external dependency as *will fail eventually*.
