# Exercise 5 — JSON Servlet

## Challenge
A servlet returning `{"name":"AEM","version":"6.5"}`, registered **by resource type** (not
path), reachable at `page.products.json`.

## Interview approach
"I register with `@SlingServletResourceTypes`, binding to **resourceType + selector +
extension + method** — the secure, RESTful way (never `paths=`). I extend
`SlingSafeMethodsServlet` for read-only GET, keep data logic separate from I/O for
testability, and serialize with Jackson."

## Files
- `servlets/ProductsServlet.java`
- `ProductsServletTest.java` (tests `buildData()`)

## New concepts
- `@Component(service = Servlet.class)` + `@SlingServletResourceTypes(resourceTypes, selectors, extensions, methods)`
- Request anatomy: `path . selectors . extension / suffix ? params`; Sling routes on resourceType+selectors+extension+method
- `SlingSafeMethodsServlet` (GET/HEAD) vs `SlingAllMethodsServlet` (+POST/PUT/DELETE)
- **Gotcha:** the page node is `cq:Page` (no `sling:resourceType`); the component type is on
  `jcr:content`. Bind to `cq:Page` for page-path URLs, or hit `.../jcr:content.sel.json`.

## Follow-up questions
- **`resourceTypes` vs `paths` registration?** `paths=` bypasses resolution/ACLs — discouraged; `resourceTypes` is secure & cacheable.
- **Safe vs All servlet?** Read-only vs read+write.
- **Why did `page.products.json` miss?** Page node is `cq:Page`, not `wknd/components/page`.
- **Business logic in the servlet?** No — delegate to a service (Ex 6).

## Best practices & memory hooks
- Memory hook: **"routing key = resourceType + selector + extension + method."**
- Memory hook: **"URL = path . selectors . ext / suffix ? params."**
- Separate logic from I/O (`buildData()`) → unit-testable.
- ⚠️ A static `new ObjectMapper()` field can break unit tests on a split test-classpath; make it local.
