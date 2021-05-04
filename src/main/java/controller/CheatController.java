package controller;

import model.Player;
import model.User;
import model.cards.Card;

public class CheatController {
    private static CheatController cheatController;

    private CheatController(){

    }
    public void increaseLPAmount(int amount, Player player) {
        player.setLP(player.getLP() + amount);
    }

    public void setWinner() {//todo

    }
    public void increaseMoney(int amount,User user){
        user.changeMoney(amount);
    }
    public void selectHandForce(Card card) {//todo

    }
    public static CheatController getInstance(){
        if(cheatController==null){
            return new CheatController();
        }
        else{
            return cheatController;
        }
    }

}
