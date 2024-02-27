package tests;

import org.openqa.selenium.WebDriver;

public abstract class WebBrowser {
    private WebDriver webDriver;

    public WebDriver execute(WebBrowser webBrowser) {
      setWebDriver(webBrowser.getWebDriver());
      return webDriver;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }
    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
}
