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

import java.util.regex.Matcher;

public class TwinTwisters extends SpellAndTrap {

    public TwinTwisters() {
        super("Twin Twisters", "Discard 1 card, then target up to 2 Spells/Traps on the field; destroy them.",
                3500, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.QUICK_PLAY, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController){
        if (!canActivate(gameController)){
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        }
        else {
            ViewInterface.showResult("choose a monster from your hand to remove it");
            String input ;
            while (true) {
                input = ViewInterface.getInput();
                Duel.processSelect(input);
                if (gameController.getCurrentTurnOpponentPlayer().getGameBoard().isCellInSpellAndTrapZone(Cell.getSelectedCell())){
                    Cell.getSelectedCell().removeCardFromCell(gameController.getCurrentTurnPlayer().getGameBoard());
                    break;
                }

            }
        }
    }

    private static boolean canActivate(GameController gameController) {
        return !gameController.getCurrentTurnPlayer().getGameBoard().isHandCardEmpty();//todo which player?
    }

}