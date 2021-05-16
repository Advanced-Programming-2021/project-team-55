package model;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CoinDice {

    public static int rollDice() {
        return (ThreadLocalRandom.current().nextInt())%6+1;
    }
    public static int tossCoin() {
        return (ThreadLocalRandom.current().nextInt())%2+1;
    }

}
