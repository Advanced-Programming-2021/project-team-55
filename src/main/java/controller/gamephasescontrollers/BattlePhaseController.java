package controller.gamephasescontrollers;

import model.board.Game;
import model.cards.Card;
import view.gamephases.BattlePhase;

public class BattlePhaseController implements methods {
    private GameController gameController;
    public BattlePhaseController(GameController gameController){
        this.gameController=gameController;
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
