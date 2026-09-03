# 1. Requirement Analysis

## Scope

The main flow starts after login. The user reaches the autocomplete form, enters/selects a value, and clicks Next. The response is persisted through a REST API.

The assignment gives three suggestions:

- agile methodology
- agile methodology process
- agile methodology process testing

The default filtering behavior is prefix matching. A backend configuration can change this to match anywhere in the suggestion.

## Requirements I would test

### FR-01 - Text Input

The user should be able to type a value in the input or select a suggestion.

### FR-02 - Prefix filtering

By default, only suggestions starting with the typed text should remain visible.

### FR-03 - Match-anywhere filtering

When backend configuration enables it, a suggestion should remain visible when the typed text occurs anywhere in the suggestion.

### FR-04 - Submission

Clicking Next should call the REST API. A successful response is HTTP 200 and the UI should show the success message. Invalid input should show the error message.

### FR-05 - API contract

The persisted response needs:

- account_id
- account_email
- start_date
- end_date
- locale
- text
- suggestion_list
- completed

The timestamp fields need to represent the user's local time. The locale needs to be in IETF BCP 47 format. `completed` is a Boolean.

## Main risks

My highest risks are around filtering, selecting a suggestion, submitting the wrong value, and storing incorrect response data. These can either block the user or create incorrect persisted data.

I would also test keyboard accessibility because the assignment specifically requires Tab, Enter and Escape interactions.
