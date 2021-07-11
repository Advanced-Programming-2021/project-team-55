package yugioh.client.model.board;

import yugioh.client.model.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    Player firstPlayer;
    Player secondPlayer;
    ArrayList<Integer> firstPlayerLPs = new ArrayList<>();
    ArrayList<Integer> secondPlayerLPs = new ArrayList<>();
    int firstPlayerScore;
    int secondPlayerScore;
    ArrayList<Player> winners = new ArrayList<>();
    ArrayList<Player> losers = new ArrayList<>();
    int rounds;

    public Game(Player firstPlayer, Player secondPlayer, int rounds) {
        setPlayers(firstPlayer, secondPlayer);
        setRounds(rounds);
    }

    private void setPlayers(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
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

    private void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public void setPlayersLP(Player player, int LP) {
        if (player == firstPlayer) {
            firstPlayerLPs.add(LP);
        } else {
            secondPlayerLPs.add(LP);
        }
    }

    public void setPlayerScore(Player player, int score) {
        if (player == firstPlayer) {
            this.firstPlayerScore = score;
        } else {
            this.secondPlayerScore = score;
        }
    }

    public int getPlayersMaxLP(Player player) {
        if (player == firstPlayer) {
            return Collections.max(firstPlayerLPs);
        } else {
            return Collections.max(secondPlayerLPs);
        }
    }

    public ArrayList<Player> getWinners() {
        return winners;
    }

    public ArrayList<Player> getLosers() {
        return losers;
    }

    public void increasePlayerScore(Player player, int amount) {
        if (player == firstPlayer) {
            firstPlayerScore += amount;
        } else {
            secondPlayerScore += amount;
        }
    }

    public int getPlayerScore(Player player) {
        if (player == firstPlayer) {
            return firstPlayerScore;
        } else {
            return secondPlayerScore;
        }
    }

    public Player getWinner() {
        int countFirstPlayerWins = 0;
        int countSecondPlayerWins = 0;
        for (Player player : winners) {
            if (player == firstPlayer) {
                countFirstPlayerWins++;
            } else if (player == secondPlayer) {
                countSecondPlayerWins++;
            }
        }
        if (countFirstPlayerWins > countSecondPlayerWins) {
            return firstPlayer;
        } else {
            return secondPlayer;
        }
    }

    public void addWinner(Player player) {
        winners.add(player);
    }

    public void addLoser(Player player) {
        losers.add(player);
    }
}
