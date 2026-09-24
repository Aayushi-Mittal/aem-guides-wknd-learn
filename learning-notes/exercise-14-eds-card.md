# Exercise 14 — EDS Card Block

## Challenge
In **Edge Delivery Services**, write `export default function decorate(block)` that turns the
authored (generic-div) DOM into a semantic card: image, title, description, link.

## Interview approach
"EDS is document-authored: a table becomes generic nested `<div>`s — block → rows → cells. My
`decorate(block)` runs client-side to reshape that into meaningful styled HTML — label the
image/text cells, promote the link into a CTA, flatten the wrapper. Opposite of AEM's
server-side HTL: markup is authored generically and enhanced in the browser."

## Files
- `eds/blocks/card/card.js` — `decorate()` DOM transform
- `eds/blocks/card/card.css` — block-scoped styles
- `eds/index.html` — demo harness (served via `python -m http.server`)

## New concepts
- EDS block anatomy: `block → rows → cells` as nested `<div>`s
- `export default function decorate(block)` — block entry point (folder = block name = class)
- DOM transform: `classList`, `closest('p')`, `replaceWith`, re-append nodes
- Block-scoped CSS via `.card.block`
- Client-side enhancement vs AEM server-side rendering

## Follow-up questions
- **Where is `decorate` called?** EDS `scripts.js` loads `blocks/<name>/<name>.js`+`.css` and calls the default export.
- **How do authors make a card?** A table; first cell = block name; columns = cells.
- **HTL vs `decorate`?** Server-side model rendering vs browser DOM reshaping.
- **Variations (`card highlight`)?** Extra class on the block → CSS/JS branch, no new block.
- **Performance?** EDS targets Core Web Vitals — keep `decorate` light, avoid layout shift.

## Best practices & memory hooks
- Memory hook: **"block → rows → cells (divs); `decorate` reshapes them."**
- Memory hook: **"one default export: `decorate(block)`; file/folder name = block name."**
- Idempotent + light `decorate`; guard defensively; scope CSS; emit semantic HTML.
