package model;

public class CoinDice {

    public static int rollDice() {
        return (int) Math.floor(Math.random() * (6) + 1);
    }

    public static int tossCoin() {
        return (int) Math.floor(Math.random() * (2) + 0);
    }

}
