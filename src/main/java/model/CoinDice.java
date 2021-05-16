package model;
import model.cards.trapandspells.MagicCylinder;

import java.util.concurrent.ThreadLocalRandom;

public class CoinDice {

    public static int rollDice() {
        return Math.abs(ThreadLocalRandom.current().nextInt())%6+1;
    }
    public static int tossCoin() {
        return Math.abs(ThreadLocalRandom.current().nextInt())%2+1;
    }

}
