package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import driver.DriverManager;

public class Hooks {
    private final DriverManager driverManager = new DriverManager();

    @Before
    public void setUp() {
        driverManager.getDriver();
    }

    @After
    public void close() {
        driverManager.quitDriver();
    }
}