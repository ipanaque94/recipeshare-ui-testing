package com.recipeshare.steps;

import com.recipeshare.pages.CrearRecetaPage;
import com.recipeshare.pages.DetalleRecetaPage;
import com.recipeshare.pages.EditarRecetaPage;
import com.recipeshare.pages.LoginPage;
import com.recipeshare.utils.ConfigReader;
import io.cucumber.java.es.*;
import org.testng.Assert;

public class RecetaSteps {

    private final ScenarioContext ctx = ScenarioContext.getInstance();
    private String recipeTitle;

    // ── LOGIN AUXILIAR ───────────────────────────────

    private void login() {
        LoginPage login = new LoginPage(ctx.getDriver());
        login.navigateTo();
        login.enterEmail(ConfigReader.getEmail());
        login.enterPassword(ConfigReader.getPassword());
        login.clickSubmit();
        Assert.assertTrue(login.isLoggedIn(), "❌ Login previo falló");
    }

    // ═══════════════════════════════════════════════════
    // CREAR RECETA
    // ═══════════════════════════════════════════════════

    @Dado("que el usuario autenticado está en el formulario de crear receta")
    public void autenticadoEnCrearReceta() {
        login();
        CrearRecetaPage page = new CrearRecetaPage(ctx.getDriver());
        page.navigateTo();
        ctx.setPage(page);
    }

    @Cuando("completa todos los campos obligatorios con datos válidos")
    public void completaCamposObligatorios() {
        CrearRecetaPage page = (CrearRecetaPage) ctx.getPage();
        this.recipeTitle = "Receta Auto " + System.currentTimeMillis();
        page.fillTitle(recipeTitle);
        page.fillDescription("Descripción generada automáticamente");
        page.fillPrepTime("15");
        page.fillCookTime("20");
        page.fillServings("4");
        page.selectDifficulty("Media");
        page.selectCountryByIndex(1);
        page.selectLunchCategory();
        page.fillIngredient(0, "Ají amarillo", "2", "tazas");
        page.fillStep(0, "Hervir el ají amarillo durante 10 minutos");
    }

    @Cuando("guarda la receta")
    public void guardaReceta() {
        ((CrearRecetaPage) ctx.getPage()).clickPublishRecipe();
    }

    @Entonces("debería ver la receta creada con sus datos correctos")
    public void verRecetaCreada() {
        Assert.assertTrue(((CrearRecetaPage) ctx.getPage()).isRecipeDisplayed(recipeTitle),
                "❌ No se visualizó la receta: " + recipeTitle);
    }

    // ═══════════════════════════════════════════════════
    // VER DETALLE
    // ═══════════════════════════════════════════════════

    @Dado("que existe una receta publicada con datos completos")
    public void recetaPublicadaConDatos() {
        DetalleRecetaPage page = new DetalleRecetaPage(ctx.getDriver());
        page.navigateTo(ConfigReader.getRecipeId());
        ctx.setPage(page);
    }

    @Dado("que soy visitante sin sesión en el detalle")
    public void visitanteSinSesion() {
        DetalleRecetaPage page = new DetalleRecetaPage(ctx.getDriver());
        page.navigateTo(ConfigReader.getRecipeId());
        ctx.setPage(page);
    }

    @Dado("que soy usuario logueado viendo receta ajena")
    public void usuarioLogueadoViendoAjena() {
        login();
        DetalleRecetaPage page = new DetalleRecetaPage(ctx.getDriver());
        page.navigateTo(ConfigReader.getRecipeId());
        ctx.setPage(page);
    }

    @Entonces("veo portada autor título ingredientes y pasos")
    public void veoElementosDetalle() {
        DetalleRecetaPage page = (DetalleRecetaPage) ctx.getPage();
        Assert.assertTrue(page.isTituloVisible(),        "Título debe ser visible");
        Assert.assertTrue(page.isAutorVisible(),         "Autor debe ser visible");
        Assert.assertTrue(page.areIngredientesVisible(), "Ingredientes deben ser visibles");
        Assert.assertTrue(page.arePasosVisible(),        "Pasos deben ser visibles");
    }

    @Entonces("los botones Calificar y Comentar no están disponibles")
    public void botonesNoDisponibles() {
        DetalleRecetaPage page = (DetalleRecetaPage) ctx.getPage();
        Assert.assertFalse(page.isCalificarVisible(), "TC46: Calificar no debe ser visible");
        Assert.assertFalse(page.isComentarVisible(),  "TC46: Comentar no debe ser visible");
    }

    @Entonces("el botón Guardar está visible y habilitado")
    public void botonGuardarVisible() {
        Assert.assertTrue(((DetalleRecetaPage) ctx.getPage()).isFavoritoVisible(),
                "TC47: El botón de favorito (corazón) debe ser visible");
    }

    @Entonces("no veo el botón de editar")
    public void noVeoBotonEditar() {
        Assert.assertTrue(((DetalleRecetaPage) ctx.getPage()).isEditarHidden(),
                "TC49: Editar no debe aparecer para no dueño");
    }

    // ═══════════════════════════════════════════════════
    // EDITAR RECETA
    // ═══════════════════════════════════════════════════

    @Dado("que soy el autor logueado en editar receta")
    public void autorLogueadoEnEditar() {
        login();
        EditarRecetaPage page = new EditarRecetaPage(ctx.getDriver());
        page.navigateTo(ConfigReader.getRecipeId());
        ctx.setPage(page);
    }

    @Cuando("cambio el título a {string}")
    public void cambioTitulo(String nuevoTitulo) {
        ((EditarRecetaPage) ctx.getPage()).clearAndTypeTitle(nuevoTitulo);
    }

    @Cuando("hago clic en guardar cambios")
    public void clicGuardarCambios() {
        ((EditarRecetaPage) ctx.getPage()).clickGuardar();
    }

    @Entonces("los cambios se reflejan inmediatamente en el detalle")
    public void cambiosReflejados() {
        DetalleRecetaPage detalle = new DetalleRecetaPage(ctx.getDriver());
        detalle.navigateTo(ConfigReader.getRecipeId());
        Assert.assertTrue(detalle.isTituloVisible(), "Título actualizado debe ser visible");
    }
}