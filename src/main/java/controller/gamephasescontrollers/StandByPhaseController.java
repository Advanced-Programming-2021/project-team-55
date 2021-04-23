package controller.gamephasescontrollers;

import model.board.Game;
import model.cards.Card;
import view.gamephases.StandByPhase;

public class StandByPhaseController implements methods{
    private GameController gameController;
    public StandByPhaseController(GameController gameController){
        this.gameController=gameController;
    }
    public void activateEffects(){
        //TODO some cards effects should be handled here!
    }

    private void handleEffectsOfCards(Card card) {

    }

    private void activateFastCards(Card card) {

    }

}
