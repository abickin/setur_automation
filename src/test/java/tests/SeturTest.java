package tests;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.util.StringUtil;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static tests.Locators.*;

public class SeturTest {

    private WebDriver driver;

    public void initialSetting(String browserName) {

        switch (browserName) {
            case "Chrome":
                driver = new ChromeDriver();
                break;
            case "Firefox":
                driver = new FirefoxDriver();
                break;
            default:
                driver = new EdgeDriver();
                break;
        }
    }

    public void setOptions() {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    public void openPage(String url) {
        driver.navigate().to(url);
    }

    public void closePopupsAndCookies() {
        driver.findElement(By.xpath(CLOSE_POPUP_BUTTON)).click();
        driver.findElement(By.xpath(REJECT_ALL_COOKIES_BUTTON)).click();
        driver.findElement(By.xpath(CHAT_GPT_CLOSE_BUTTON)).click();
    }

    public void urlControl() {
        String expectedUrl = "https://www.setur.com.tr/";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrl, actualUrl);
    }

    public void isdefaultTabControl() {
        WebElement aaa = driver.findElement(By.xpath("//span[@class='sc-363be8ce-0 gvnHSD']"));
        Assert.assertTrue(aaa.isDisplayed());
        //Assert.assertTrue(HOTEL_TAB.);
    }

    public String readCsvFile(String cityToSearch) {

        String keyword;
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(SETUR_CSV_PATH));
            String[] cell;

            while ((cell = reader.readNext()) != null) {

                driver.findElement(By.xpath(DESTINATION_BOX)).click();
                keyword = cell[0];
                driver.findElement(By.xpath(DESTINATION_BOX)).sendKeys(keyword);
            }

        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }

        WebElement chosenCity = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[3]/div[3]/div[1]/div[2]/div/div/div/div[2]/div[1]/div/div[2]/div/div/div/div[3]"));
        chosenCity.click();
        return cityToSearch;
    }

    public void chooseDate(String mounth, int dayOfMounth) throws InterruptedException {

        driver.findElement(By.xpath(VACATION_DATE_BOX)).click();
        WebElement tableElement = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[3]/div[3]/div[1]/div[2]/div/div/div/div[2]/div[2]/div/div[2]/div/div/div/div/div/div[2]/div[1]/button[2]"));
        int monthValue = LocalDate.now().getMonthValue();

        for (int i = 0; i < 6 - monthValue; i++) {
            Thread.sleep(1000);
            tableElement.click();
        }

        Map<Integer, SeturCalendar> calendarMap = new HashedMap();
        for (int row = 0; row < 5; row++) {
            List<WebElement> calendarRowElementList = driver.findElements(By.xpath("//*[@id=\"__next\"]/div[3]/div[3]/div[1]/div[2]/div/div/div/div[2]/div[2]/div/div[2]/div/div/div/div/div/div[2]/div[2]/div/div[2]/div/table/tbody/tr")).get(row).findElements(By.tagName("td"));
            for (int column = 0; column < 7; column++) {
                System.out.println(row + 1 + ". satır -> " + column + ". index'in" + "Deger: " + calendarRowElementList.get(column).getText());

                if (StringUtil.isNotBlank(calendarRowElementList.get(column).getText())) {
                    SeturCalendar seturCalendar = new SeturCalendar();
                    seturCalendar.setColumn(column);
                    seturCalendar.setRow(row);

                    String dayofMounth = calendarRowElementList.get(column).getText();
                    calendarMap.put(Integer.parseInt(dayofMounth), seturCalendar);
                    Thread.sleep(1000);
                }
            }
        }
        SeturCalendar setCalendar = calendarMap.get(15);
        driver.findElements(By.xpath("//*[@id=\"__next\"]/div[3]/div[3]/div[1]/div[2]/div/div/div/div[2]/div[2]/div/div[2]/div/div/div/div/div/div[2]/div[2]/div/div[2]/div/table/tbody/tr")).get(setCalendar.getRow()).findElements(By.tagName("td")).get(setCalendar.getColumn()).click();
    }

}