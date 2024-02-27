package tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.ReusubleMethods;

import java.time.Duration;
import java.util.List;

public class calisma {

    @Test
    public void deneme() {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        driver.get("https://www.setur.com.tr/");
        driver.findElement(By.xpath("//span[@class='ins-close-button']")).click();
        driver.findElement(By.xpath("//a[@id='CybotCookiebotDialogBodyLevelButtonLevelOptinDeclineAll']")).click();
        driver.findElement(By.xpath("//span[@class='sc-fd984615-0 dzbodO']")).click();
        ReusubleMethods.bekle(1);
        driver.findElement(By.xpath("(//span[@class='sc-eb82d810-0 eClCrJ'])[48]")).click();


        List<WebElement> calendarBodyList = driver.findElements(By.xpath("(//table[@class='CalendarMonth_verticalSpacing CalendarMonth_verticalSpacing_1'])[2]"));
        System.out.println("size" +calendarBodyList.size());

        for (int i = 0; i < calendarBodyList.size(); i++) {
            System.out.println(i+1 +". satir : "+calendarBodyList.get(i).getText());
        }
    }

}
