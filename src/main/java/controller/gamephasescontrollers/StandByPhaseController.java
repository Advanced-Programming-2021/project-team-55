package controller.gamephasescontrollers;

import model.cards.Card;

public class StandByPhaseController {

    private static StandByPhaseController standByPhaseController;


    private StandByPhaseController() {

    }

    public static StandByPhaseController getInstance() {
        if(standByPhaseController==null){
            return new StandByPhaseController();
        }
        return standByPhaseController;
    }

    public void handleEffectsOfCards(Card card) {

    }

    public void activateFastCards(Card card) {

    }

}
