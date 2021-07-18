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

public class Raigeki extends SpellAndTrap {

    public Raigeki() {
        super("Raigeki", "Destroy all monsters your opponent controls.",
                2500, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, EffectiveTerm.LIMITED);
    }

    public static void setActivated(GameController gameController) {
        GameBoard opponentPlayerGameBoard = gameController.getCurrentTurnOpponentPlayer().getGameBoard();
        if (!opponentPlayerGameBoard.doesMonsterZoneHaveMonsters(1)) {
            try {
                new PopUpWindow(GameResponses.PREPARATION_NOT_DONE.response).start(WelcomeMenu.stage);
            } catch (Exception e) {
            }
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        }
        for (Cell cell : opponentPlayerGameBoard.getMonsterCardZone()) {
            if (!cell.isEmpty()) {
                cell.removeCardFromCell(opponentPlayerGameBoard);
            }
        }
        updateSpellInGameBoard(gameController);
        ViewInterface.showResult("Raigeki activated : all opponents monsters destroyed.");

    }

}