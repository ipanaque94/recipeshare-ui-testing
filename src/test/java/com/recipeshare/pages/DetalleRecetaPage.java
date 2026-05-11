package com.recipeshare.pages;

import com.recipeshare.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DetalleRecetaPage extends BasePage {

    // El autor está en <p class="text-muted-foreground">por qa_prince</p>
    private final By autor        = By.xpath("//p[contains(text(),'por ')]");
    private final By titulo       = By.cssSelector("h1");
    private final By ingredientes = By.xpath("//*[contains(text(),'Ingredientes')]/following-sibling::*");
    private final By pasos        = By.xpath("//*[contains(text(),'Preparación') or contains(text(),'Pasos')]/following-sibling::*");
    private final By btnCalificar = By.xpath("//button[contains(text(),'Calificar')]");
    private final By btnComentar  = By.xpath("//button[contains(text(),'Comentar')]");
    private final By btnFavorito  = By.cssSelector("svg.lucide-heart");
    private final By btnEditar    = By.xpath("//button[contains(text(),'Editar') or .//svg[contains(@class,'square-pen')]]");

    public DetalleRecetaPage(WebDriver driver) {
        super(driver);
    }
    public boolean isEditarVisible() { return isVisible(btnEditar); }

    public void navigateTo(String recipeId) {
        driver.get(ConfigReader.getBaseUrl() + "/recipe/" + recipeId);
        $(titulo);
    }

    public boolean isAutorVisible()         { return isVisible(autor); }
    public boolean isTituloVisible()        { return isVisible(titulo); }
    public boolean areIngredientesVisible() { return isVisible(ingredientes); }
    public boolean arePasosVisible()        { return isVisible(pasos); }
    public boolean isCalificarVisible()     { return isVisible(btnCalificar); }
    public boolean isComentarVisible()      { return isVisible(btnComentar); }
    public boolean isFavoritoVisible() { return isVisible(btnFavorito); }
    public boolean isEditarHidden()         { return !isVisible(btnEditar); }
}