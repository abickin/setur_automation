package tests;

import org.junit.Test;

public class SeturTestRunner {
    SeturTestImpl seturTest = new SeturTestImpl();

    @Test
    public void Test01() throws InterruptedException {

        VacationDate vacationDate = new VacationDate();

        HolidayDate holidayStartDate = new HolidayDate();
        holidayStartDate.setDay(15);
        holidayStartDate.setMounth(06);
        holidayStartDate.setYear(2024);
        vacationDate.setStart(holidayStartDate);

        HolidayDate holidayEndDate = new HolidayDate();
        holidayEndDate.setDay(22);
        holidayEndDate.setMounth(06);
        holidayEndDate.setYear(2024);
        vacationDate.setFinish(holidayEndDate);

        vacationDate.getFinish();
        vacationDate.getStart();


        seturTest.initialSetting("Chrome");
        seturTest.setOptions();
        seturTest.openPage("https://www.setur.com.tr/");
        seturTest.closePopupsAndCookies();
        seturTest.urlControl();
        seturTest.IsdefaultTabControl();
        seturTest.readCsvFile("Antalya");
        seturTest.chooseDate("Haziran",15);





    }


}
