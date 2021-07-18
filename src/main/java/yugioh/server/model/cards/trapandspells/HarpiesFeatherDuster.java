package yugioh.server.model.cards.trapandspells;

import yugioh.client.view.menus.PopUpWindow;
import yugioh.client.view.menus.WelcomeMenu;
import yugioh.server.controller.gamephasescontrollers.GameController;
import yugioh.server.model.board.Cell;
import yugioh.server.model.board.GameBoard;
import yugioh.server.model.cards.SpellAndTrap;
import yugioh.server.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.server.view.ViewInterface;
import yugioh.server.view.gamephases.GameResponses;

public class HarpiesFeatherDuster extends SpellAndTrap {

    public HarpiesFeatherDuster() {
        super("Harpie's Feather Duster", "Destroy all Spells and Traps your opponent controls.",
                2500, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, EffectiveTerm.LIMITED);
    }

    public static void setActivated(GameController gameController) {
        GameBoard opponentPlayerGameBoard = gameController.getCurrentTurnOpponentPlayer().getGameBoard();
        if (opponentPlayerGameBoard.isSpellAndTrapCardZoneEmpty()) {
            try {
                new PopUpWindow(GameResponses.PREPARATION_NOT_DONE.response).start(WelcomeMenu.stage);
            } catch (Exception e) {
            }
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        }
        for (Cell cell : opponentPlayerGameBoard.getSpellAndTrapCardZone()) {
            if (!cell.isEmpty()) {
                cell.removeCardFromCell(opponentPlayerGameBoard);
            }
        }
        updateSpellInGameBoard(gameController);
        ViewInterface.showResult("Harpie's Feather Duster activated : all spells and traps your opponent controls destroyed.");
    }

}