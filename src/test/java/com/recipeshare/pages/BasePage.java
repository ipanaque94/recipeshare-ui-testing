package com.recipeshare.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    protected WebElement $(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void escribir(By locator, String texto) {
        WebElement el = wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(locator));

        // 1. Click para enfocar el campo
        el.click();

        // 2. Seleccionar todo el contenido actual
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"));

        // 3. Borrar con DELETE
        el.sendKeys(Keys.DELETE);

        // 4. Escribir el nuevo valor caracter por caracter
        //    React detecta cada keystroke
        el.sendKeys(texto);

        // 5. Disparar evento 'change' por si React lo necesita
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent("
                        + "new Event('change', {bubbles:true}));",
                el
        );
    }

    protected void escribirTextoLargo(
            By locator, String texto) {
        WebElement el = wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(locator));

        el.click();
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        el.sendKeys(Keys.DELETE);

        // Inyectar el valor via JavaScript
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1];",
                el, texto
        );

        // Disparar eventos React obligatorios
        ((JavascriptExecutor) driver).executeScript(
                "var ev = new Event('input', {bubbles:true});"
                        + "arguments[0].dispatchEvent(ev);",
                el
        );
        ((JavascriptExecutor) driver).executeScript(
                "var ev = new Event('change', {bubbles:true});"
                        + "arguments[0].dispatchEvent(ev);",
                el
        );

        // Pequeño tap al campo para confirmar focus
        el.sendKeys(" ");
        el.sendKeys(Keys.BACK_SPACE);
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        WebElement el = $(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected boolean isVisible(By locator) {
        try {
            return wait.until(ExpectedConditions
                            .visibilityOfElementLocated(locator))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean noEsVisible(By locator) {
        try {
            wait.until(ExpectedConditions
                    .invisibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Devuelve el texto de un elemento */
    protected String textOf(By locator) {
        return $(locator).getText();
    }

    /** Devuelve el valor de un atributo */
    protected String attrOf(By locator, String attribute) {
        return $(locator).getAttribute(attribute);
    }

    /** Busca todos los elementos (lista vacía si no hay) */
    protected List<WebElement> all(By locator) {
        return driver.findElements(locator);
    }

    /** Select dropdown por texto visible */
    protected void selectText(By locator, String text) {
        new Select($(locator)).selectByVisibleText(text);
    }

    /** Select dropdown por índice */
    protected void selectIndex(By locator, int index) {
        new Select($(locator)).selectByIndex(index);
    }

    /** URL actual */
    protected String url() {
        return driver.getCurrentUrl();
    }

    protected void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /** Título de la página */
    protected String title() {
        return driver.getTitle();
    }
    // ─── DEBUG ─────────────────────────────────────────

    protected void dumpDom(String context) {
        log.error("═══════════════════════════════════════════════════════");
        log.error("❌ FALLÓ: {}", context);
        log.error("🌐 URL: {}", url());
        log.error("📄 Título: {}", title());
        try {
            String html = driver.findElement(By.tagName("body")).getAttribute("innerHTML");
            java.nio.file.Path p = java.nio.file.Path.of("target", "dom-dump-" + context.replaceAll("[^a-zA-Z0-9]", "_") + ".html");
            java.nio.file.Files.createDirectories(p.getParent());
            java.nio.file.Files.writeString(p, html);
            log.error("💾 HTML guardado: {}", p.toAbsolutePath());
        } catch (Exception e) {
            log.error("No se pudo guardar DOM: {}", e.getMessage());
        }
        log.error("═══════════════════════════════════════════════════════");
    }

    /** Espera a que el elemento sea visible (alias de $) */
    protected WebElement waitVisible(By locator) {
        return $(locator);
    }

    /** true si el elemento existe en el DOM (aunque no sea visible) */
    protected boolean isPresent(By locator) {
        try {
            return !driver.findElements(locator).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /** Hace scroll hasta el elemento usando JavaScript */
    protected void scrollIntoView(By locator) {
        WebElement el = driver.findElement(locator);
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", el);
    }

    protected void jsClick(By locator) {
        WebElement el = driver.findElement(locator);
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'}); arguments[0].click();", el);
    }
}