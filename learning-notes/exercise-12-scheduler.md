# Exercise 12 — OSGi Scheduler

## Challenge
Run code periodically in the background — find content and log it — on a configurable schedule.

## Interview approach
"I use the Sling **whiteboard scheduler**: register a `Runnable` OSGi component with a
**`scheduler.expression`** service property (cron); Sling runs it on that schedule. Cron + an
enabled flag are configurable via `@ObjectClassDefinition`. The scheduler stays thin —
delegates to a service that uses a **service-user resolver**. Background jobs have no request,
so a service user is mandatory."

## Files
- `schedulers/ProductReportScheduler.java` — `Runnable` + cron OCD + `run()`
- `ProductReportSchedulerTest.java` — enabled/disabled

## New concepts
- Whiteboard scheduler: `@Component(service = Runnable.class)` + `scheduler.expression`
- OCD naming trick: `scheduler_expression()` → property `scheduler.expression` (underscore → dot)
- Quartz cron (7 fields incl. seconds): `0 0/1 * * * ?` = every minute
- `scheduler.concurrent` (prevent overlap); enabled kill-switch
- No request → service user for repo access

## Follow-up questions
- **How does Sling schedule it?** Whiteboard picks up any `Runnable` with `scheduler.expression`/`scheduler.period`.
- **Why a service user here?** No request/session in a background thread.
- **Cluster (multiple nodes)?** A plain scheduler runs on every node; for run-once use a **Sling Job** (JobManager) or leader election.
- **How to test?** Test `run()` directly (mock the service) + the enabled flag, not the timer.

## Best practices & memory hooks
- Memory hook: **"`Runnable` + `scheduler.expression` = a scheduled job."**
- Memory hook: **"underscore in OCD → dot in property."**
- Keep schedulers thin; always a service user + enabled flag; be cluster-aware (Sling Job for once-globally).
