package controller.gamephasescontrollers;

import model.Player;

public class EndPhaseController implements methods {

    private GameController gameController;

    public EndPhaseController(GameController gameController){
        this.gameController=gameController;
    }

    private String changePlayerTurn() {//todo check this
        Player temp = gameController.getCurrentTurnPlayer();
        gameController.setCurrentTurnPlayer(gameController.getCurrentTurnOpponentPlayer());
        gameController.setCurrentTurnOpponentPlayer(temp);

        gameController.setCurrentPhase(GamePhase.DRAW);

        return "its " + gameController.getCurrentTurnPlayer().getUser().getNickname() + "’s turn";
    }

}
