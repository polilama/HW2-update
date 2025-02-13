package hooks;


import com.google.inject.Inject;
import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;
import scoped.GuiseScoped;

public class Hooks {
    @Inject
    private GuiseScoped scenarioScoped;
    @After
    public void close(){
        WebDriver driver = scenarioScoped.getDriver();

        if(driver != null) {
            driver.quit();
        }

    }
}