package fr.ardole.mm.gitlab.slash.command;

import fr.ardole.mm.gitlab.configuration.SlashConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;

public class Browser {

    @Autowired
    SlashConfig slashConfig;

    private static Browser browser;
    private WebDriver webDriver;

    private Browser() {
        webDriver = new FirefoxDriver();
    }

    public static Browser getInstance() {
        if (browser == null) {
            browser = new Browser();
        }
        return browser;
    }

    public WebDriver getDriver() {
        return this.webDriver;
    }
}
