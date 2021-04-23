package model.board;

import model.cards.Deck;
import model.Player;

public class Game {
    Player firstPlayer;
    Player secondPlayer;
    int rounds;

    public Game(Player firstPlayer, Player secondPlayer, int rounds) {
        setPlayers(firstPlayer, secondPlayer);
        setRounds(rounds);
        //?
    }

    private void createGameBoards() {

    }

    private void setPlayers(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public int getRounds() {
        return rounds;
    }
}
