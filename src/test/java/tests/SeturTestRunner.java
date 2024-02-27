package tests;

import org.junit.Test;
import org.openqa.selenium.By;

import static tests.Locators.VACATION_DATE_BOX;

public class SeturTestRunner {
    SeturTest seturTest = new SeturTest();

    @Test
    public void Test01() throws InterruptedException {
        seturTest.initialSetting("Chrome");
        seturTest.setOptions();
        seturTest.openPage("https://www.setur.com.tr/");
        seturTest.closePopupsAndCookies();
        seturTest.urlControl();
        seturTest.isdefaultTabControl();
        seturTest.readCsvFile("Antalya");
        seturTest.clickVacationBox();
        seturTest.chooseDate(Mounths.EYLUL, 17);
    }


}
