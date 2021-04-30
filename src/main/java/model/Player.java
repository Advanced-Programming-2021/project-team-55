package model;

import model.board.GameBoard;
import model.cards.Deck;

public class Player {
    private User user;
    private Deck playDeck;
    private int LP = 8000;
    private GameBoard playerGameBoard;
    private boolean isAI;

    public Player(User user, Deck playDeck) {
        this.user = user;
        this.playDeck = playDeck;
        this.playerGameBoard=new GameBoard(playDeck);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setDeckToPlay(Deck deckToPlay) {
        this.playDeck = deckToPlay;
    }

    public int getLP() {
        return LP;
    }

    public void setLP(int LP) {
        this.LP = LP;
    }

    public GameBoard getGameBoard() {//better to rename to getPlayerGameBoard
        return playerGameBoard;
    }
}
