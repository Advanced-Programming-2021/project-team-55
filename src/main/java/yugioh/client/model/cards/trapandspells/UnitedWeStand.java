package yugioh.client.model.cards.trapandspells;

import yugioh.client.controller.gamephasescontrollers.GameController;
import yugioh.client.model.board.CardStatus;
import yugioh.client.model.board.Cell;
import yugioh.client.model.board.GameBoard;
import yugioh.client.model.cards.Card;
import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.SpellAndTrap;
import yugioh.client.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.client.view.ViewInterface;
import yugioh.client.view.gamephases.Duel;
import yugioh.client.view.gamephases.GameResponses;

public class UnitedWeStand extends SpellAndTrap {
    private Card equippedCard;
    private int equipEffect;

    public UnitedWeStand() {
        super("United We Stand", "The equipped monster gains 800 ATK/DEF for each face-up monster you control.",
                4300, SpellOrTrap.SPELL, SpellOrTrapAttribute.EQUIP, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        GameBoard playerGameBoard = gameController.getCurrentTurnPlayer().getGameBoard();
        Cell spellCell = Cell.getSelectedCell();
        if (!playerGameBoard.doesMonsterZoneHaveOccupiedMonsters()) {
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        }
        ViewInterface.showResult("United We Stand effect activated : select a face-up card to equip");
        String input = ViewInterface.getInput();
        while (true) {
            String response = Duel.processSelect(input);
            if (input.equals("cancel")) {
                ViewInterface.showResult("you cancelled the activation of United We Stand");
                return;
            }
            if (input.matches("^select --monster (\\d+)$")) {
                if (response.equals(GameResponses.CARD_SELECTED.response)) {
                    Cell selectedCell = Cell.getSelectedCell();
                    if (selectedCell.getCardStatus() != CardStatus.OFFENSIVE_OCCUPIED &&
                            selectedCell.getCardStatus() != CardStatus.DEFENSIVE_OCCUPIED) {
                        ViewInterface.showResult(GameResponses.CANT_EQUIP_CARD_FOR_SPELL.response);
                    } else {
                        int effect = playerGameBoard.getCountOccupiedMonstersInMonsterZone() * 800;
                        ((UnitedWeStand) spellCell.getCellCard()).equipEffect = effect;
                        ((Monster) selectedCell.getCellCard()).addATK(effect);
                        ((Monster) selectedCell.getCellCard()).addDEF(effect);
                        ((UnitedWeStand) spellCell.getCellCard()).equippedCard = selectedCell.getCellCard();
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
        if (!cell.isEmpty() && cell.getCellCard().getName().equals("United We Stand")) {
            try {
                ((Monster) ((UnitedWeStand) cell.getCellCard()).equippedCard).addATK
                        (-((UnitedWeStand) cell.getCellCard()).equipEffect);
            } catch (Exception ignored) {
            }
        }
    }


}