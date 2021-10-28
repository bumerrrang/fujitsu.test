package andrei.lunin.fujitsu.test.constants;

public enum Constants {
    PREMIUM_PRICE("New release", 4, 2),
    BASIC_PRICE("Regular film", 3, 1),
    OLD_FILM("Old film", 3, 1);

    public final String videoType;
    public final int price;
    public final int bonus;

    Constants(String videoType, int price, int bonus) {
        this.videoType = videoType;
        this.price = price;
        this.bonus = bonus;
    }

    public static final int oneDayCost = 25;
}
