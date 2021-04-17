package controller;

import model.Card;

public class BattlePhaseController {

    private static BattlePhaseController battlePhaseController;


    private BattlePhaseController() {

    }

    public static BattlePhaseController getInstance() {
        return null;
    }

    public void startBattlePhase() {

    }

    public String attack(Card attackerCard, Card attackedCard) {
        return null;
    }

    public String calculateDamage(Card attackerCard, Card attackedCard) {
        return null;
    }

    public boolean canCardAttack(Card card) {
        return false;
    }


}
