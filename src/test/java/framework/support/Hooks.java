package framework.support;

import com.microsoft.playwright.Page;
import io.cucumber.java.*;
import java.nio.file.*;
import java.util.UUID;

public final class Hooks {
    private final TestContext context;
    public Hooks(TestContext context) { this.context = context; }
    @Before("@ui") public void startUi() { context.startBrowser(); }

    @After public void finish(Scenario scenario) throws Exception {
        try {
            if (scenario.isFailed() && context.hasPage()) {
                String id = UUID.randomUUID().toString();
                Path directory = Path.of("target", "artifacts", id);
                Files.createDirectories(directory);
                scenario.log("Failure evidence: " + directory);
                try {
                    byte[] screenshot = context.page().screenshot(new Page.ScreenshotOptions().setFullPage(true));
                    Files.write(directory.resolve("failure.png"), screenshot);
                    scenario.attach(screenshot, "image/png", "Failure screenshot");
                } catch (RuntimeException e) { scenario.log("Screenshot unavailable: " + e.getMessage()); }
                try { context.stopTrace(directory.resolve("trace.zip")); }
                catch (RuntimeException e) { scenario.log("Trace unavailable: " + e.getMessage()); }
            }
        } finally { context.close(); }
    }
}
