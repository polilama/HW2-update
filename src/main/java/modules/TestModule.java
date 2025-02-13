package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    public WebDriver provideWebDriver() {
        System.setProperty("webdriver.chrome.driver", "C:/path/to/chromedriver.exe");
        return new ChromeDriver();
    }
}

