package controller.gamephasescontrollers;

import exceptions.GameException;

public class MainPhase1Controller implements methods, MainPhasesController {
    private final GameController gameController;

    public MainPhase1Controller(GameController gameController) {
        this.gameController = gameController;
    }


    public void flipSummon() throws GameException {

    }

    public String attack(int number) throws GameException {
        //TODO : it should return a response based on the situation of cards-->page 31 project doc
        return "";

    }

    public void activateSpell() throws GameException {
    }

}
