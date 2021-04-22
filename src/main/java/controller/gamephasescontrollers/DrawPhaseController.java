package controller.gamephasescontrollers;

import model.cards.Card;
import model.Player;

import java.util.ArrayList;

public class DrawPhaseController {

    private static DrawPhaseController drawPhaseController;

    public static DrawPhaseController getInstance() {

        if (drawPhaseController == null) {
            return new DrawPhaseController();
        }
        return drawPhaseController;
    }

    private void getFirstDeckCard(Player player) {

    }

    private void removeFirstDeckCardFromDeckToPlay(Player player) {

    }

    private void CardFrequencyChecker(ArrayList<Card> handCards) {

    }

}
