# 8. Architecture Discussion

## Proposed automation structure

I kept UI and API tests separate but used the same Maven project so they can run from CI.

```text
tests
├── ui
│   ├── pages
│   │   └── AutocompletePage.java
│   ├── tests
│   │   └── AutocompleteFormTest.java
│   └── config
│       └── TestConfig.java
└── api
    └── tests
        ├── AutocompleteApiTest.java
        └── contract-schema.json
```

## UI layer

The Page Object contains locators and user actions such as:

- enter text
- read suggestions
- click a suggestion
- click Next
- press keyboard keys

The test class contains the actual assertions. This keeps locators out of most test methods and makes the tests easier to maintain.

## API layer

REST Assured is used for API calls and assertions. The JSON schema is kept as a separate file so contract changes can be reviewed without changing every test.

The API tests also have individual checks for:

- required fields
- data types
- timestamps
- locale
- suggestion matching
- negative responses

## Configuration

Environment-specific values should not be hard-coded. The base URL can be supplied through Maven system properties. The same approach can be extended to credentials, API tokens and browser configuration in CI.

## CI/CD

In a CI pipeline I would run:

1. Build/compile
2. API contract tests
3. UI smoke tests
4. Full regression tests
5. Publish test reports

For a pull request, I would keep the fast smoke/API contract checks as a quality gate and run the larger regression suite according to the team's CI strategy.

## Scalability

For a larger application, I would add reusable API clients, test data builders, common assertions, environment configuration, parallel execution where safe, and reporting. The same framework can then support additional autocomplete forms without duplicating common code.
