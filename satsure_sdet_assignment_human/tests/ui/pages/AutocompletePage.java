package ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.ArrayList;
import java.util.List;

public class AutocompletePage {
    private final Page page;
    private final Locator input;
    private final Locator suggestions;
    private final Locator nextButton;
    private final Locator errorMessage;
    private final Locator successMessage;

    public AutocompletePage(Page page) {
        this.page = page;
        this.input = page.locator("#input-field");
        this.suggestions = page.locator("ul.suggestions li");
        this.nextButton = page.locator("#next-button");
        this.errorMessage = page.locator(".error-message");
        this.successMessage = page.locator(".success-container");
    }

    public void open(String url) {
        page.navigate(url);
    }

    public void enterText(String value) {
        input.fill(value);
    }

    public void clickSuggestion(String value) {
        suggestions.filter(new Locator.FilterOptions().setHasText(value)).click();
    }

    public String inputValue() {
        return input.inputValue();
    }

    public List<String> visibleSuggestions() {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < suggestions.count(); i++) {
            Locator item = suggestions.nth(i);
            if (item.isVisible()) {
                values.add(item.innerText().trim());
            }
        }
        return values;
    }

    public void clickNext() {
        nextButton.click();
    }

    public void pressEnter() {
        input.press("Enter");
    }

    public void pressEscape() {
        input.press("Escape");
    }

    public void pressTab() {
        page.keyboard().press("Tab");
    }

    public boolean successVisible() {
        return successMessage.isVisible();
    }

    public boolean errorVisible() {
        return errorMessage.isVisible();
    }
}
