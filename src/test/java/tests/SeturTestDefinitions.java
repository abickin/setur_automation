package tests;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.log4j.Logger;
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
import static utils.CommonUtils.clickWithBackgroundColor;
import static utils.CommonUtils.waitAsSecond;

public class SeturTestDefinitions {
    final static Logger logger = Logger.getLogger(SeturTestDefinitions.class);
    private static String EXPECTED_WORD_IN_URL = "antalya";
    private static String EXPECTED_CLASS_VALUE = "jmbIRo";

    private WebDriver driver;

    public void initialSetting(String browserName) {
        logger.info("Initial Setting Test Begun");

        switch (browserName) {
            case "Chrome":
                driver = new ChromeDriver();
                logger.info("Chrome driver started");
                break;
            case "Firefox":
                driver = new FirefoxDriver();
                logger.info("Firefox driver started");
                break;
            default:
                driver = new EdgeDriver();
                logger.info("Edge driver started");
                break;
        }
    }

    public void setOptions() {
        logger.info("Set Option Test Begun");

        driver.manage().window().maximize();
        logger.info("Window maximized by Selenium");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        logger.info("Driver implicitly wait for 15 seconds");
    }

    public void openPage(String url) {
        logger.info("Page Open Test Begun");

        driver.navigate().to(url);
        logger.info(url + " opened by selenium");

    }

    public void closePopupsAndCookies() {
        logger.info("Close Popups And Cookies Test Begun");

        try {
            driver.findElement(By.xpath(CLOSE_POPUP_BUTTON_XPATH)).click();
        } catch (Exception e) {
            logger.error("Pop-up Could Not Closed");
        }
        try {
            driver.findElement(By.xpath(REJECT_ALL_COOKIES_BUTTON_XPATH)).click();
        } catch (Exception e) {
            logger.error("Cookies Could Not Rejected");
        }
        try {
            driver.findElement(By.xpath(CHAT_GPT_CLOSE_BUTTON_XPATH)).click();
        } catch (Exception e) {
            logger.error("ChatGPT Could Not Closed");
        }
    }

    public void urlControl() {
        logger.info("Url Control Test Begun");
        String expectedUrl = "https://www.setur.com.tr/";
        String actualUrl = driver.getCurrentUrl();

        if (expectedUrl.equals(actualUrl)  ) {
            logger.info("Url Control Test Passed");
        } else {
            logger.error("Expected Url and Actual Url are Not Equal: Test Failed");
        }
        Assert.assertEquals(expectedUrl, actualUrl);
    }

    public void defaultTabControl() {
        logger.info("Default Tab Control Begun");
        WebElement seturActiveTab = driver.findElement(By.xpath(SETUR_SERVICES_TABS_XPATH));
        String classValue = seturActiveTab.getAttribute("class");

        if (!classValue.contains(EXPECTED_CLASS_VALUE)) {
            logger.error("Hotel Tab is not Default");
        }
    }

    public void readCsvFile() throws InterruptedException {
        logger.info("Read CSV File Test Begun");
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(SETUR_CSV_PATH));
            String[] cell;
            cell = reader.readNext();
            List<String> cityList = Arrays.asList(cell[0].split(";"));

            driver.findElement(By.xpath(DESTINATION_BOX_XPATH)).click();
            driver.findElement(By.xpath(DESTINATION_BOX_XPATH)).sendKeys(cityList.get(0));

