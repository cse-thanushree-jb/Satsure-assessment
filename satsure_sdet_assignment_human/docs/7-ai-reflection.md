# 7. AI Reflection

## a. Tools Used

I used ChatGPT as an AI assistant while working on this assignment.

## b. Usage Areas

I used it mainly for:

- Organizing the requirements into testable scenarios.
- Creating an initial test-case structure.
- Getting a starting point for Playwright Page Object Model code.
- Getting a starting point for REST Assured API validation.
- Checking the structure of the Maven project.

## c. Modifications Made

### Modification 1 - API contract checks

The initial API test idea was mainly focused on checking whether fields existed. I changed it to validate the actual data types as well. For example, `completed` must be a Boolean, not the string `"true"`.

I made this change because FR-05 explicitly defines `completed` as a Boolean, and a response can have the right field name but still violate the contract through the wrong type.

### Modification 2 - Suggestion-list validation

I added a separate validation for `suggestion_list` instead of only checking that the property exists.

The assignment specifically says that the API automation must confirm that the list contains only matching suggestions. This is important because the sample response contains all three suggestions even though one value was selected.

### Modification 3 - Local timestamp consideration

I also added validation around the timezone requirement. The sample response uses `Z`, which means UTC, while the test environment says the user is in India. I therefore treated the timestamp handling as a contract issue rather than only checking that the timestamp looked syntactically valid.

## d. AI Limitations

One limitation I found was that AI can produce valid-looking automation code even when the actual application/API endpoint is not available. The assignment only provides an assumed URL and HTML structure, so I did not treat generated execution output as real test evidence.

I also reviewed the generated test cases because generic AI-generated cases can miss the specific requirement that `suggestion_list` must contain only matching suggestions.
