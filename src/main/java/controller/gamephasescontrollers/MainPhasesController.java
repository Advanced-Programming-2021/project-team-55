package controller.gamephasescontrollers;

import model.Player;
import model.board.Cell;
import model.board.GameBoard;

public interface MainPhasesController {

    default void monsterInsert(Cell cell) {

    }

    default void monsterSet(Cell cell) {

    }

    default void monsterSummon(Cell cell) {

    }

    default void specialSummon(Cell cell) {

    }

    default void ritualSummon(Cell cell) {

    }

    default void changeMonsterMode(Cell cell) {

    }

    default boolean isSummonable(Cell cell) {
        return false;
    }

    default boolean isRitualSummonable(Cell cell) {
        return false;
    }

    default boolean isSpecialSummonable(Cell cell) {
        return false;
    }

    default boolean isSettable(Cell cell) {
        return false;
    }

    default void nonMonsterInsert(Cell cell) {

    }

    default boolean isFieldZoneFull() {
        return false;
    }

    default boolean hasEnoughTribute(int cardLevel) {
        return false;
    }

    default boolean isInFieldZone(Cell cell) {
        return false;
    }

    default boolean isTributesAddressesValid(int[] addresses) {
        return false;
    }

    default String showGameBoard(Player currentPlayer,Player opponentPlayer){
        String response=opponentPlayer.getUser().getNickname()+":"+opponentPlayer.getLP();
        GameBoard playerGameBoard=currentPlayer.getGameBoard();
        GameBoard opponentPlayerGameBoard=opponentPlayer.getGameBoard();
        for (int i = 0; i < opponentPlayerGameBoard.getHandCards().size(); i++) {
            response+="\tc";
        }
        response+="\n"+opponentPlayerGameBoard.getDeckZone().size()+"\n";
        for (int i = 0; i < 5; i++) {
            if(opponentPlayerGameBoard.getSpellAndTrapCardZone()[opponentPlayerGameBoard.getAreasNumber()[4-i]]
                    .getCellCard()==null){
                response+="\tE";
            }
            switch (opponentPlayerGameBoard.getSpellAndTrapCardZone()[opponentPlayerGameBoard.getAreasNumber()[4-i]]
                    .getCardPosition()){
                case HIDDEN:{
                    response+="\tH";
                    break;
                }
                case OCCUPIED:{
                    response+="\tO";
                    break;
                }

            }
        }
        response+="\n";
        for (int i = 0; i < 5; i++) {
            if(opponentPlayerGameBoard.getMonsterCardZone()[opponentPlayerGameBoard.getAreasNumber()[4-i]]
                    .getCellCard()==null){
                response+="\tE";
            }
            switch (opponentPlayerGameBoard.getMonsterCardZone()[opponentPlayerGameBoard.getAreasNumber()[4-i]]
                    .getCardPosition()){
                case DEFENSIVE_HIDDEN:{
                    response+="\tDH";
                    break;
                }
                case DEFENSIVE_OCCUPIED:{
                    response+="\tDO";
                    break;
                }
                case OFFENSIVE_HIDDEN:{
                    response+="\tOH";
                    break;
                }
                case OFFENSIVE_OCCUPIED:{
                    response+="\tOO";
                }
            }
        }
        response+="\n"+opponentPlayerGameBoard.getGraveyard().size()+"\t\t\t\t\t\t";
        if(opponentPlayerGameBoard.getFieldZone()!=null){
            response+="E";
        }
        else{
            response+="O";
        }
        response+="\n\n--------------------------\n\n";

        //todo current player game board should be handled!




































        return response;
    }

}
