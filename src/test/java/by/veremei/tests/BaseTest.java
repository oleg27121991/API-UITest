package by.veremei.tests;

import by.veremei.helpers.Attach;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    private static final by.veremei.config.web.WebConfig webConfig = by.veremei.config.ConfigReader.Instance.read();
    public static final String BASE_URI = "https://demoqa.com";
    @BeforeAll
    public static void setupAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        by.veremei.config.ProjectConfiguration projectConfiguration = new by.veremei.config.ProjectConfiguration(webConfig);
        projectConfiguration.webConfig();
        RestAssured.baseURI = BASE_URI;
    }

    @AfterEach
    void addAttachment() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        Selenide.closeWebDriver();
    }
}
