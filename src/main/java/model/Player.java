package model;

import model.board.GameBoard;
import model.cards.Deck;

public class Player {

    private User user;
    private Deck playDeck;
    private int LP = 8000;
    private GameBoard playerGameBoard;
    private boolean isAI;

    public Player(User user, Deck playDeck, boolean isAI) {
        this.user = user;
        this.playDeck = playDeck;
        this.isAI = isAI;
        this.playerGameBoard = new GameBoard(playDeck);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLP() {
        return LP;
    }

    public void setLP(int LP) {
        this.LP = LP;
    }

    public void decreaseLP(int amount) {
        setLP(this.getLP() - amount);
    }

    public void increaseLP(int amount) {
        setLP(this.getLP() + amount);
    }

    public GameBoard getGameBoard() {
        return playerGameBoard;
    }

    public Deck getPlayDeck() {
        return playDeck;
    }

    public void setPlayDeck(Deck playDeck) {
        this.playDeck = playDeck;
    }

    public void resetGameBoard() {
        playerGameBoard = new GameBoard(playDeck);
        setLP(8000);
    }

    public boolean isAI() {
        return isAI;
    }

    public void setAI(boolean AI) {
        isAI = AI;
    }

}
