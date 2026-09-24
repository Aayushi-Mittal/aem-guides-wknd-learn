# WKND AEM Interview Practice — Learning Notes

A hands-on, one-exercise-at-a-time walk through core AEM development, built into this
WKND project and verified live on a local AEM 6.5.2 author instance.

Each exercise lives on its own git branch (`exercise-NN-name`, cumulative off the previous)
and has a README here with: **challenge, interview approach, files, new concepts,
follow-up questions, best practices + memory hooks**.

| # | Exercise | Branch | Key concepts |
|---|----------|--------|--------------|
| 1 | [Employee Card](exercise-01-employee-card.md) | `learning/aem-practice-set` | Dialog, Sling Model, HTL, clientlib |
| 2 | [Article List](exercise-02-article-list.md) | `exercise-02-article-list` | Composite multifield, `@ChildResource`, `data-sly-list` |
| 3 | [Core Component Extension](exercise-03-core-extension.md) | `exercise-03-core-extension` | `resourceSuperType`, Resource Merger, `@Via` delegation |
| 4 | [Configurable OSGi Service](exercise-04-osgi-config.md) | `exercise-04-osgi-config` | `@Component`, `@ObjectClassDefinition`, `@Designate` |
| 5 | [JSON Servlet](exercise-05-json-servlet.md) | `exercise-05-json-servlet` | `@SlingServletResourceTypes`, selectors |
| 6 | [Servlet → Service → Repo](exercise-06-servlet-service.md) | `exercise-06-servlet-service` | Layering, `@Reference`, `ResourceResolver` |
| 7 | [Service User](exercise-07-service-user.md) | `exercise-07-service-user` | repoinit, service user mapping, try-with-resources |
| 8 | [QueryBuilder Search](exercise-08-querybuilder.md) | `exercise-08-querybuilder` | Predicate map, indexing/traversal |
| 9 | [Search full stack](exercise-09-search-fullstack.md) | `exercise-09-search-fullstack` | `data-*` handoff, `fetch`, UI states |
| 10 | [FAQ accordion](exercise-10-faq.md) | `exercise-10-faq` | `data-sly-repeat`, ARIA accordion |
| 11 | [External API](exercise-11-external-api.md) | `exercise-11-external-api` | Timeouts, error handling, fallback |
| 12 | [Scheduler](exercise-12-scheduler.md) | `exercise-12-scheduler` | Whiteboard scheduler, cron |
| 13 | [Dispatcher debugging](13-dispatcher-debugging.md) | `exercise-13-dispatcher` | Filters, cache, deny-by-default |
| 14 | [EDS Card block](exercise-14-eds-card.md) | `exercise-14-eds-card` | `decorate(block)`, DOM transform |
| 15 | [Full Product System](exercise-15-full-system.md) | `exercise-15-full-system` | Capstone: whole stack together |

## Environment / deploy cheatsheet
- Local AEM 6.5.2 author: `http://localhost:4502` (admin:admin).
- Core bundle **must** build with the **`-Pclassic`** profile (6.5 API ranges). Default
  `cloudservice` profile imports newer package versions that won't resolve on 6.5.
- Deploy commands:
  - Core bundle: `mvn install -PautoInstallBundle -Pclassic -DskipTests -Dbnd.baseline.skip=true -pl core`
  - ui.apps: `mvn install -PautoInstallPackage -DskipTests -pl ui.apps`
  - ui.config: `mvn install -PautoInstallPackage -DskipTests -pl ui.config`
- Adding new exported API (in `core...models`) requires bumping `package-info.java` `@Version`.
