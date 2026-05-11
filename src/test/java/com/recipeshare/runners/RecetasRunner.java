package com.recipeshare.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/recetas",
        glue = {
                "com.recipeshare.steps",
                "com.recipeshare.hooks"  // ✅ AGREGADO
        },
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "html:target/reports/recetas-report.html",
                "json:target/reports/recetas-report.json"
        },
        tags = "@EP-C",
        monochrome = true
)
public class RecetasRunner extends AbstractTestNGCucumberTests {
}