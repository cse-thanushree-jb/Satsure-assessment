package api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.junit.jupiter.api.Assertions.*;

public class AutocompleteApiTest {

    private String baseUrl() {
        return System.getProperty("api.baseUrl", "http://localhost:8080");
    }

    private String path() {
        return System.getProperty(
                "api.path",
                "/api/autocomplete/responses"
        );
    }

    @Test
    void responseShouldMatchContractSchema() {
        RestAssured
                .given()
                .baseUri(baseUrl())
                .when()
                .get(path())
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(
                        new java.io.File("tests/api/tests/contract-schema.json")
                ));
    }

    @Test
    void responseShouldHaveCorrectDataTypesAndLocale() {
        Response response = RestAssured
                .given()
                .baseUri(baseUrl())
                .when()
                .get(path());

        response.then().statusCode(200);

        Object completed = response.jsonPath().get("completed");
        assertInstanceOf(Boolean.class, completed);

        String locale = response.jsonPath().getString("locale");
        assertTrue(
                Pattern.matches("^[A-Za-z]{2,8}(?:-[A-Za-z0-9]{1,8})*$", locale),
                "Locale should follow the basic BCP 47 language-tag structure"
        );

        assertValidTimestamp(response.jsonPath().getString("start_date"));
        assertValidTimestamp(response.jsonPath().getString("end_date"));
    }

    @Test
    void endDateShouldNotBeBeforeStartDate() {
        Response response = RestAssured
                .given()
                .baseUri(baseUrl())
                .when()
                .get(path());

        response.then().statusCode(200);

        OffsetDateTime start =
                OffsetDateTime.parse(response.jsonPath().getString("start_date"));
        OffsetDateTime end =
                OffsetDateTime.parse(response.jsonPath().getString("end_date"));

        assertFalse(end.isBefore(start));
    }

    @Test
    void suggestionListShouldContainOnlyMatchingSuggestions() {
        Response response = RestAssured
                .given()
                .baseUri(baseUrl())
                .when()
                .get(path());

        response.then().statusCode(200);

        String text = response.jsonPath().getString("text");
        String list = response.jsonPath().getString("suggestion_list");

        assertNotNull(text);
        assertNotNull(list);

        List<String> suggestions = Arrays.stream(list.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        for (String suggestion : suggestions) {
            assertTrue(
                    suggestion.toLowerCase().startsWith(text.toLowerCase()),
                    "Suggestion does not match submitted text: " + suggestion
            );
        }
    }

    @Test
    void missingRequiredFieldShouldFailContractValidation() {
        // This test is intended for an endpoint/test fixture that returns a response
        // with account_email removed. The base URL/path are configurable for that fixture.
        String negativePath = System.getProperty(
                "api.negativeMissingFieldPath",
                "/api/autocomplete/responses/negative/missing-account-email"
        );

        Response response = RestAssured
                .given()
                .baseUri(baseUrl())
                .when()
                .get(negativePath);

        assertNotEquals(200, response.statusCode(),
                "A response missing a required contract field should not be accepted as valid.");
    }

    @Test
    void invalidCompletedTypeShouldFailContractValidation() {
        String negativePath = System.getProperty(
                "api.negativeInvalidTypePath",
                "/api/autocomplete/responses/negative/completed-as-string"
        );

        Response response = RestAssured
                .given()
                .baseUri(baseUrl())
                .when()
                .get(negativePath);

        assertNotEquals(200, response.statusCode(),
                "An invalid completed data type should not be accepted as a valid contract response.");
    }

    private void assertValidTimestamp(String value) {
        assertNotNull(value);
        assertDoesNotThrow(() -> OffsetDateTime.parse(value));
    }
}
