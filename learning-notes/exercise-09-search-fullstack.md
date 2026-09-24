# Exercise 9 — Search → Servlet → JS (Full Stack)

## Challenge
A client-side search UI: input → `fetch` the servlet → render results, handling empty query,
loading, no results, and errors.

## Interview approach
"The component renders an input and empty results container, and passes the servlet URL to JS
via a **`data-` attribute** built from `${currentPage.path}` — HTL owns the URL, JS stays
generic. A component clientlib `fetch`es, shows loading, renders, and handles all failure
states. I render with **`textContent`** (never inject raw JSON) to prevent XSS."

## Files
- `ui.apps/.../product-search/product-search.html` — input/button/results + `data-endpoint`
- `ui.apps/.../product-search/clientlib/*` — vanilla JS `fetch` + CSS

## New concepts
- Server→client handoff via `data-*` attributes (never hardcode paths in JS)
- `${currentPage.path}` HTL global binding
- `fetch(url).then(res => res.json())` + `.catch`
- Component clientlib with JS + CSS (`clientlib.all`)
- Four UI states: empty / loading / no-results / error
- XSS-safe rendering with `textContent`

## Follow-up questions
- **How does JS know the endpoint?** `data-endpoint` from `currentPage.path` in HTL.
- **Empty / no results / error?** Empty → search all; `[]` → message; throw → `.catch` message.
- **XSS risk?** `innerHTML` of server data is dangerous; use `textContent` (HTL escapes server-side too).
- **Dispatcher caching of `?category=`?** Query params are cache-key-sensitive (Ex 13/45).

## Best practices & memory hooks
- Memory hook: **"HTL computes URL into `data-*`; JS reads `data-*` and fetches."**
- Memory hook: **"handle all four states: empty, loading, no-results, error."**
- Vanilla `fetch` + `querySelectorAll`; scope JS per component; progressive enhancement.
