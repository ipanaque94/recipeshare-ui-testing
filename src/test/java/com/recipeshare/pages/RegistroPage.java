package com.recipeshare.pages;

import com.recipeshare.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistroPage extends BasePage {

    private final By emailInput    = By.cssSelector("input[type='email'], input[name='email']");
    private final By passwordInput = By.cssSelector("input[type='password']");
    private final By submitBtn     = By.cssSelector("button[type='submit']");
    private final By errorMsg      = By.cssSelector("[role='alert'], .text-red-500, .text-destructive");

    public RegistroPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        driver.get(ConfigReader.getBaseUrl() + "/auth");
        $(passwordInput);
    }

    public void fillForm(String email, String password) {
        type(emailInput, email);
        type(passwordInput, password);
    }

    public void clickRegistrar() {
        click(submitBtn);
        try { Thread.sleep(5000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    public boolean isErrorVisible() { return isVisible(errorMsg); }
    public String getErrorText()    { return $(errorMsg).getText(); }
}