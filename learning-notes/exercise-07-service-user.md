# Exercise 7 — Service User & Repository Access (Security)

## Challenge
An OSGi service reads the repository with its **own identity** — no admin resolver,
least-privilege, always closed.

## Interview approach
"Never an admin resolver. I create a **system user** with only the permissions it needs via
**repoinit** (perms as code), map my bundle's **subservice** to it with the **Service User
Mapper**, and get a resolver from `getServiceResourceResolver(subservice)` inside
**try-with-resources**. Fail safe on `LoginException`."

## Files
- `ui.config/.../RepositoryInitializer~wknd-productservice.cfg.json` — repoinit (create user + ACL)
- `ui.config/.../ServiceUserMapperImpl.amended~wknd-productservice.cfg.json` — mapping (principal syntax)
- `services/impl/ProductServiceImpl.java` — `getProductsAsService()` + try-with-resources

## New concepts
- **System/service user** — passwordless identity for background code
- **repoinit**: `create service user …`, `set ACL for … allow jcr:read on /path`
- **Service User Mapper**: `bundle:subservice=[principal]`
- `ResourceResolverFactory.getServiceResourceResolver(Map SUBSERVICE)`
- `ResourceResolver` is `Closeable` → try-with-resources

## Follow-up questions
- **Why not admin?** Deprecated/unsafe, over-privileged, forbidden on AEMaaCS.
- **Not closing it?** Session/memory leak → repository exhaustion.
- **Authorized vs unauthorized path?** ACL scoped to `/content/wknd-demo`; outside → null → empty.
- **repoinit vs manual?** repoinit is versioned, repeatable, runs on every fresh instance.
- **When does repoinit run?** At **repository startup** (correct factory PID also applies runtime adds).

## Best practices & memory hooks
- Memory hook: **"User (repoinit) → Mapping (mapper) → Resolver (getServiceResourceResolver)."**
- Memory hook: **"Never admin. Always least privilege. Always close."**
- ⚠️ repoinit factory PID on 6.5 = `org.apache.sling.jcr.repoinit.RepositoryInitializer` (confirm via `/system/console/components`).
- Use principal syntax `=[user]` to avoid the deprecation warning.
