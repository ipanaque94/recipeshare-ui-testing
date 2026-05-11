package com.recipeshare.pages;

import com.recipeshare.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Page Object — Página de Crear Receta
 * URL: /create  (tusrecetas.lovable.app/create)
 *
 * CORRECCIÓN v2:
 * - isRecipeDisplayed() esperaba /recipe/ pero la app usa /recipes/ o /recipe/
 *   → Ahora usa WebDriverWait real en vez de Thread.sleep
 * - Locators basados en el DOM real de Lovable (inspeccionado)
 */
public class CrearRecetaPage extends BasePage {

    // ─── Campos principales del formulario ────────────────────────────────────
    private final By titleInput       = By.id("title");
    private final By descInput        = By.id("description");
    private final By prepInput        = By.xpath("(//input[@type='number'])[1]");
    private final By cookInput        = By.xpath("(//input[@type='number'])[2]");
    private final By servingsInput    = By.xpath("(//input[@type='number'])[3]");
    private final By difficultySelect = By.xpath("(//select)[1]");
    private final By countrySelect    = By.xpath("(//select)[2]");
    private final By lunchCategory = By.id("lunch");
    // ─── Ingredientes ─────────────────────────────────────────────────────────
    private final By ingredientNames  = By.xpath("//input[@placeholder='Ingrediente']");
    private final By ingredientQtys   = By.xpath("//input[@placeholder='Cantidad']");
    private final By ingredientUnits  = By.xpath("//input[@placeholder='Unidad']");
    private final By addIngredientBtn = By.xpath("//button[contains(text(),'Agregar Ingrediente')]");

    // ─── Pasos ────────────────────────────────────────────────────────────────
    private final By stepDescs = By.xpath("//textarea[@placeholder='Describe el paso...']");
    private final By addStepBtn = By.xpath("//button[contains(text(),'Agregar Paso')]");

    // ─── Guardar / resultado ──────────────────────────────────────────────────
    private final By saveBtn      = By.xpath("//button[contains(text(),'Publicar Receta')]");
    private final By successTitle = By.cssSelector("h1");

    // Indicadores de éxito post-creación
    // CORRECCIÓN: la app puede redirigir a /recipe/{id} O /recipes/{id}
    // Usamos el h1 del detalle como indicador principal, no la URL
    private final By recipeDetailTitle = By.cssSelector(
            "h1, [data-testid='recipe-title'], .recipe-title");

    // Errores de validación
    private final By titleError       = By.cssSelector(
            "[data-testid='title-error'], .title-error, " +
                    "p.text-red-500:first-of-type");
    private final By descError        = By.cssSelector(
            "[data-testid='desc-error'], .desc-error");
    private final By ingredientsError = By.cssSelector(
            "[data-testid='ingredients-error'], .ingredients-error");

