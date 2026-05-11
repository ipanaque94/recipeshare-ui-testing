package com.recipeshare.pages;

import com.recipeshare.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PerfilPage extends BasePage {

    // Navegación: avatar → Mi Perfil
    private final By avatarMenu   = By.xpath("//button[@aria-haspopup='menu']");
    private final By miPerfil     = By.xpath("//div[@role='menuitem'][contains(.,'Mi Perfil')]");

    // Campos del formulario (HTML real de tu app)
    private final By nombreInput  = By.id("username");
    private final By bioInput     = By.cssSelector("textarea[placeholder*='bio' i], textarea[name='bio'], textarea");
    private final By saveBtn = By.xpath("//button[contains(text(),'Guardar Cambios')]");

    // Mensajes
    private final By successMsg   = By.cssSelector("[role='status'], .toast-success, .text-green-600");
    private final By errorMsg     = By.cssSelector("[role='alert'], .text-red-500, .text-destructive");

    public PerfilPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        // Navegación directa por URL — más estable que menú desplegable en SPA
        driver.get(ConfigReader.getBaseUrl() + "/profile");
        $(nombreInput);
    }

    public void setNombre(String nombre) {
        escribir(nombreInput, nombre);
    }

    public void setBio(String bio) {
       escribirTextoLargo(bioInput, bio);
    }

    public void guardar() {
        click(saveBtn);
        sleep(2000);
    }

    public boolean isSuccessVisible() {
        // Esperar hasta 5 segundos por el toast
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return isVisible(successMsg);
    }


    public boolean isFieldErrorVisible() {
        return isVisible(errorMsg);

    }

    public String getNombreValue() {
        return driver.findElement(nombreInput).getAttribute("value");
    }

    public String getBioValue() {
        return driver.findElement(bioInput).getAttribute("value");
    }
}