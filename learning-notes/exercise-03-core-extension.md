# Exercise 3 — Core Component Extension

## Challenge
Add an author-set `trackingId` to the core Image component **without duplicating** it.

## Interview approach
"I extend with **`sling:resourceSuperType`** (proxy pattern), add the field via the **Sling
Resource Merger** (declare only the new field; inherit the rest), and add behaviour with
**`@Via(ResourceSuperType)` delegation** so my model borrows the core model. HTL reuses the
core rendering entirely via `data-sly-resource`. Reuse everything, duplicate nothing."

## Files
- `models/ImageTracking.java` + `impl/ImageTrackingImpl.java` — delegating model
- `ui.apps/.../image-tracking/.content.xml` — `sling:resourceSuperType=core image v3`
- `ui.apps/.../image-tracking/_cq_dialog/.content.xml` — merged dialog (adds "Tracking" tab)
- `ui.apps/.../image-tracking/image-tracking.html` — wrapper + core reuse
- `ImageTrackingImplTest.java`

## New concepts
- `sling:resourceSuperType` — inherit dialog/HTL/model from another component
- Sling Resource Merger: add = declare node; remove = `sling:hideResource`; reorder = `sling:orderBefore`
- `@Self @Via(type = ResourceSuperType.class) Image image;` — adapt request *as the super type*
- `data-sly-resource="${resource.path @ resourceType='...core...'}"` — re-render same node as core type
- Core components need `currentPage` + `currentStyle` → only render inside a real `cq:Page`

## Follow-up questions
- **How add a field without redefining the dialog?** Resource Merger — declare only the new node.
- **What does `@Via(ResourceSuperType)` do?** Makes Sling pick the core model for the delegate.
- **Full method forwarding?** Lombok `@Delegate` on the field (then your class *is* an `Image`).
- **Why did it fail on a bare node?** No page context → `currentPage`/`currentStyle` missing.

## Best practices & memory hooks
- Memory hook: **"@Via ResourceSuperType = borrow the parent's model."**
- Memory hook: **"add / hideResource / orderBefore"** for merged dialogs.
- Extend over copy → inherit upstream fixes; keep the extension thin.
