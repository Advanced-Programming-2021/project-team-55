package model;

import java.util.Random;

public class CoinDice {

    public static int rollDice() {
        return (int)Math.random() % 6 + 1;
    }

    public static int tossCoin() {
        return (int)Math.random() % 2 + 1;
    }

}
