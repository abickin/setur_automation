package tests;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.poi.util.StringUtil;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

import static tests.Locators.*;
import static utils.ReusubleMethods.*;

public class SeturTestDefinitions {
    private static String EXPECTED_WORD_IN_URL = "antalya";
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

    /*public void defaultTabControl() {
        List<WebElement> seturServicesTabList = driver.findElements(By.xpath(SETUR_SERVICES_TABS)).get(0)
        List<String> seturServicesTabValueList = new ArrayList<>();

        String expectedClassNameAttribute = "jmbIRo";
        Assert.assertTrue(expectedClassNameAttribute.contains(seturServicesTabList.get(0).getText()));

    }*/
    public void readCsvFile() throws InterruptedException {

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(SETUR_CSV_PATH));
            String[] cell;
            cell = reader.readNext();
            List<String> cityList = Arrays.asList(cell[0].split(";"));

            driver.findElement(By.xpath(DESTINATION_BOX)).click();
            driver.findElement(By.xpath(DESTINATION_BOX)).sendKeys(cityList.get(0));

            WebElement chosenCity = driver.findElement(By.xpath(CHOSEN_CITY));
            chosenCity.click();
            //clickWithBackgroundColor(chosenCity,driver);

        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickVacationBox() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath(VACATION_DATE_BOX));
        clickWithBackgroundColor(element, driver);
    }

    public Map<Integer, SeturCalendar> setCalenderMap() throws InterruptedException {
        Map<Integer, SeturCalendar> calendarMap = new HashMap<>();
        int chosenMonthRowCount = driver.findElements(By.xpath(CHOSEN_MONTH_ROW)).size();
        waitAsSecond(10);
        for (int row = 0; row < chosenMonthRowCount; row++) {
            List<WebElement> calendarRowElementList = driver.findElements(By.xpath(CALENDAR_ROW)).get(row).findElements(By.tagName("td"));
            for (int column = 0; column < 7; column++) {
                //System.out.println(row + 1 + ". satır -> " + column + ". index'in" + "Deger: " + calendarRowElementList.get(column).getText());

                if (StringUtil.isNotBlank(calendarRowElementList.get(column).getText())) {
                    SeturCalendar seturCalendar = new SeturCalendar();
                    seturCalendar.setColumn(column);
                    seturCalendar.setRow(row);
                    String dayOfMonth = calendarRowElementList.get(column).getText();
                    calendarMap.put(Integer.parseInt(dayOfMonth), seturCalendar);
                }
            }
        }
        return calendarMap;
    }

    public void chooseMonth(Months month) throws InterruptedException {
        if (month == null) {
            System.out.println("AY DEĞERİ BOŞ OLAMAZ");
        }
        int monthValueAsInt = Months.getMonthOfYear(month.getNameOfMonth());

        WebElement tableElement = driver.findElement(By.xpath(MONTH_INCREASE_BUTTON));
        int currentMonthValue = LocalDate.now().getMonthValue();
        int clickCount = monthValueAsInt - currentMonthValue;

        for (int i = 0; i < clickCount; i++) {
            Thread.sleep(1000);
            clickWithBackgroundColor(tableElement, driver);
        }
    }

    public void selectVacationDate(Map<Integer, SeturCalendar> calendarMap, int monthOfDay) {
        SeturCalendar seturCalendar = calendarMap.get(monthOfDay);
        WebElement element = driver.findElements(By.xpath(CHOSEN_MONTH_ROW)).get(seturCalendar.getRow()).findElements(By.tagName("td")).get(seturCalendar.getColumn());
        element.click();
    }

    public void increaseAdultGuest() throws InterruptedException {
        WebElement personAndRoomElement = driver.findElement(By.xpath(PERSON_AND_ROOM));
        clickWithBackgroundColor(personAndRoomElement, driver);

        String defaultAdultPersonCountString = driver.findElement(By.xpath(ADLUT_PERSON_COUNT)).getText();
        int defaultAdultPersonCount = Integer.parseInt(defaultAdultPersonCountString);

        WebElement adultIncreaseElement = driver.findElement(By.xpath(ADULT_INCREASE_BUTTON));
        clickWithBackgroundColor(adultIncreaseElement, driver);

        String increasedAdultPersonCountString = driver.findElement(By.xpath(ADLUT_PERSON_COUNT)).getText();
        int increasedAdultPersonCount = Integer.parseInt(increasedAdultPersonCountString);

        Assert.assertTrue(increasedAdultPersonCount > defaultAdultPersonCount);
        clickWithBackgroundColor(personAndRoomElement, driver);
    }

    public void clickSearch() throws InterruptedException {
        WebElement searchButtonElement = driver.findElement(By.xpath(SEARCH_BUTTON));
        if (searchButtonElement.isDisplayed()) {
            clickWithBackgroundColor(searchButtonElement, driver);
        }
    }

    public void isExistGivenWordInURL() throws InterruptedException {
        waitAsSecond(1);
        String actualUrl = driver.getCurrentUrl();
        waitAsSecond(2);
        Assert.assertTrue(actualUrl.contains(EXPECTED_WORD_IN_URL));
    }

    public int clickOtherRegionsRandomly() {
        Random random = new Random();
        List<WebElement> otherRegionList = driver.findElements(By.xpath(OTHER_REGION));
        int randomChosenRegion = random.nextInt(otherRegionList.size());
        WebElement regionSelector = otherRegionList.get(randomChosenRegion);
        String selectedOtherRegionHotelNumberStr = regionSelector.findElement(By.tagName("span")).getText().replaceAll("\\D", "");
        int selectedOtherRegionHotelNumber = Integer.parseInt(selectedOtherRegionHotelNumberStr);
        regionSelector.click();
        WebElement otherRegionElement = driver.findElement(By.xpath(OTHER_REGION_COLOR));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background-color:yellow')", otherRegionElement);

        return selectedOtherRegionHotelNumber;
    }

    public boolean isExistPagingElement(int filterCount) {
        return filterCount >= 20;
    }

    public void scrollPage() {
        WebElement element = driver.findElement(By.xpath(PAGE_FOOTER_XPATH));
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(element, 0, 0);
        new Actions(driver).scrollFromOrigin(scrollOrigin, 0, 0).perform();
    }

    public int getResultCount() {
        WebElement element = driver.findElement(By.xpath(PAGE_FOOTER_XPATH));
        String count = element.findElement(By.xpath("//*[@id='__next']/div[3]/div[2]/div/div[1]/div[2]/div[7]/div[1]/span[2]")).getText();
        return Integer.parseInt(count);
    }

    public void compareFilterCountAndResultCount(int resultCount, int filterCount) {
        Assert.assertEquals(resultCount, filterCount);
    }
}

