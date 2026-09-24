# Exercise 8 — QueryBuilder Search

## Challenge
Search the repository in Java with **QueryBuilder** — under a path, filtered by type and
property (category).

## Interview approach
"QueryBuilder expresses a JCR query as a **map of predicates** — readable and composable. Get
a `Session` from the resolver, build predicates (`path`, `type`, `property`/`property.value`,
`p.limit`), run `createQuery(...).getResult()`, map `getResources()` to objects. Always cap
with `p.limit`, and be index-aware — unindexed property filters cause traversals."

## Files
- `services/ProductSearchService.java` + `impl/ProductSearchServiceImpl.java`
- `servlets/ProductSearchServlet.java` — `page.productsearch.json?category=hiking`
- `ProductSearchServiceImplTest.java` — Mockito mocks QueryBuilder/Query/SearchResult

## New concepts
- Predicate map: `path`, `type`, `property` + `property.value`, `p.limit`, `p.offset`, `p.guessTotal`
- `resolver.adaptTo(Session.class)`
- `queryBuilder.createQuery(PredicateGroup.create(map), session).getResult().getResources()`
- Traversal vs indexed queries (Oak `consider creating an index` WARN — seen live)
- `request.getParameter(...)`

## Follow-up questions
- **Find all pages under a path?** `type=cq:Page`.
- **Traversal query — why bad?** No index → scans every node; Oak aborts huge traversals.
- **`p.limit`?** Default is only 10 — always set it; never run unbounded in prod.
- **QueryBuilder vs SQL2?** QueryBuilder = readable map (dynamic); SQL2 = lower-level/precise.
- **Whose permissions?** The `Session` behind the resolver → ACL-filtered results.

## Best practices & memory hooks
- Memory hook: **"map of predicates → createQuery → getResult → getResources."**
- Memory hook: **"path, type, property/value, p.limit."**
- Always cap results; index frequently-filtered properties; map hits to typed objects.
