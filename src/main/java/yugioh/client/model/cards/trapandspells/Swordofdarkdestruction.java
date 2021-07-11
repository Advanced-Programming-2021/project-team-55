package yugioh.client.model.cards.trapandspells;

import yugioh.client.controller.gamephasescontrollers.GameController;
import yugioh.client.model.board.CardStatus;
import yugioh.client.model.board.Cell;
import yugioh.client.model.board.GameBoard;
import yugioh.client.model.cards.Card;
import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.SpellAndTrap;
import yugioh.client.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.client.model.cards.cardfeaturesenums.MonsterType;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.client.view.ViewInterface;
import yugioh.client.view.gamephases.Duel;
import yugioh.client.view.gamephases.GameResponses;

public class Swordofdarkdestruction extends SpellAndTrap {
    private Card equippedCard;

    public Swordofdarkdestruction() {
        super("Sword of dark destruction", "A DARK monster equipped with this card increases its ATK by 400 points and decreases its DEF by 200 points.",
                4300, SpellOrTrap.SPELL, SpellOrTrapAttribute.EQUIP, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        GameBoard playerGameBoard = gameController.getCurrentTurnPlayer().getGameBoard();
        Cell spellCell = Cell.getSelectedCell();
        if (!playerGameBoard.doesMonsterZoneHaveOccupiedMonsters()) {
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        }
        ViewInterface.showResult("Sword of dark destruction effect activated : select a face-up card to equip");
        String input = ViewInterface.getInput();
        while (true) {
            String response = Duel.processSelect(input);
            if (input.equals("cancel")) {
                ViewInterface.showResult("you cancelled the activation of Sword of dark destruction");
                return;
            }
            if (input.matches("^select --monster (\\d+)$")) {
                if (response.equals(GameResponses.CARD_SELECTED.response)) {
                    Cell selectedCell = Cell.getSelectedCell();
                    if (selectedCell.getCardStatus() != CardStatus.OFFENSIVE_OCCUPIED &&
                            selectedCell.getCardStatus() != CardStatus.DEFENSIVE_OCCUPIED) {
                        ViewInterface.showResult(GameResponses.CANT_EQUIP_CARD_FOR_SPELL.response);
                    } else {
                        Monster selectedMonster = (Monster) selectedCell.getCellCard();
                        if (selectedMonster.getMonsterType() == MonsterType.FIEND ||
                                selectedMonster.getMonsterType() == MonsterType.SPELLCASTER) {
                            selectedMonster.addATK(400);
                            selectedMonster.addDEF(-200);
                        }
                        ((Swordofdarkdestruction) spellCell.getCellCard()).equippedCard = selectedCell.getCellCard();
                        ViewInterface.showResult(selectedMonster.getName() + " equipped!");
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
        if (!cell.isEmpty() && cell.getCellCard().getName().equals("Sword of dark destruction")) {
            Monster equipedMonster = (Monster) ((Swordofdarkdestruction) cell.getCellCard()).equippedCard;
            if (equipedMonster.getMonsterType() == MonsterType.FIEND || equipedMonster.getMonsterType() == MonsterType.SPELLCASTER) {
                equipedMonster.addATK(-400);
                equipedMonster.addDEF(200);
            }
        }
    }

}