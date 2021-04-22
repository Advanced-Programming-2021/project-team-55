package model;

import model.board.GameBoard;
import model.cards.Deck;

public class Player {
    private User user;
    private Deck playDeck;
    private int LP=8000;//todo is it correct?
    private GameBoard playerGameBoard;
    private boolean isAI;

    public Player(User user, Deck playDeck) {
        this.user = user;
        this.playDeck = playDeck;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDeckToPlay(Deck deckToPlay) {
        this.playDeck = deckToPlay;
    }

    public void setLP(int LP) {
        this.LP = LP;
    }

    public int getLP() {
        return LP;
    }

    public GameBoard getGameBoard() {//better to rename to getPlayerGameBoard
        return playerGameBoard;
    }
}
