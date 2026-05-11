package com.recipeshare.hooks;

import com.recipeshare.steps.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Hooks {

    private final ScenarioContext ctx = ScenarioContext.getInstance();

    @Before
    public void setUp(Scenario scenario) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // Descomenta la línea siguiente en CI/CD:
        // options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        ctx.setDriver(driver);
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = ctx.getDriver();
        if (driver == null) {
            return;
        }
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png",
                        "Screenshot — " + scenario.getName());
            } catch (Exception e) {
                // Ignorar si no se puede tomar screenshot
            }
        }
        driver.quit();
    }
}