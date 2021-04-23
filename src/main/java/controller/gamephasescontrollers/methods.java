package controller.gamephasescontrollers;

import exceptions.GameException;

public interface methods {

    default void selectCard(String zone ,int number)throws GameException {

    }
    default void deselect()throws GameException{

    }
}
