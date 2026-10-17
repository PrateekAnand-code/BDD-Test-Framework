# BDD Test Framework

Java 17 · Cucumber 8 · JUnit Platform · Playwright · REST Assured

A small framework for readable UI and API acceptance tests. A fresh browser context and dependency-injected test context belong to each scenario. API tests do not launch a browser. The examples start an isolated local demo server on an available port, so they need no external account, API key or public demo service.

## Quick start

Prerequisite: **JDK 17+** and internet access for the first dependency/browser download. Maven is bootstrapped by the checked-in Maven Wrapper (3.9.9). On Windows use `mvnw.cmd` in place of `./mvnw`.

```sh
# API only — no browser installation needed
./mvnw clean verify -Papi

# Install Chromium once (Linux may need: install --with-deps chromium)
./mvnw test-compile exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install chromium"

# UI only, headless by default
./mvnw clean verify -Pui

# All scenarios
./mvnw clean verify

# Watch the browser
./mvnw test -Pui -Dheadless=false

# Filter scenarios (also works without a profile)
./mvnw test "-Dtest.tags=@api and @smoke"
```

`verify` runs the JUnit helper tests as well as the selected Cucumber scenarios. A clean all-suite run contains 7 scenarios: 4 API and 3 UI, plus 3 helper tests. No automatic retries hide failures.

## Browsers

```sh
./mvnw test-compile exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install firefox webkit"
./mvnw test -Pui -Dbrowser=firefox
./mvnw test -Pui -Dbrowser=webkit
```

Valid browsers: `chromium` (default), `firefox`, `webkit`. Playwright manages browser versions; there are no driver binaries to configure. Linux browser dependencies can be installed with the CLI's `install --with-deps` command. The CI workflow installs Chromium and its dependencies explicitly.

## Add tests

1. Add business-readable Gherkin in `src/test/resources/features`; use `@ui` or `@api`.
2. Implement steps in `framework.steps`. Inject `TestContext` through the constructor; never put scenario state in static fields.
3. Put UI behaviour and locators in `framework.pages`. Prefer accessible labels/roles and Playwright retrying assertions to sleeps.
4. Put API requests in `framework.api`. Assert status, body and error behaviour in steps. Do not use global `RestAssured.baseURI`.

The included `LoginPage` shows a page object; `UsersClient` shows a reusable API client. Scenario Outlines replace the old untyped YAML dataset maps. Records or typed request objects can be introduced when payloads grow; schema-generated classes are no longer written into source control during a build.

## Environments

`src/test/resources/config/local.properties` is loaded from the classpath. Add a matching file (for example `qa.properties`) and select it with `-Denv=qa`.

For endpoint settings, precedence is JVM system property, environment variable, then configuration file. `ui.baseUrl` maps to `UI_BASEURL`; `api.baseUrl` maps to `API_BASEURL`; `timeout.ms` maps to `TIMEOUT_MS`. Blank base URLs use the local fixture independently for each kind of test. Browser, headless mode and environment are Maven command-line properties.

```sh
./mvnw test -Papi -Denv=qa -Dapi.baseUrl=https://your-service.example
./mvnw test -Pui -Dui.baseUrl=https://your-app.example -Dtimeout.ms=15000
```

**Adapt the example page object, API client and feature expectations to your application's contract before targeting it.** The supplied examples expect `/login`, `/session` and `/users` with the local demo contract; they are not universal tests for arbitrary sites. The embedded demo app is test infrastructure, not a production service. Its visible credentials are disposable example data. Do not commit real credentials; obtain secrets from environment variables or CI secrets in your own clients/steps. There is no blanket request/response logging of secrets.

## Reports and debugging

- `target/reports/cucumber.html`: human-readable scenarios and attached screenshots.
- `target/reports/cucumber.json` and `cucumber.xml`: machine-readable results.
- `target/surefire-reports/`: Maven/JUnit results.
- `target/artifacts/<unique-id>/`: screenshot and Playwright trace for failed UI scenarios where a page exists. Browser startup failures are recorded in test output.

Open a trace:

```sh
./mvnw test-compile exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="show-trace target/artifacts/<unique-id>/trace.zip"
```

GitHub Actions runs API and Chromium UI as separate jobs and uploads results even on failure. Dependabot proposes Maven and Actions updates weekly. Parallel execution is intentionally off by default; each scenario owns its resources so it can be enabled after validating application isolation and capacity.

## Layout

```text
src/test/java/framework/
  api/       Reusable API clients
  config/    Validated classpath configuration
  pages/     UI page objects
  steps/     Business step definitions
  support/   Scenario lifecycle and isolated local demo app
src/test/resources/
  config/    Named environments
  features/  UI and API scenarios
```

## Migration from v1

This intentionally replaces the Selenium 3 / JUnit 4 framework. Old Java step definitions, YAML locators, YAML datasets and Extent configuration were removed. Recreate existing application-specific steps using the page/client examples; do not copy the old static driver lifecycle. Reports now come directly from Cucumber and Playwright. The MIT license is retained.
