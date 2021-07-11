package yugioh.client.controller.gamephasescontrollers;

import yugioh.client.model.cards.monsters.Scanner;

public class StandByPhaseController {

    private final GameController gameController;

    public StandByPhaseController(GameController gameController) {
        this.gameController = gameController;
    }

    public void activateEffects() {
        try {
            Scanner.handleEffect(gameController);
        }catch (Exception ignored){
        }
    }

}
