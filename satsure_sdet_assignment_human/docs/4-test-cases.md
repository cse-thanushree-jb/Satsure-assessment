# 4. Test Cases

## TC-01 - Select suggestion and submit

**Title:** Submit a valid autocomplete response

**Preconditions:**
- User is logged in.
- User has reached the autocomplete form.
- Suggestions are displayed.

**Steps:**
1. Click the input field.
2. Enter `agile`.
3. Select `agile methodology`.
4. Click Next.
5. Check the API response and UI.

**Expected Results:**
- The input contains `agile methodology`.
- The API is called once for the submission.
- API returns HTTP 200.
- Success message is displayed.
- The persisted response has `completed=true` as a Boolean.

**Test Data:** `agile` -> `agile methodology`

---

## TC-02 - Prefix filtering

**Title:** Verify default prefix filtering

**Preconditions:**
- Prefix filtering is enabled.

**Steps:**
1. Open the form.
2. Type `agile`.
3. Check the suggestion list.
4. Type a value that does not match the beginning of any suggestion.

**Expected Results:**
- While typing `agile`, all three supplied suggestions remain visible.
- When the text does not match any suggestion prefix, matching suggestions disappear.

**Test Data:** `agile`, `testing`

---

## TC-03 - Match-anywhere filtering

**Title:** Verify match-anywhere filtering when configured

**Preconditions:**
- Backend match-anywhere configuration is enabled.

**Steps:**
1. Open the form.
2. Type `method`.
3. Check the suggestion list.

**Expected Results:**
- Suggestions containing `method` anywhere in the string remain visible.

**Test Data:** `method`

---

## TC-04 - Invalid submission

**Title:** Verify invalid input shows an error

**Preconditions:**
- User is on the form.
- Input does not contain a valid suggestion.

**Steps:**
1. Enter `invalid value`.
2. Do not select a valid suggestion.
3. Click Next.

**Expected Results:**
- The submission is rejected.
- Error message is displayed.
- Success message is not displayed.
- The response is not marked completed.

**Test Data:** `invalid value`

---

## TC-05 - Suggestion selection

**Title:** Verify clicking a suggestion populates the input

**Preconditions:**
- Suggestions are visible.

**Steps:**
1. Click the input.
2. Type `agile`.
3. Click `agile methodology process`.

**Expected Results:**
- Input value becomes exactly `agile methodology process`.

**Test Data:** `agile methodology process`

---

## TC-06 - Tab navigation

**Title:** Verify keyboard Tab navigation

**Preconditions:**
- Form is open.

**Steps:**
1. Press Tab until the input receives focus.
2. Press Tab again.
3. Continue until the Next button receives focus.

**Expected Results:**
- Focus moves through the form controls in a logical order.
- The Next button can be reached without a mouse.

**Test Data:** None

---

## TC-07 - Enter submission

**Title:** Verify Enter can submit a valid response

**Preconditions:**
- A valid suggestion has been selected.

**Steps:**
1. Select `agile methodology`.
2. Focus the input/form.
3. Press Enter.

**Expected Results:**
- The form submission is triggered.
- A successful HTTP 200 response results in the success message.

**Test Data:** `agile methodology`

---

## TC-08 - Escape interaction

**Title:** Verify Escape clears or closes suggestion state

**Preconditions:**
- Suggestions are visible.

**Steps:**
1. Type `agile`.
2. Press Escape.
3. Observe the input and suggestion list.

**Expected Results:**
- The suggestion popup/list is closed or the input is cleared according to the product behavior.
- No accidental submission occurs.

**Test Data:** `agile`

---

## TC-09 - API contract validation

**Title:** Validate all required API fields

**Preconditions:**
- A response has been created.

**Steps:**
1. Send GET request for the saved response.
2. Validate the JSON schema.
3. Validate required properties.

**Expected Results:**
- All required properties are present.
- No required property is null unexpectedly.

**Test Data:** Response for `agile methodology`

---

## TC-10 - API data type validation

**Title:** Validate completed and timestamp types

**Preconditions:**
- API response is available.

**Steps:**
1. Read `completed`.
2. Read `start_date` and `end_date`.
3. Validate the values.

**Expected Results:**
- `completed` is a Boolean.
- Timestamps use the expected ISO timestamp representation.
- `end_date` is not before `start_date`.

**Test Data:** Saved response

---

## TC-11 - Locale validation

**Title:** Validate IETF BCP 47 locale

**Preconditions:**
- API response is available.

**Steps:**
1. Read the locale.
2. Validate it against the BCP 47 pattern.

**Expected Results:**
- Locale is a valid BCP 47 value.
- For the supplied India test context, `en-IN` is the expected locale representation.

**Test Data:** `en-IN`

---

## TC-12 - Suggestion list matching

**Title:** Verify API returns only matching suggestions

**Preconditions:**
- A value has been selected/submitted.

**Steps:**
1. Get the saved response.
2. Read `text`.
3. Split `suggestion_list` by comma.
4. Compare each suggestion with the selected/entered value according to the configured matching rule.

**Expected Results:**
- `suggestion_list` contains only suggestions that match the submitted value.
- Unrelated suggestions are not returned.

**Test Data:** `agile methodology`
