# Exercise 10 — FAQ Accordion

## Challenge
FAQ from a composite multifield (Question/Answer); accordion where **only one item is open at
a time**, accessibly.

## Interview approach
"Data side = the same multifield → child/parent Sling Model pattern. Front end = the WAI-ARIA
accordion pattern: each header is a `<button aria-expanded aria-controls>`, each answer a
`<div role=region aria-labelledby hidden>`. JS toggles `aria-expanded` + `hidden` and closes
the others. Scoped per component instance."

## Files
- `models/FaqItem` + `impl/FaqItemImpl`, `models/Faq` + `impl/FaqImpl`
- `ui.apps/.../faq/_cq_dialog/.content.xml` — composite multifield
- `ui.apps/.../faq/faq.html` — ARIA accordion (`data-sly-repeat`)
- `ui.apps/.../faq/clientlib/*` — one-open JS + CSS
- `FaqImplTest.java`

## New concepts
- **`data-sly-list` vs `data-sly-repeat`**: list repeats the **content** (host once); repeat
  repeats the **host element itself** (fixed live).
- WAI-ARIA accordion: `button[aria-expanded][aria-controls]` ↔ `panel[role=region][aria-labelledby][hidden]`
- Native `hidden` attribute for show/hide
- Per-component JS scoping via root query + `getElementById(aria-controls)`

## Follow-up questions
- **`data-sly-list` vs `data-sly-repeat`?** Content vs element.
- **Why `<button>` not `<div>`?** Keyboard focus + Enter/Space for free.
- **How do screen readers know state?** `aria-expanded`, `aria-controls`/`aria-labelledby`, `hidden`.
- **Multiple FAQs / id collisions?** Scope JS per root; prefix ids with component id/path.

## Best practices & memory hooks
- Memory hook: **"`list` repeats the inside, `repeat` repeats the whole tag."**
- Memory hook: **"button aria-expanded + aria-controls → panel role=region + hidden."**
- Semantic HTML first; toggle via attributes (keeps state + a11y in sync).
