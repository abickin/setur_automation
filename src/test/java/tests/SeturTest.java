package tests;

public interface SeturTest {
    void initialSetting(String browserName);

    void setOptions();

    void openPage(String url);

    void closePopupsAndCookies();

    void urlControl();

    void IsdefaultTabControl();

    String readCsvFile(String cityToSearch);

    void chooseDate(String mounth,int dayOfMounth) throws InterruptedException;

}