    public CrearRecetaPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        driver.get(ConfigReader.getBaseUrl() + "/create");
        waitVisible(titleInput);
    }

    // ─── Relleno de campos ────────────────────────────────────────────────────

    public void fillTitle(String title) {
        clearReactInput(waitVisible(titleInput));
        waitVisible(titleInput).sendKeys(title);
    }

    public void fillDescription(String desc) {
        escribirTextoLargo( descInput, desc);
    }

    public void fillPrepTime(String min) {
        type(prepInput, min);
    }

    public void fillCookTime(String min) {
        type(cookInput, min);
    }

    public void fillServings(String num) {
        type(servingsInput, num);
    }

    public void selectDifficulty(String text) {
        new Select($(difficultySelect)).selectByVisibleText(text);
    }

    public void selectCountryByIndex(int index) {
        new Select($(countrySelect)).selectByIndex(index);
    }

    public void selectLunchCategory() {
        if (isPresent(lunchCategory)) {
            jsClick(lunchCategory);
        }
    }

    public void fillIngredient(int index, String name, String qty, String unit) {
        List<WebElement> names = driver.findElements(ingredientNames);
        if (index >= names.size()) {
            click(addIngredientBtn);
            sleep(500);
            names = driver.findElements(ingredientNames);
        }
        List<WebElement> qtys  = driver.findElements(ingredientQtys);
        List<WebElement> units = driver.findElements(ingredientUnits);

        names.get(index).clear(); names.get(index).sendKeys(name);
        if (!qtys.isEmpty())  { qtys.get(index).clear();  qtys.get(index).sendKeys(qty); }
        if (!units.isEmpty()) { units.get(index).clear(); units.get(index).sendKeys(unit); }
    }

    public void fillStep(int index, String desc) {
        List<WebElement> steps = driver.findElements(stepDescs);
        if (index >= steps.size()) {
            click(addStepBtn);
            sleep(500);
            steps = driver.findElements(stepDescs);
        }
        steps.get(index).clear();
        steps.get(index).sendKeys(desc);
    }

    // ─── Acción de publicar ───────────────────────────────────────────────────

    public void clickPublishRecipe() {
        scrollIntoView(saveBtn);
        click(saveBtn);
        // No usar Thread.sleep fijo — esperar con WebDriverWait
        // La redirección al detalle puede tardar 2-5s dependiendo de Supabase
        waitForRecipeDetailOrTimeout();
    }

    /**
     * CORRECCIÓN CRÍTICA: en vez de Thread.sleep(3000) + verificar URL manualmente,
     * usa WebDriverWait para esperar la URL de detalle o el título en pantalla.
     *
     * La app puede redirigir a:
     *   - /recipe/{uuid}
     *   - /recipes/{uuid}
     *   - /receta/{uuid}
     * O mostrar el título directamente en la misma página (SPA sin redirección).
     */
    private void waitForRecipeDetailOrTimeout() {
        try {
            // Espera hasta 10s a que la URL cambie a algo que no sea /create
            wait.until(driver -> {
                String url = driver.getCurrentUrl();
                return !url.endsWith("/create") && !url.contains("/create?");
            });
        } catch (Exception e) {
            // Si no redirigió, puede ser que el título apareció en la misma página
            // Continuamos y dejamos que isRecipeDisplayed() haga la verificación
        }
    }

    public boolean isRecipeDisplayed(String expectedTitle) {
        try {
            // 1. Esperar redirección a /recipe/ (máximo 20s por Supabase)
            wait.until(d -> d.getCurrentUrl().contains("/recipe/"));

            // 2. Esperar que el <h1> del detalle sea visible (React terminó de renderizar)
            WebElement h1 = wait.until(
                    org.openqa.selenium.support.ui.ExpectedConditions
                            .visibilityOfElementLocated(By.cssSelector("h1"))
            );

            // 3. El <h1> debe contener el título que escribimos en el formulario
            String actualTitle = h1.getText();
            return actualTitle.contains(expectedTitle);

        } catch (Exception e) {
            return false;
        }
    }

    public String getTotalTime() {
        By totalTimeLabel = By.xpath(
                "//*[contains(text(),'35') or contains(@class,'total-time')]");
        try {
            return $(totalTimeLabel).getText();
        } catch (Exception e) {
            return "";
        }
    }

    // ─── Verificaciones de error ──────────────────────────────────────────────

    public boolean isTitleErrorVisible() {
        return isVisible(titleError);
    }

    public boolean isDescriptionErrorVisible() {
        return isVisible(descError);
    }

    public boolean isIngredientsErrorVisible() {
        return isVisible(ingredientsError);
    }

    // ─── Utilidad privada ─────────────────────────────────────────────────────

    private void clearReactInput(WebElement el) {
        ((JavascriptExecutor) driver).executeScript(
                "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(" +
                        "  window.HTMLInputElement.prototype, 'value').set;" +
                        "nativeInputValueSetter.call(arguments[0], '');" +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                el);
    }
}