package controller.gamephasescontrollers;

import model.cards.monsters.Scanner;

public class StandByPhaseController {

    private final GameController gameController;

    public StandByPhaseController(GameController gameController) {
        this.gameController = gameController;
    }

    public void activateEffects() {
        Scanner.handleEffect(gameController);
    }

}