            WebElement chosenCity = driver.findElement(By.xpath(CHOSEN_CITY_XPATH));
            chosenCity.click();

        } catch (IOException | CsvException e) {
            logger.error("File Could not Read");
        }
    }

    public void clickVacationBox() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath(VACATION_DATE_BOX_XPATH));
        clickWithBackgroundColor(element, driver);
    }

    public Map<Integer, SeturCalendar> setCalenderMap() throws InterruptedException {
        logger.info("Create Calendar Map Test Begun");
        Map<Integer, SeturCalendar> calendarMap = new HashMap<>();
        int chosenMonthRowCount = driver.findElements(By.xpath(CHOSEN_MONTH_ROW_XPATH)).size();
        waitAsSecond(10);

        try {
            for (int row = 0; row < chosenMonthRowCount; row++) {
                List<WebElement> calendarRowElementList = driver.findElements(By.xpath(CALENDAR_ROW_XPATH)).get(row).findElements(By.tagName("td"));
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
        } catch (NumberFormatException e) {
            logger.error("Calendar Map Could not Created");
        }
        return calendarMap;
    }

    public void chooseMonth(Months month) throws InterruptedException {
        logger.info("Choose Month Test Begun");

        if (month == null) {
            System.out.println("AY DEGERI BOS OLAMAZ");
        }
        int monthValueAsInt = Months.getMonthOfYear(month.getNameOfMonth());

        WebElement tableElement = driver.findElement(By.xpath(MONTH_INCREASE_BUTTON_XPATH));
        int currentMonthValue = LocalDate.now().getMonthValue();
        int clickCount = monthValueAsInt - currentMonthValue;

        for (int i = 0; i < clickCount; i++) {
            waitAsSecond(1);
            clickWithBackgroundColor(tableElement, driver);
        }
    }

    public void selectVacationDate(Map<Integer, SeturCalendar> calendarMap, int monthOfDay) {
        logger.info("Select Vacation Date Test Began ");
        SeturCalendar seturCalendar = calendarMap.get(monthOfDay);

        try {
            WebElement element = driver.findElements(By.xpath(CHOSEN_MONTH_ROW_XPATH)).get(seturCalendar.getRow()).findElements(By.tagName("td")).get(seturCalendar.getColumn());
            element.click();
        } catch (Exception e) {
            logger.error("Vacation Date Could not Selected");
        }
    }

    public void increaseAdultGuest() throws InterruptedException {
        logger.info("Increase Adult Guest Test Began");

        WebElement personAndRoomElement = driver.findElement(By.xpath(PERSON_AND_ROOM_XPATH));
        clickWithBackgroundColor(personAndRoomElement, driver);

        String defaultAdultPersonCountString = driver.findElement(By.xpath(ADLUT_PERSON_COUNT_XPATH)).getText();
        int defaultAdultPersonCount = Integer.parseInt(defaultAdultPersonCountString);

        WebElement adultIncreaseElement = driver.findElement(By.xpath(ADULT_INCREASE_BUTTON_XPATH));
        clickWithBackgroundColor(adultIncreaseElement, driver);

        String increasedAdultPersonCountString = driver.findElement(By.xpath(ADLUT_PERSON_COUNT_XPATH)).getText();
        int increasedAdultPersonCount = Integer.parseInt(increasedAdultPersonCountString);

        Assert.assertTrue(increasedAdultPersonCount > defaultAdultPersonCount);
        clickWithBackgroundColor(personAndRoomElement, driver);
    }

    public void clickSearch() throws InterruptedException {
        logger.info("Click Search Test Began");
        WebElement searchButtonElement = driver.findElement(By.xpath(SEARCH_BUTTON_XPATH));
        if (searchButtonElement.isDisplayed()) {
            clickWithBackgroundColor(searchButtonElement, driver);
        }
    }

    public void isExistGivenWordInURL() throws InterruptedException {
        logger.info("Given Word In URL Test Began");

        waitAsSecond(1);
        String actualUrl = driver.getCurrentUrl();
        waitAsSecond(2);
        if (!actualUrl.contains(EXPECTED_WORD_IN_URL)) {
            logger.error("URL Does not Contain the Given Word");
        }
    }

    public int clickOtherRegionsRandomly() throws InterruptedException {
        logger.info("Randomly Select Other Region Test Began");

        Random random = new Random();
        List<WebElement> otherRegionList = driver.findElements(By.xpath(OTHER_REGION_XPATH));
        int randomChosenRegion = random.nextInt(otherRegionList.size());
        WebElement regionSelector = otherRegionList.get(randomChosenRegion);
        String selectedOtherRegionHotelNumberStr = regionSelector.findElement(By.tagName("span")).getText().replaceAll("\\D", "");
        int selectedOtherRegionHotelNumber = Integer.parseInt(selectedOtherRegionHotelNumberStr);
        regionSelector.click();
        waitAsSecond(2);
        WebElement otherRegionElement = driver.findElement(By.xpath(OTHER_REGION_COLOR_XPATH));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background-color:yellow')", otherRegionElement);

        return selectedOtherRegionHotelNumber;
    }

    public boolean isExistPagingElement(int filterCount) {
        logger.info("ExistPagingElement Test Began");
        return filterCount >= 20;
    }

    public void scrollPage() {
        logger.info("Page scrolling Test Began");
        WebElement element = driver.findElement(By.xpath(PAGE_FOOTER_XPATH));
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(element, 0, 0);
        new Actions(driver).scrollFromOrigin(scrollOrigin, 0, 0).perform();
    }

    public int getResultCount() {
        logger.info("Get Result Count Test Began");
        WebElement element = driver.findElement(By.xpath(PAGE_FOOTER_XPATH));
        String count = element.findElement(By.xpath("//*[@id='__next']/div[3]/div[2]/div/div[1]/div[2]/div[7]/div[1]/span[2]")).getText();
        return Integer.parseInt(count);
    }

    public void compareFilterCountAndResultCount(int resultCount, int filterCount) {
        logger.info("Compare Test: Passed");
        Assert.assertEquals(resultCount, filterCount);
    }

    public void closeDriver() {
        driver.quit();
    }
}

