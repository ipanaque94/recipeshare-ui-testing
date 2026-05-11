package com.recipeshare.pages;

import com.recipeshare.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By emailInput    = By.cssSelector("input[type='email'], input[name='email'], input[placeholder*='mail' i]");
    private final By passwordInput = By.cssSelector("input[type='password']");
    private final By submitBtn     = By.cssSelector("button[type='submit']");
    private final By loggedInIndicator = By.xpath("//*[contains(text(),'Nueva Receta') or contains(text(),'¡Bienvenido de vuelta!')]");
    private final By authForm      = By.cssSelector("input[type='password']");

    // Menú de usuario
    private final By avatarMenuBtn = By.xpath("//button[@aria-haspopup='menu']");
    private final By logoutOption  = By.xpath("//div[@role='menuitem'][contains(.,'Cerrar')]");
    private final By errorMsg = By.cssSelector("[role='alert'], .text-red-500, .text-destructive, .error-message");
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        driver.get(ConfigReader.getBaseUrl() + "/auth");
        $(authForm);
    }

    public void enterEmail(String email)    { type(emailInput, email); }
    public void enterPassword(String pass)  { type(passwordInput, pass); }

    public void clickSubmit() {
        click(submitBtn);
        sleep(2000);
    }

    public void clickLogout() {
        // Logout real en SPA con Supabase: borrar token y refrescar
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(
                        "localStorage.clear(); " +
                                "sessionStorage.clear(); " +
                                "window.location.href = '/auth';"
                );
        sleep(3000);
    }
    public String getErrorText() {
        try {
            return $(errorMsg).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isLoggedIn()   { return isVisible(loggedInIndicator); }
    public boolean isOnAuthPage() { return isVisible(authForm); }
}