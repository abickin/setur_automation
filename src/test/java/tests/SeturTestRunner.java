package tests;

import org.junit.Test;

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
        seturTest.chooseDate(Mounths.EYLUL, 29);
    }


}
