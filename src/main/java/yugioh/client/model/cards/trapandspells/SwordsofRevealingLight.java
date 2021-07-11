package yugioh.client.model.cards.trapandspells;

import yugioh.client.controller.gamephasescontrollers.GameController;
import yugioh.client.model.board.CardStatus;
import yugioh.client.model.board.Cell;
import yugioh.client.model.board.GameBoard;
import yugioh.client.model.cards.SpellAndTrap;
import yugioh.client.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.client.view.ViewInterface;

public class SwordsofRevealingLight extends SpellAndTrap {
    public int counter;

    public SwordsofRevealingLight() {
        super("Swords of Revealing Light", "After this card's activation, it remains on the field, but destroy it during the End Phase of your opponent's 3rd turn. When this card is activated: If your opponent controls a face-down monster, flip all monsters they control face-up. While this card is face-up on the field, your opponent's monsters cannot declare an attack.",
                2500, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, EffectiveTerm.UNLIMITED);
    }

    public static boolean handleEffect(GameController gameController) {
        GameBoard opponentGameBoard = gameController.getCurrentTurnOpponentPlayer().getGameBoard();
        Cell cell = opponentGameBoard.getSpellZoneCardByName("Swords of Revealing Light");
        return cell != null && cell.getCardStatus() == CardStatus.OCCUPIED;
    }

    public static void setActivated(GameController gameController) {
        GameBoard opponentGameBoard = gameController.getCurrentTurnOpponentPlayer().getGameBoard();
        for (Cell cell : opponentGameBoard.getMonsterCardZone()) {
            if (!cell.isEmpty() && cell.getCardStatus() == CardStatus.DEFENSIVE_HIDDEN) {
                cell.setCardStatus(CardStatus.DEFENSIVE_OCCUPIED);
            }
        }
        ViewInterface.showResult("Swords of Revealing Light activated : your opponents face-down monsters flipped to " +
                "face-up and your opponent's monsters cannot declare an attack for 3 rounds.");
        ((SwordsofRevealingLight) Cell.getSelectedCell().getCellCard()).counter = 1;
        updateSpellInGameBoard(gameController);

    }

    public static void updateCounter(GameController gameController) {
        GameBoard opponentPlayerGameBoard = gameController.getCurrentTurnOpponentPlayer().getGameBoard();
        Cell cell = opponentPlayerGameBoard.getSpellZoneCardByName("Swords of Revealing Light");
        if (cell == null || cell.getCardStatus() != CardStatus.OCCUPIED) {
            return;
        }
        if (((SwordsofRevealingLight) cell.getCellCard()).counter == 3) {
            ViewInterface.showResult("Swords of Revealing Light deactivated.");

            cell.removeCardFromCell(opponentPlayerGameBoard);
        } else {
            ((SwordsofRevealingLight) cell.getCellCard()).counter++;
        }
    }

}