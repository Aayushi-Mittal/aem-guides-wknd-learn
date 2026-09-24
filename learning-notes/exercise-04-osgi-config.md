# Exercise 4 — Configurable OSGi Service

## Challenge
A `GreetingService` with `getGreeting(name)` and a configurable prefix ("Hello" → "Hello X"),
changeable **without code changes**.

## Interview approach
"I register the impl with `@Component`, externalise the prefix into an `@ObjectClassDefinition`
config schema, bind it with `@Designate`, and read it in `@Activate` — plus `@Modified` so it
re-reads config **at runtime with no redeploy**. Config that varies per environment lives in
`.cfg.json` / the Configuration Manager, never hardcoded."

## Files
- `services/GreetingService.java` — interface
- `services/impl/GreetingServiceConfig.java` — `@ObjectClassDefinition`
- `services/impl/GreetingServiceImpl.java` — `@Component` + `@Designate` + `@Activate/@Modified`
- `ui.config/.../GreetingServiceImpl.cfg.json` — deployed config (prefix "Hello")
- `models/GreetingModel.java` — `@OSGiService` consumer (demo)
- `ui.apps/.../greeting/*`, `GreetingServiceImplTest.java`

## New concepts
- `@Component(service = X.class)` — register an OSGi service
- `@ObjectClassDefinition` + `@AttributeDefinition` — config schema (each method = a field)
- `@Designate(ocd = ...)` — bind schema to component
- `@Activate` (on start) vs `@Modified` (on config change) — bind both = hot reconfigurable
- `.cfg.json` filename = the PID (singleton = FQCN; factory = `FQCN~alias`)
- `@OSGiService` — inject a service into a Sling Model

## Follow-up questions
- **Change config without redeploy?** Configuration Manager or `.cfg.json`; `@Modified` re-reads. (Proven: Hello→Namaste live.)
- **Factory vs singleton config?** `@Designate(factory=true)` + `~alias`.
- **Per-environment values?** Run-mode config folders (`config.author`, `config.publish`, …) — Ex 12/65.
- **Secrets?** Never in `.cfg.json`; AEMaaCS `$[secret:...]` + Cloud Manager env vars.

## Best practices & memory hooks
- Memory hook: **"OCD = the form, `@Designate` = the wire, `@Activate` = read it."**
- Memory hook: **"`@Activate` on birth, `@Modified` on edit."**
- Interface in `services`, impl+config in `services.impl`.
- ⚠️ HTL `data-sly-use` needs the model in an **exported** package (or use its interface).
