# Exercise 1 — Employee Card

## Challenge
Create an `employee-card` component: name, designation, description, image, LinkedIn URL,
and a "Show LinkedIn" checkbox. Dialog + Sling Model + HTL + clientlib, with defaults and
conditional rendering.

## Interview approach
"An author fills a **dialog** → values save as JCR properties → a **Sling Model** reads them
into typed getters → **HTL** renders them → a **clientlib** styles it. I put display logic
(e.g. whether to show the LinkedIn button) in Java, not HTL, and I render null-safely so
missing fields never produce broken markup."

## Files
- `core/.../models/EmployeeCard.java` — interface (view contract)
- `core/.../models/impl/EmployeeCardImpl.java` — `@Model` impl
- `ui.apps/.../employee-card/.content.xml` — component
- `ui.apps/.../employee-card/_cq_dialog/.content.xml` — dialog
- `ui.apps/.../employee-card/employee-card.html` — HTL
- `ui.apps/.../employee-card/clientlib/*` — CSS
- `core/src/test/.../EmployeeCardImplTest.java` (+ JSON fixture) — 5 tests

## New concepts
- `@Model(adaptables, adapters, resourceType, defaultInjectionStrategy=OPTIONAL)`
- `@ValueMapValue` (reads a property from the resource's ValueMap); `name=` to override
- OPTIONAL injection = missing property → default value, model still builds
- HTL `data-sly-use`, `data-sly-test`, placeholder template, boolean getter `isX()`
- Derived logic in Java: `isShowLinkedIn() = checkbox && non-blank url`

## Follow-up questions
- **Why `@ValueMapValue` not `@Inject`?** Specific injector → explicit, faster, no ambiguity.
- **Missing property?** OPTIONAL → null/false; REQUIRED → whole model fails to adapt.
- **Why is the button hidden?** `isShowLinkedIn()` needs checkbox on AND a URL.
- **How to test?** `AemContext` + JSON fixture, adapt resource, assert (incl. edge cases).

## Best practices & memory hooks
- Memory hook: **Dialog `name="./x"` → property `x` → `@ValueMapValue x` → `getX()` → `${model.x}`.**
- Program to an **interface**; impl in `...models.impl`.
- Checkbox: set `value` + `uncheckedValue` so unchecking stores `false`.
- `target="_blank"` links need `rel="noopener noreferrer"`.
