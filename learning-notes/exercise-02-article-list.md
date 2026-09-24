# Exercise 2 — Article List (Composite Multifield)

## Challenge
A component with a **composite multifield** (Title, Description, Category, URL). Render all
articles as cards, and render the **first article differently** from the rest.

## Interview approach
"A composite multifield stores each row as a **child node** (`item0`, `item1`, …) under a
wrapper node. I model each row with a small child Sling Model and inject the whole list into
the parent with `@ChildResource List<Article>`. HTL's `data-sly-list` gives a free status
object (`.first`, `.index`, …) to style the first item specially — no Java branching."

## Files
- `models/Article.java` + `impl/ArticleImpl.java` — child row model
- `models/ArticleList.java` + `impl/ArticleListImpl.java` — parent (`@ChildResource List`)
- `ui.apps/.../article-list/_cq_dialog/.content.xml` — composite multifield
- `ui.apps/.../article-list/article-list.html` — `data-sly-list` + `.first`
- `ArticleListImplTest.java` (+ JSON) — 2 tests

## New concepts
- Composite multifield: `multifield` + `composite="{Boolean}true"`, `field` = a **container**
  with `name="./articles"`, sub-fields with `name="./title"` etc.
- `@ChildResource` on a `List<T>` → children of the named node, each adapted to `T`, in order
- `data-sly-list.article="${...}"` → loop variable `article` + status object `articleList`
  (`.first`, `.last`, `.index`, `.count`, `.odd`, `.even`)

## Follow-up questions
- **`@ValueMapValue` vs `@ChildResource`?** Properties on this node vs child nodes below it.
- **Order preserved?** JCR keeps child-node order → authored order == rendered order.
- **Simple vs composite multifield?** Simple = a String[] (one value per row); composite =
  child nodes (multiple fields per row).

## Best practices & memory hooks
- Memory hook: **"composite = children, simple = array."**
- Memory hook: **"the list variable + `List` suffix = loop metadata."**
- Return `Collections.emptyList()`, never null.
