package test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.cucumber.java.ru.Пусть;
import modules.TestModule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import scoped.GuiseScoped;

import javax.inject.Inject;

public class TestBase {
    @Inject
    protected WebDriver driver;
    @Inject
    private GuiseScoped guiseScoped;
    @Inject
    private CourseTest courseTest;
    @Inject
    private Injector injector;


    @Пусть("я открываю браузер Chrome")
    @BeforeAll
    public void setUp() {
        injector = Guice.createInjector(new TestModule());
        injector.injectMembers(this);
    }

    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}