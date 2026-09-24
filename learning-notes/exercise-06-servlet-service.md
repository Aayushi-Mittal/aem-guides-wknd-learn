# Exercise 6 — Servlet → Service → Repository (Layering)

## Challenge
Wire a servlet to an OSGi `ProductService` that reads products from the repository.
**No business logic in the servlet.**

## Interview approach
"Single responsibility per layer: the **servlet** does HTTP (routing, resolver, response);
the **service** owns the how (repo read + mapping); the **model** is the data. The servlet
gets the service via `@Reference` and delegates. This makes logic reusable, testable, and
swappable."

## Files
- `models/Product.java` + `impl/ProductImpl.java`
- `services/ProductService.java` + `impl/ProductServiceImpl.java`
- `servlets/ProductListServlet.java` (thin; `@Reference` the service)
- `ProductServiceImplTest.java` — 2 tests

## New concepts
- `@Reference` — inject an OSGi service into another OSGi **component** (servlet/service)
- `ResourceResolver.getResource(path)` + `resource.getChildren()`
- `resource.adaptTo(Product.class)` — node → typed object
- Layered architecture: presentation → service → data

## Follow-up questions
- **Why not read JCR in the servlet?** Not reusable, not unit-testable, mixes concerns.
- **`@Reference` vs `@OSGiService`?** `@Reference` → components; `@OSGiService` → Sling Models.
- **Who closes the resolver?** Here it's `request.getResourceResolver()` — request-scoped, Sling closes it. Background code opens/closes its own (Ex 7).
- **Hardcoded base path?** Should be OSGi config (Ex 12/65).

## Best practices & memory hooks
- Memory hook: **"Servlet talks HTTP, Service talks logic, Resolver talks JCR."**
- Memory hook: **"`@Reference` for components, `@OSGiService` for models."**
- Thin controllers; return empty not null; depend on interfaces.
