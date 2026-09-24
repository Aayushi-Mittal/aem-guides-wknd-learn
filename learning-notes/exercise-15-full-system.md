# Exercise 15 — Full Product System (Capstone)

## Challenge
Assemble a cohesive product-listing feature that ties the whole stack together:
**Dialog → Sling Model → OSGi Service → QueryBuilder → HTL → Clientlib**, with filter-by-category.

## Interview approach
"I compose the pieces built earlier rather than reinvent them. The author sets a **title** and
optional **category** in the dialog. The **Sling Model** injects `ProductSearchService`
(`@OSGiService`) and its own `ResourceResolver` (`@SlingObject`), and delegates the query —
no logic in the model or HTL. **HTL** renders the title and a card grid with `data-sly-list`;
the **clientlib** styles it. Each layer keeps one responsibility, exactly like a real feature."

## Files
- `models/ProductListing.java` + `impl/ProductListingImpl.java`
- `ui.apps/.../product-listing/.content.xml` + `_cq_dialog` (title + category)
- `ui.apps/.../product-listing/product-listing.html`
- `ui.apps/.../product-listing/clientlib/*` — grid CSS
- `ProductListingImplTest.java`

## New concepts
- `@SlingObject ResourceResolver` — inject this resource's resolver into a Resource-adaptable model
- Composition of services: model → `ProductSearchService` → QueryBuilder (reuse across the app)
- End-to-end wiring of every prior concept into one component

## The full chain (what an interviewer wants to see named)
```
Dialog (title, category)
  -> JCR properties
  -> ProductListing Sling Model (@ValueMapValue + @OSGiService + @SlingObject)
  -> ProductSearchService (@OSGiService)
  -> QueryBuilder (path + type + property=category)
  -> HTL data-sly-list (card grid)
  -> Clientlib (CSS)
```

## Follow-up questions
- **Where does the resolver come from in the model?** `@SlingObject` → the resource's resolver.
- **Why delegate to a service?** Reuse (servlet, scheduler, model all share it), testability, single source of truth.
- **Filter vs no filter?** Blank category → `search()` omits the property predicate → all products (proven live).
- **Dynamic (client-side) variant?** Reuse the Ex 9 pattern: `data-endpoint` + `fetch` to the search servlet.
- **Scaling?** Index the `category` property (Ex 8/31); cache at Dispatcher with a correct cache key (Ex 13).

## Best practices & memory hooks
- Memory hook: **"Dialog → Model → Service → Query → HTL → Clientlib — one job per layer."**
- Compose existing services; keep model + HTL logic-free; return empty not null.
- This is the shape of almost every real AEM feature — recognise it and you can build anything.
