package controller.gamephasescontrollers;

import model.cards.Card;

public class StandByPhaseController implements methods {
    private final GameController gameController;

    public StandByPhaseController(GameController gameController) {
        this.gameController = gameController;
    }

    public void activateEffects() {
        //TODO some cards effects should be handled here!
    }

    private void handleEffectsOfCards(Card card) {

    }

    private void activateFastCards(Card card) {

    }

}
