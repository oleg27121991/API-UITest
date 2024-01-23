package by.veremei.config;

import by.veremei.config.web.WebConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.function.Supplier;

public class WebDriverProvider implements Supplier<WebDriver> {
    private final WebConfig webConfig = ConfigReader.Instance.read();

    @Override
    public WebDriver get() {
        WebDriver driver = createWebDriver();
        driver.get(webConfig.baseUrl());
        return driver;
    }

    private WebDriver createWebDriver() {
        if (!webConfig.isRemote()) {
            return createLocalWebDriver();
        } else {
            return createRemoteWebDriver();
        }
    }

    private WebDriver createLocalWebDriver() {
        switch (webConfig.getBrowser()) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            default:
                throw new IllegalArgumentException("No such browser: " + webConfig.getBrowser());
        }
    }

    private WebDriver createRemoteWebDriver() {
        switch (webConfig.getBrowser()) {
            case CHROME:
                return new RemoteWebDriver(webConfig.getRemoteUrl(), new ChromeOptions());
            case FIREFOX:
                return new RemoteWebDriver(webConfig.getRemoteUrl(), new FirefoxOptions());
            default:
                throw new IllegalArgumentException("No such browser: " + webConfig.getBrowser());
        }
    }
}
