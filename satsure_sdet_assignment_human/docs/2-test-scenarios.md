# 2. Test Scenarios

I ranked these based on impact to the main user journey and the possibility of incorrect data being stored.

| Rank | Scenario | Risk | Why I ranked it this way |
|---|---|---|---|
| 1 | Submit a valid selected suggestion and verify success | Critical | This is the main business flow and failure prevents the response from being recorded. |
| 2 | Verify default prefix filtering | Critical | Filtering is the main feature and wrong results can cause the user to select an incorrect value. |
| 3 | Verify API response contains correct account and form data | Critical | Incorrect persisted data is a serious backend/data-integrity issue even if the UI looks successful. |
| 4 | Verify invalid input shows the error and is not treated as successful | High | Invalid input must not be recorded as a completed response. |
| 5 | Verify suggestion selection populates the input correctly | High | If the selected value is different from what the user clicked, the submitted response can be wrong. |
| 6 | Verify match-anywhere filtering when enabled | High | This is configurable behavior, so a wrong implementation can affect configured environments. |
| 7 | Verify keyboard navigation and Enter submission | High | Keyboard users must be able to complete the form without depending only on mouse actions. |
| 8 | Verify Escape clears/closes the suggestion state as designed | Medium | It is explicitly required by the assignment, but it has less impact than the main submit flow. |
| 9 | Verify start_date and end_date are local user timestamps | Medium | Wrong time or timezone data can affect reporting and audit information. |
| 10 | Verify locale and completed data types/format | Medium | These fields are part of the contract and can break downstream consumers if stored incorrectly. |
