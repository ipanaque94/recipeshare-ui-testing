package com.recipeshare.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "com.recipeshare.steps",
                "com.recipeshare.hooks"
        },
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "html:target/cucumber-reports/report.html",
                "json:target/cucumber-reports/report.json"
        },
        tags = "@regresion or @smoke",  // Cambia según lo que quieras correr
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}