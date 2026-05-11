package com.recipeshare.steps;

import org.openqa.selenium.WebDriver;

public class ScenarioContext {

    private static final ThreadLocal<ScenarioContext> INSTANCE
            = ThreadLocal.withInitial(ScenarioContext::new);

    private WebDriver driver;
    private Object currentPage;

    private ScenarioContext() {}

    public static ScenarioContext getInstance() {
        return INSTANCE.get();
    }

    public WebDriver getDriver()         { return driver; }
    public void setDriver(WebDriver d)   { this.driver = d; }
    public Object getPage()              { return currentPage; }
    public void setPage(Object page)     { this.currentPage = page; }
}