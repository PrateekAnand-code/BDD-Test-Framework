package framework.support;

import com.microsoft.playwright.*;
import framework.config.TestConfig;
import io.restassured.response.Response;
import java.io.IOException;
import java.nio.file.Path;

/** PicoContainer creates one context per Cucumber scenario; no shared mutable state. */
public final class TestContext implements AutoCloseable {
    public final TestConfig config = new TestConfig();
    private DemoApplication demo;
    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;
    private Response response;

    public String baseUrl(String kind) throws IOException {
        String configured = config.get(kind + ".baseUrl", "").strip();
        if (!configured.isEmpty()) return configured;
        if (demo == null) demo = new DemoApplication();
        return demo.baseUrl();
    }

    public Page page() {
        if (page == null) throw new IllegalStateException("UI browser has not been started; add @ui");
        return page;
    }
    public void startBrowser() {
        String selected = config.get("browser", "chromium");
        if (!java.util.Set.of("chromium", "firefox", "webkit").contains(selected))
            throw new IllegalArgumentException("browser must be chromium, firefox or webkit: " + selected);
        playwright = Playwright.create();
        BrowserType type = switch (selected) {
            case "firefox" -> playwright.firefox();
            case "webkit" -> playwright.webkit();
            default -> playwright.chromium();
        };
        browser = type.launch(new BrowserType.LaunchOptions().setHeadless(config.flag("headless", true)));
        browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(1440, 900));
        browserContext.setDefaultTimeout(Double.parseDouble(config.get("timeout.ms", "10000")));
        browserContext.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true).setSources(true));
        page = browserContext.newPage();
    }
    public void response(Response value) { response = value; }
    public Response response() {
        if (response == null) throw new IllegalStateException("Send an API request before asserting the response");
        return response;
    }
    public void stopTrace(Path destination) {
        if (browserContext != null) browserContext.tracing().stop(new Tracing.StopOptions().setPath(destination));
    }
    public boolean hasPage() { return page != null && !page.isClosed(); }
    @Override public void close() {
        try {
            if (browserContext != null) browserContext.close();
        } finally {
            try { if (browser != null) browser.close(); }
            finally {
                try { if (playwright != null) playwright.close(); }
                finally { if (demo != null) demo.close(); }
            }
        }
    }
}
