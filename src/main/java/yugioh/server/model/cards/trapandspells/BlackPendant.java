package yugioh.server.model.cards.trapandspells;

import yugioh.server.controller.gamephasescontrollers.GameController;
import yugioh.server.model.board.CardStatus;
import yugioh.server.model.board.Cell;
import yugioh.server.model.board.GameBoard;
import yugioh.server.model.cards.Card;
import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.SpellAndTrap;
import yugioh.server.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.server.view.ViewInterface;
import yugioh.server.view.gamephases.Duel;
import yugioh.server.view.gamephases.GameResponses;

public class BlackPendant extends SpellAndTrap {
    private Card equippedCard;

    public BlackPendant() {
        super("Black Pendant", "The equipped monster gains 500 ATK. When this card is sent from the field to the Graveyard: Inflict 500 damage to your opponent.",
                4300, SpellOrTrap.SPELL, SpellOrTrapAttribute.EQUIP, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        GameBoard playerGameBoard = gameController.getCurrentTurnPlayer().getGameBoard();
        Cell spellCell = Cell.getSelectedCell();
        if (!playerGameBoard.doesMonsterZoneHaveOccupiedMonsters()) {
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        }
        ViewInterface.showResult("Black Pendant effect activated : select a face-up card to equip");
        String input = ViewInterface.getInput();
        while (true) {
            String response = Duel.processSelect(input);
            if (input.equals("cancel")) {
                ViewInterface.showResult("you cancelled the activation of Black Pendant");
                return;
            }
            if (input.matches("^select --monster (\\d+)$")) {
                if (response.equals(GameResponses.CARD_SELECTED.response)) {
                    Cell selectedCell = Cell.getSelectedCell();
                    if (selectedCell.getCardStatus() != CardStatus.OFFENSIVE_OCCUPIED &&
                            selectedCell.getCardStatus() != CardStatus.DEFENSIVE_OCCUPIED) {
                        ViewInterface.showResult(GameResponses.CANT_EQUIP_CARD_FOR_SPELL.response);
                    } else {
                        ((Monster) selectedCell.getCellCard()).addATK(500);
                        ((BlackPendant) spellCell.getCellCard()).equippedCard = selectedCell.getCellCard();
                        ViewInterface.showResult(selectedCell.getCellCard().getName() + " equipped!");
                        Cell.setSelectedCell(spellCell);
                        updateSpellInGameBoard(gameController);
                        return;
                    }
                }
            }
            ViewInterface.showResult("Error: try again!");
            input = ViewInterface.getInput();
        }
    }

    public static void deActivateEffect(Cell cell) {
        try {
            if (!cell.isEmpty() && cell.getCellCard().getName().equals("Black Pendant")) {
                ((Monster) ((BlackPendant) cell.getCellCard()).equippedCard).addATK(-500);
            }
        } catch (Exception ignored) {
        }
    }
}