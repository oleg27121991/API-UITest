package by.veremei.tests;

import by.veremei.config.ConfigReader;
import by.veremei.config.ProjectConfiguration;
import by.veremei.config.web.WebConfig;
import by.veremei.helpers.Attach;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {
    public static final WebConfig webConfig = ConfigReader.Instance.read();
    public static final String BASE_URI = "https://demoqa.com";
    @BeforeAll
    public static void setupAll() {
        ProjectConfiguration projectConfiguration = new ProjectConfiguration(webConfig);
        projectConfiguration.webConfig();
        RestAssured.baseURI = BASE_URI;
    }

    @BeforeEach
    void setup() {
        SelenideLogger.addListener("allure", new AllureSelenide());
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
