package model.cards.trapandspells;

import controller.gamephasescontrollers.GameController;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import view.ViewInterface;
import view.gamephases.Duel;
import view.gamephases.GameResponses;

public class Mysticalspacetyphoon extends SpellAndTrap {

    public Mysticalspacetyphoon() {
        super("Mystical space typhoon", "Target 1 Spell/Trap on the field; destroy that target.",
                3500, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.QUICK_PLAY, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        GameBoard playerGameBoard = gameController.getCurrentTurnPlayer().getGameBoard();
        GameBoard opponentGameBoard = gameController.getCurrentTurnOpponentPlayer().getGameBoard();
        if (playerGameBoard.isSpellAndTrapCardZoneEmpty() && opponentGameBoard.isSpellAndTrapCardZoneEmpty()) {
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
        }
        Cell selectedCell = Cell.getSelectedCell();
        ViewInterface.showResult("Mystical space typhoon activated : select a spell or trap on the field to destroy:");
        String input = ViewInterface.getInput();
        while (true) {
            String response = Duel.getMainPhase1().processSelect(input);
            if (input.equals("cancel")) {
                ViewInterface.showResult("you cancelled the activation of Mystical space typhoon!");
                return;
            }
            if (input.matches("^select --spell (\\d+)$")) {
                if (response.equals(GameResponses.CARD_SELECTED.response)) {
                    ViewInterface.showResult("your card "+Cell.getSelectedCell().getCellCard().getName()+" destroyed.");
                    Cell.getSelectedCell().removeCardFromCell(playerGameBoard);
                    break;
                }
            } else if (input.matches("^select --opponent --spell (\\d+)$")) {
                if (response.equals(GameResponses.CARD_SELECTED.response)) {
                    ViewInterface.showResult("your opponent card "+Cell.getSelectedCell().getCellCard().getName()+" destroyed.");
                    Cell.getSelectedCell().removeCardFromCell(playerGameBoard);
                    break;
                }
            }
            ViewInterface.showResult("Error: try again!");
            input = ViewInterface.getInput();
        }
        Cell.setSelectedCell(selectedCell);
        updateSpellInGameBoard(gameController);
    }
}