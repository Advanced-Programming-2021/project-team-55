package controller.gamephasescontrollers;

import exceptions.GameException;
import model.board.Game;
import model.cards.Card;
import model.Player;
import view.gamephases.DrawPhase;

import java.util.ArrayList;

public class DrawPhaseController implements methods {
    private GameController gameController;
    public DrawPhaseController(GameController gameController){
        this.gameController=gameController;
    }

    private void getFirstDeckCard(Player player) {

    }

    public String removeFirstDeckCardFromDeckToPlay(Player player)throws GameException {
        //TODO it should return new card added to the hand
        return "";
    }

    private void CardFrequencyChecker(ArrayList<Card> handCards) {

    }

}
