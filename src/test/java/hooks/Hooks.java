package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import driver.DriverManager;

public class Hooks {
    @Before
    public void setUp() {
        DriverManager.getDriver();
    }

    @After
    public void close() {
        DriverManager.quitDriver();
    }
}