package view.gamephases;

import controller.gamephasescontrollers.DrawPhaseController;
import controller.gamephasescontrollers.GameController;
import exceptions.GameException;
import model.Player;
import model.User;
import model.board.Game;
import model.cards.Deck;
import view.ViewInterface;

public class DrawPhase extends Duel {
    private DrawPhaseController drawPhaseController;

    @Override
    protected void execute(GameController gameController) {

        drawPhaseController =  gameController.getDrawPhaseController();
        String response = "";
        try {
            response = drawPhaseController.removeFirstDeckCardFromDeckToPlay(gameController.getCurrentTurnPlayer());
            drawPhaseController.changePhase(gameController,gameController.getCurrentPhase());
            showPhase(gameController);
        } catch (GameException e) {
            //TODO end game
        }
    }

    @Override
    protected void processCommand(String command) {

    }
}
