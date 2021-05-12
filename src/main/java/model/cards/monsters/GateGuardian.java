package model.cards.monsters;

import controller.gamephasescontrollers.GameController;
import model.board.CardStatus;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;
import model.exceptions.GameException;
import view.ViewInterface;
import view.gamephases.Duel;
import view.gamephases.GameResponses;

import java.util.ArrayList;

public class GateGuardian extends Monster {

    public GateGuardian() {
        super("Gate Guardian", "Cannot be Normal Summoned/Set. Must first be Special Summoned (from your hand) by Tributing 1 \"Sanga of the Thunder\", \"Kazejin\", and \"Suijin\"."
                , 20000, 3750, 3400, 11, MonsterAttribute.DARK, MonsterType.WARRIOR, CardType.EFFECTIVE);
    }

    public static boolean handleEffect(GameController gameController) throws GameException {
        GameBoard playerGameBoard = gameController.getCurrentTurnPlayer().getGameBoard();
        Cell selectedCell = Cell.getSelectedCell();
        if (!selectedCell.getCellCard().getName().equals("Gate Guardian")) return false;
//todo پنج تا مانستر برا یه هیولا D:
        if (!playerGameBoard.doesMonsterZoneHaveNMonsters(5) || !gameController.getMainPhase1Controller().canSpecialSummon(gameController)) {
            throw new GameException(GameResponses.CAN_NOT_SET_OR_SUMMON.response);
        }
        ArrayList<Cell>tributes=new ArrayList<>();
        ViewInterface.showResult("Gate Guardian effect activated:");
        ViewInterface.showResult("you have to select 3 cards to tribute");
        for (int i = 0; i < 3; i++) {
            while (true) {
                ViewInterface.showResult("select cell to tribute:");
                String input=ViewInterface.getInput();
                String result = Duel.getMainPhase1().processSelect(input);
                if (!result.equals("card selected")) {
                    ViewInterface.showResult("Error: try again!");
                    continue;
                }
                Cell newSelectedCell = Cell.getSelectedCell();
                if (!playerGameBoard.isCellInMonsterZone(newSelectedCell)) {
                    ViewInterface.showResult(GameResponses.NO_MONSTER_ON_CELL.response);
                    continue;
                }
                tributes.add(newSelectedCell);
                ViewInterface.showResult("cell taken");
                break;
            }
        }
        for (Cell tribute : tributes) {
            tribute.removeCardFromCell(playerGameBoard);
        }
        selectedCell.removeCardFromCell(playerGameBoard);
        try {
            playerGameBoard.addCardToMonsterCardZone(selectedCell.getCellCard(), CardStatus.OFFENSIVE_OCCUPIED,gameController);
            gameController.shouldSpecialSummonNow=true;
        }
        catch (GameException e){
            e.printStackTrace();
        }
        return true;
    }

}
