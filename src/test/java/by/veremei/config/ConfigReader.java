package by.veremei.config;

import by.veremei.config.web.WebConfig;
import org.aeonbits.owner.ConfigFactory;

public enum ConfigReader {
    Instance;
    public static final WebConfig webConfig = ConfigFactory.create(WebConfig.class, System.getProperties());
    public WebConfig read() {
        return webConfig;
    }
}
