package com.recipeshare.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/perfil",
        glue = {
                "com.recipeshare.steps",
                "com.recipeshare.hooks"  // ✅ AGREGADO
        },
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "html:target/reports/perfil-report.html",
                "json:target/reports/perfil-report.json"
        },
        tags = "@EP-B",
        monochrome = true
)
public class PerfilRunner extends AbstractTestNGCucumberTests {
}