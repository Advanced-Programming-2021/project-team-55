package controller;

import model.Player;
import model.cards.Card;

public class CheatController {

    public void increaseLPAmount(int amount, Player player) {
        player.setLP(player.getLP() + amount);
    }

    public void setWinner() {//todo

    }

    public void increaseMoney(int amount, Player player) {
        player.getUser().changeMoney(amount);
    }

    public void selectHandForce(Card card) {//todo

    }

}
