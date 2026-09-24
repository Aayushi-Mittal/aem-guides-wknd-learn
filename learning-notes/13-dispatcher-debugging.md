# Exercise 13 — Dispatcher Debugging (grounded in this repo)

The Dispatcher is Apache HTTPD + the `dispatcher` module. It does two jobs:
**security filtering** (what requests reach publish) and **caching** (what gets stored/served).

## The request/cache flow
```
Author --replication--> Publish --> Dispatcher (filter -> cache) --> CDN --> Browser
```
Each hop can cache. A "stale page" or "404" bug is always "which hop is wrong?".

## Where each concern lives (this repo)
| Concern | File |
|---|---|
| Which URLs are allowed through | `conf.dispatcher.d/filters/default_filters.any` + `filters.any` |
| What gets cached / TTL | `conf.dispatcher.d/cache/rules.any` + `default_rules.any` |
| Which query params keep caching | `conf.dispatcher.d/cache/marketing_query_parameters.any` |
| Host + docroot + farm wiring | `conf.dispatcher.d/available_farms/wknd.farm`, `conf.d/available_vhosts/wknd.vhost` |

## Rule 1 you must remember: default is DENY
`default_filters.any` starts with:
```
/0001 { /type "deny" /url "*" }        # deny everything
/0010..                                 # then allow-list specific things
```
So **anything not explicitly allowed returns 404 at the Dispatcher** even if publish returns 200.

### Scenario: "Publish 200, Dispatcher 404"  (THE classic)
Our Exercise 8/9 endpoints use custom selectors:
`page.productsearch.json`, `page.productlist.json`.
Look at `filters.any` — only these custom JSON selectors are allowed:
```
/0101 ... /selectors "model"        /extension "json"
/0110 ... /selectors "searchresults" /extension "json"
```
`productsearch` / `productlist` are **NOT** in the allow-list -> Dispatcher denies them (404),
even though they work directly on publish. **Fix:** add a filter, e.g.
```
/0112 { /type "allow" /extension "json" /selectors "productsearch" /path "/content/*" }
```
Debug order for a 404: publish direct (200?) -> dispatcher filters (allowed?) ->
vhost/rewrite (path mapped?) -> cache (stale 404 cached?).

### Scenario: Stale page after publish
Walk the flow: Author activated? -> replication agent queue clean? -> publish shows new? ->
Dispatcher `/statfileslevel` invalidation touched the right path? -> CDN TTL/soft-purge? -> browser hard-reload.
Most common cause: Dispatcher didn't get (or didn't act on) the invalidation, OR a CDN/browser TTL.

### Scenario: Query-string caching (Exercise 45)
`marketing_query_parameters.any` allows ONLY specific params (`utm_*`, `gclid`, `fbclid`, ...).
Our `?category=hiking` is **not** listed. Consequences:
- Params not on the allow-list are **ignored for the cache key** -> `?category=books` and
  `?category=electronics` can serve the **same** cached file -> wrong results.
- **Fix options:** add `category` to the allow-list so it's part of the cache key, OR make the
  endpoint path-based (`/products/hiking.json`) instead of query-based, OR mark it uncacheable.

### Scenario: Sensitive/personalized page cached (Exercise 44)
Personalized responses must NOT be cached. Options: don't cache that path (cache rules `deny`),
set `Cache-Control: private/no-store` + `Dispatcher: no-cache` response headers from AEM, or
serve personalization client-side after a cacheable shell.

## Fast triage checklist
1. Reproduce on **publish directly** (bypass dispatcher). 200 there but 404 via dispatcher = **filters**.
2. Check `filters.any` allow-list for the exact method + path + selector + extension.
3. Check cache: is a bad response cached? Clear `/cache` (docroot) or invalidate.
4. Check query params vs `marketing_query_parameters.any`.
5. Check vhost/rewrites for path mapping.
6. Only then look upstream (replication, CDN, browser).

## Memory hooks
- "Dispatcher = **filter + cache**. Default filter is **deny**; you allow-list."
- "**200 on publish + 404 via dispatcher = a filter problem.**"
- "Only **allow-listed query params** are part of the cache key — everything else risks wrong cache hits."
- "Debug **top-down the flow**: publish -> filter -> rewrite -> cache -> CDN -> browser."
