package yugioh.model.cards.trapandspells;

import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.Player;
import yugioh.model.board.Cell;
import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.view.ViewInterface;

import java.util.ArrayList;

public class TimeSeal extends SpellAndTrap {

    private static final ArrayList<Player> bannedPlayersForOneTurn = new ArrayList<>();

    public TimeSeal() {
        super("Time Seal", "Skip the Draw Phase of your opponent's next turn.",
                2000, SpellOrTrap.TRAP, SpellOrTrapAttribute.NORMAL, EffectiveTerm.LIMITED);
    }

    public static void setActivated(GameController gameController) {
        if (!Cell.getSelectedCell().getCellCard().getName().equals("Time Seal")) return;
        bannedPlayersForOneTurn.add(gameController.getCurrentTurnOpponentPlayer());
        ViewInterface.showResult("Time Seal activated.");
    }

    public static boolean handleEffect(GameController gameController, Player currentPlayer) {
        if (bannedPlayersForOneTurn.contains(currentPlayer)) {
            gameController.changeTurn(true, false);
            ViewInterface.showResult(gameController.getCurrentTurnPlayer().getUser().getNickname() + " do you want to activate your Time Seal effect? yes/no");
            if (!ViewInterface.getInput().equals("yes")) {
                gameController.changeTurn(true, true);
                return false;
            }
            gameController.changeTurn(true, true);
            bannedPlayersForOneTurn.remove(currentPlayer);
            ViewInterface.showResult("you didn't get any hand cards due to Time Seal effect.");
            return true;
        }
        return false;
    }

}