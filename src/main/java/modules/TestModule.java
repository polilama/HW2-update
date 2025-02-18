package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import driver.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    public WebDriver provideWebDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }
}

