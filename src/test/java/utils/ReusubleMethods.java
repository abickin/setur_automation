package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReusubleMethods {

    public static void waitAsSecond(int second) throws InterruptedException {

        Thread.sleep(second * 1000);

    }

    public static void tumSayfaFotografCek(WebDriver driver, String screenshotIsmi) {   // herkes farkli driver kullanabilecegi icin driver parametre olarak verilmeli.yani kullanılacak driver gonderilmeli chrome,firefox vs

        /*
        screenshot dosyasi icin hep ayni isim kullanirsak
        tum fotograflar ayni isimle kaydedileceginden tekbir dosya olur, son cekilen fotograf eskilerin uzerine kaydolur.

        cektigimiz fotograflarin hepsinin kalmasi ve istenilen isimde olmasi icin dosya adini dinamik yapmali.
        1- methodun cagrildigi yerde foto ismi yazilsin
        2- methodda dosya ismine tarih etikeri ekleyelim.
        ornek, ilkUrun23100800829

        */

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        LocalDateTime localDateTime = LocalDateTime.now(); //localdatetime objesi olusturma
        DateTimeFormatter istenenFormat = DateTimeFormatter.ofPattern("yyMMddHHmm"); // olusturulan objeye atama yapmak icin formati hazirlama
        localDateTime.format(istenenFormat); // objeye istedigimiz formati atama

        // dosya adi dinamik yapilsin
        // target/screenShots/tumSayfaSS.png


        String dinamikDosyaAdi = "target/screenShots/" + screenshotIsmi + localDateTime.format(istenenFormat) + ".png";
        File tumSayfaSS = new File(dinamikDosyaAdi);
        File geciciDosya = takesScreenshot.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(geciciDosya, tumSayfaSS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static void webelementFotografCek(WebElement istenenWebelemet, String screenshotIsmi) {

        LocalDateTime localDateTime = LocalDateTime.now(); //localdatetime objesi olusturma
        DateTimeFormatter istenenFormat = DateTimeFormatter.ofPattern("yyMMddHHmm"); // olusturulan objeye atama yapmak icin formati hazirlama
        localDateTime.format(istenenFormat); // objeye istedigimiz formati atama

        String dinamikDosyaAdi = "target/screenShots/" + screenshotIsmi + localDateTime.format(istenenFormat) + ".png";
        File webElementSS = new File(dinamikDosyaAdi);

        File geciciDosya = istenenWebelemet.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(geciciDosya, webElementSS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clickWithBackgroundColor(WebElement element,WebDriver driver) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        element.click();
        js.executeScript("arguments[0].setAttribute('style', 'background-color:yellow')", element);
        //Thread.sleep(2000);



    }

}
