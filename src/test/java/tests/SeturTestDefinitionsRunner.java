package tests;

import org.junit.Test;

import java.util.Map;

public class SeturTestDefinitionsRunner {
    private static String URL_SETUR = "https://www.setur.com.tr/";
    SeturTestDefinitions seturTestDefinitions = new SeturTestDefinitions();

    @Test
    public void SeturReservationTest() throws InterruptedException {
        seturTestDefinitions.initialSetting("Chrome");
        seturTestDefinitions.setOptions();
        seturTestDefinitions.openPage(URL_SETUR);
        seturTestDefinitions.closePopupsAndCookies();
        seturTestDefinitions.urlControl();
        //seturTestDefinitions.defaultTabControl();
        seturTestDefinitions.readCsvFile();
        seturTestDefinitions.clickVacationBox();
        seturTestDefinitions.chooseMonth(Months.NISAN);
        Map<Integer, SeturCalendar> calendarMap = seturTestDefinitions.setCalenderMap();
        seturTestDefinitions.selectVacationDate(calendarMap, 1);
        seturTestDefinitions.selectVacationDate(calendarMap, 7);
        seturTestDefinitions.increaseAdultGuest();
        seturTestDefinitions.clickSearch();
        seturTestDefinitions.isExistGivenWordInURL();
        int filterCount = seturTestDefinitions.clickOtherRegionsRandomly();
        boolean isExist = seturTestDefinitions.isExistPagingElement(filterCount);
        if (isExist) {
            seturTestDefinitions.scrollPage();
            int resultCount = seturTestDefinitions.getResultCount();
            seturTestDefinitions.compareFilterCountAndResultCount(resultCount, filterCount);
        }
    }
}
