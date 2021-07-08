package yugioh.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import yugioh.controller.menucontroller.GameMenuController;
import yugioh.model.board.GameBoard;
import yugioh.model.cards.Deck;
import yugioh.view.gamephases.Duel;

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
        if (LP < 0) LP = 0;
        updateLP();
        if (Duel.getGameController().isGameEnded()) Duel.getGameController().endGameRound();
    }

    private void updateLP() {
        if (this == Duel.getGameController().currentTurnPlayer) {
            GameMenuController.getGameMenuController().userLP.setText(getLP() + "");
            GameMenuController.getGameMenuController().userLPBar.setProgress((double) getLP() / 8000);
        } else {
            GameMenuController.getGameMenuController().rivalLP.setText(getLP() + "");
            GameMenuController.getGameMenuController().rivalLPBar.setProgress((double) getLP() / 8000);
        }
    }

    public void increaseLP(int amount) {
        setLP(this.getLP() + amount);
        updateLP();
    }

    public GameBoard getGameBoard() {//better to rename to getPlayerGameBoard
        return playerGameBoard;
    }

    public Deck getPlayDeck() {
        return playDeck;
    }

    public void setPlayDeck(Deck playDeck) {
        this.playDeck = playDeck;
    }

    public void resetGameBoard() {
        try {
            playerGameBoard = new GameBoard(playDeck);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setLP(8000);
    }

    public boolean isAI() {
        return isAI;
    }

    public void setAI(boolean AI) {
        isAI = AI;
    }

}
