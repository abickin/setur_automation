package tests;

public enum Months {

    OCAK("Ocak", 1, 30),
    SUBAT("Şubat", 2, 28),
    MART("Mart", 3, 31),
    NISAN("Nisan", 4, 30),
    MAYIS("Mayıs", 5, 31),
    HAZIRAN("Haziran", 6, 30),
    TEMMUZ("Temmuz", 7, 31),
    AGUSTOS("Ağustos", 8, 31),
    EYLUL("Eylül", 9, 30),
    EKIM("Ekim", 10, 31),
    KASIM("Kasım", 11, 30),
    ARALIK("Aralık", 12, 31);


    private int maxDayOfMonth;
    private String nameOfMonth;
    private int mothOfYear;


    Months(String nameOfMonth, int mouthOfYear, int maxDayOfMonth) {
        this.nameOfMonth = nameOfMonth;
        this.mothOfYear = mouthOfYear;
        this.maxDayOfMonth = maxDayOfMonth;
    }

    public static int getMonthOfYear(String nameOfMonth) {
        int fondDayOfMounth = 0;
        for (Months mounth : Months.values()) {

            if (nameOfMonth.equalsIgnoreCase(mounth.nameOfMonth)) {
                fondDayOfMounth = mounth.mothOfYear;
            }
        }
        return fondDayOfMounth;
    }

    public String getNameOfMonth() {
        return nameOfMonth;
    }


    public int getMothOfYear() {
        return mothOfYear;
    }


    public int getMaxDayOfMonth() {
        return maxDayOfMonth;
    }

}
