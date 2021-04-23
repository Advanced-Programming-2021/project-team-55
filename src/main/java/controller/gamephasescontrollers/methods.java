package controller.gamephasescontrollers;

import exceptions.GameException;
import model.board.Cell;

public interface methods {

    default void selectCard(String zone ,int number,boolean opponent)throws GameException {
        switch (zone){
            case "Monster":{

            }
            case "Spell":{

            }
            case "Field":{

            }
            case "Hand":{

            }
        }

    }
    default void deselect()throws GameException{
        Cell.setSelectedCell(null);

    }
}
