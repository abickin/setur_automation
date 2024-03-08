package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CommonUtils {

    public static void waitAsSecond(int second) throws InterruptedException {
        Thread.sleep(second * 1000);
    }

    public static void clickWithBackgroundColor(WebElement element, WebDriver driver){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        element.click();
        js.executeScript("arguments[0].setAttribute('style', 'background-color:yellow')", element);
    }
}
