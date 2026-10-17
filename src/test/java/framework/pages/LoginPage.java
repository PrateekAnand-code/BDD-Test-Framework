package framework.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public final class LoginPage {
    private final Page page;
    public LoginPage(Page page) { this.page = page; }
    public void open(String baseUrl) { page.navigate(baseUrl.replaceAll("/$", "") + "/login"); }
    public void login(String username, String password) {
        page.getByLabel("Username").fill(username);
        page.getByLabel("Password").fill(password);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in")).click();
    }
    public void assertMessage(String expected) { assertThat(page.getByRole(AriaRole.STATUS)).hasText(expected); }
}
