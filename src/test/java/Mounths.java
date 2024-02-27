public enum Mounths {

    OCAK("Ocak", 1),
    SUBAT("Şubat", 2),
    MART("Mart", 3),
    NISAN("Nisan", 4);


    private String name;
    private int dayOfMounth;


    Mounths(String name, int dayOfMounth) {
        this.name = name;
        this.dayOfMounth = dayOfMounth;
    }
}
