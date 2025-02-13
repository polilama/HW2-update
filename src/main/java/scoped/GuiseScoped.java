package scoped;

import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.inject.Inject;

@ScenarioScoped
public class GuiseScoped {

    private WebDriver driver;

    @Inject
    public GuiseScoped(WebDriver driver) {
        this.driver = driver;
    }

    public static void open() {
    }

    public WebDriver getDriver() {
        if (driver == null) {
            driver = new ChromeDriver();
        }
        return driver;
    }
}
