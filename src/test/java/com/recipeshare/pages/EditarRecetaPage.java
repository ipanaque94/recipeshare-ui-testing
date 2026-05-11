package com.recipeshare.pages;

import com.recipeshare.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EditarRecetaPage extends BasePage {

    private final By titleInput   = By.id("title");
    private final By saveBtn      = By.xpath("//button[contains(text(),'Actualizar Receta')]");
    private final By detailTitle  = By.cssSelector("h1");

    public EditarRecetaPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo(String recipeId) {
        driver.get(ConfigReader.getBaseUrl() + "/edit/" + recipeId);
        $(titleInput);
    }

    public void clearAndTypeTitle(String text) {
        type(titleInput, text);
    }

    public void clickGuardar() {
        click(saveBtn);
        sleep(2000);
    }

    public String getTitleFromDetail() {
        return $(detailTitle).getText();
    }
}