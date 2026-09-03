# SatSure SDET Practical Assignment

This repository contains my solution for the SDET practical assignment.

## Approach

I treated the autocomplete form as a small end-to-end feature and covered it at three levels:

1. Requirement and risk analysis
2. UI automation using Playwright with Page Object Model
3. API automation using REST Assured

The assignment provides the expected HTML structure and an assumed form URL. Since a live application/API endpoint is not provided, the test URLs are configurable and no fake execution result is claimed.

## Project Structure

```text
README.md
docs/
  1-requirement-analysis.md
  2-test-scenarios.md
  3-defect-identification.md
  4-test-cases.md
  7-ai-reflection.md
  8-architecture-discussion.md
tests/
  ui/
    pages/
    tests/
    config/
  api/
    tests/
pom.xml
prompts/
  assignment-prompt.txt
ai-transcript.json
SUBMISSION-CHECKLIST.md
```

## Prerequisites

- Java 17+
- Maven 3.9+
- Node.js 18+ (required by Playwright)
- Chrome/Chromium for UI execution

## UI setup

```bash
mvn test -Dtest=AutocompleteFormTest
```

The first Playwright run may require browser installation. Depending on the local Playwright setup, install the required browser binaries before running the suite.

## API setup

The API tests use the `api.baseUrl` system property.

Example:

```bash
mvn test -Dtest=AutocompleteApiTest -Dapi.baseUrl=http://localhost:8080
```

If the API path in the actual environment is different, update `api.path`:

```bash
mvn test -Dtest=AutocompleteApiTest -Dapi.baseUrl=http://localhost:8080 -Dapi.path=/api/autocomplete/responses
```

## UI configuration

The default URL is the URL given in the assignment:

```text
https://test.com/autocomplete-form
```

It can be overridden:

```bash
mvn test -Dtest=AutocompleteFormTest -Dui.baseUrl=https://<test-environment>/autocomplete-form
```

## Important

The assignment asks for an AI reflection and the prompt/transcript used during the work. Those files are included separately. The transcript contains the assignment-related conversation only; it does not expose internal system instructions or tool calls.
