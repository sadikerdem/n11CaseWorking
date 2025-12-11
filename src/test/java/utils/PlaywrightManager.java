package utils;

import com.microsoft.playwright.*;

public class PlaywrightManager {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;

    public static void start() {
        if (playwright == null) {
            playwright = Playwright.create();
            browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false)
            );
            context = browser.newContext();
            page = context.newPage();
        }

    }

    public static Page getPage() {
        return page;
    }

    public static void close() {
        try { if (page != null) page.close(); } catch (Exception ignored) {}
        try { if (context != null) context.close(); } catch (Exception ignored) {}
        try { if (browser != null) browser.close(); } catch (Exception ignored) {}
        try { if (playwright != null) playwright.close(); } catch (Exception ignored) {}

        page = null;
        context = null;
        browser = null;
        playwright = null;
    }
}