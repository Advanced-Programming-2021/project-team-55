package controller.gamephasescontrollers;

import exceptions.GameException;
import model.board.Cell;
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

    private void addCardToHandDeck(Player player, Card toBeAdded) {
        player.getGameBoard().getHandCards().add(new Cell(toBeAdded));
    }

    public String removeFirstDeckCardFromDeckToPlay(Player player)throws GameException {
        if (!checkCardFrequency(player.getGameBoard().getHandCards()))
            return "hand deck is full! no card added";//todo check, پیام ارور رو پیدا نکردم توی داک ها هرچی گشتم

        ArrayList<Card> playerDeck = player.getPlayDeck().getMainDeck();
        Card removedCard = playerDeck.get(0);
        playerDeck.remove(0);
        addCardToHandDeck(player, removedCard);
        return "new card added to the hand : " + removedCard.getName();
    }

    private boolean checkCardFrequency(ArrayList<Cell> handCards) {
        return handCards.size() < 7;
    }

}
