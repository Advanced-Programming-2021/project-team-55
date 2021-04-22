package model;

import org.junit.jupiter.api.*;

import static org.junit.Assert.assertEquals;

class CoinDiceTest {

    @RepeatedTest(100)
    @DisplayName("check is in bounds")
    void testIsInBoundsRollDice() {
        int random = CoinDice.rollDice();
        Assertions.assertTrue(random > 0 && random <= 6);
    }

    @RepeatedTest(100)
    @DisplayName("check is in bounds")
    void testIsInBoundsTossCoin() {
        int random = CoinDice.tossCoin();
        System.out.println(random);
        Assertions.assertTrue(random >= 0 && random <= 1);
    }

}