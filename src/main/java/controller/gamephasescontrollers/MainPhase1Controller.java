package controller.gamephasescontrollers;

import exceptions.GameException;
import model.board.Game;

public class MainPhase1Controller implements methods, MainPhasesController {
    private GameController gameController;
    public MainPhase1Controller(GameController gameController){
        this.gameController=gameController;
    }
    public void setPosition(String position)throws GameException{

    }
    public void flipSummon()throws GameException{

    }
    public String attack(int number)throws GameException{
        //TODO : it should return a response based on the situation of cards-->page 31 project doc
        return "";

    }
    public String directAttack()throws GameException{
        //TODO : it should return : your opponent received damage
        return "";
    }
    public void activateSpell()throws GameException{
    }

}
