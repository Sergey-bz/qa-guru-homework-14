package ru.exist.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import ru.exist.helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BaseTest {

    @BeforeAll
    static void configure() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (System.getProperty("remote_url") != null) {
            Configuration.remote = System.getProperty("remote_url");
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);
        }

        Configuration.browserCapabilities = capabilities;
        Configuration.browser = System.getProperty("browser_name", "chrome");
        Configuration.browserVersion = System.getProperty("browser_version", "100.0");
        Configuration.browserSize = System.getProperty("browser_size", "1920x1080");
        Configuration.baseUrl = "https://exist.ru";
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        if (System.getProperty("remote_url") != null) {
            Attach.addVideo();
        }
    }
}
