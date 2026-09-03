package ui.tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import ui.config.TestConfig;
import ui.pages.AutocompletePage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AutocompleteFormTest {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private AutocompletePage form;

    @BeforeEach
    void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
        );
        context = browser.newContext(
                new Browser.NewContextOptions().setLocale("en-IN")
        );
        page = context.newPage();
        form = new AutocompletePage(page);
        form.open(TestConfig.baseUrl());
    }

    @AfterEach
    void tearDown() {
        context.close();
        browser.close();
        playwright.close();
    }

    @Test
    void shouldFilterSuggestionsByPrefix() {
        form.enterText("agile");

        List<String> suggestions = form.visibleSuggestions();

        assertEquals(3, suggestions.size());
        assertTrue(suggestions.contains("agile methodology"));
        assertTrue(suggestions.contains("agile methodology process"));
        assertTrue(suggestions.contains("agile methodology process testing"));
    }

    @Test
    void shouldPopulateInputAfterSuggestionSelection() {
        form.enterText("agile");
        form.clickSuggestion("agile methodology");

        assertEquals("agile methodology", form.inputValue());
    }

    @Test
    void shouldSupportTabNavigation() {
        form.pressTab();
        assertTrue(page.locator(":focus").count() > 0);
    }

    @Test
    void shouldSupportEscapeInteraction() {
        form.enterText("agile");
        form.pressEscape();

        assertFalse(form.successVisible());
    }

    @Test
    void shouldSubmitWithEnterForSelectedValue() {
        form.enterText("agile");
        form.clickSuggestion("agile methodology");
        form.pressEnter();

        assertTrue(
                form.successVisible() || form.errorVisible(),
                "The application should show a submission result."
        );
    }

    @Test
    void shouldShowResultAfterClickingNext() {
        form.enterText("agile");
        form.clickSuggestion("agile methodology");
        form.clickNext();

        assertTrue(
                form.successVisible() || form.errorVisible(),
                "The application should show a submission result."
        );
    }
}
