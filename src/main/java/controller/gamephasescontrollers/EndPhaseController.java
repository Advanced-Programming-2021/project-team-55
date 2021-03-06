package controller.gamephasescontrollers;

import model.cards.monsters.Scanner;
import model.cards.monsters.Texchanger;
import model.cards.trapandspells.SwordsofRevealingLight;

public class EndPhaseController {

    private static EndPhaseController endPhaseController;

    private final GameController gameController;

    private EndPhaseController(GameController gameController) {
        this.gameController = gameController;
    }

    public static EndPhaseController getInstance(GameController gameController) {
        if (endPhaseController == null) {
            endPhaseController = new EndPhaseController(gameController);
        }
        return endPhaseController;
    }

    public void handleCardsSideEffectsForThisPhase() {
        Scanner.deActivateEffect();
        SwordsofRevealingLight.updateCounter(gameController);
        Texchanger.makeArrayEmpty();
    }

}
