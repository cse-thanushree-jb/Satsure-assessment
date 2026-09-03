package ui.config;

public final class TestConfig {
    private TestConfig() {}

    public static String baseUrl() {
        return System.getProperty(
                "ui.baseUrl",
                "https://test.com/autocomplete-form"
        );
    }
}
