package com.recipeshare.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/auth",
        glue = {
                "com.recipeshare.steps",
                "com.recipeshare.hooks"  // ✅ AGREGADO
        },
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "html:target/reports/auth-report.html",
                "json:target/reports/auth-report.json"
        },
        tags = "@EP-A",
        monochrome = true
)
public class AuthRunner extends AbstractTestNGCucumberTests {
}