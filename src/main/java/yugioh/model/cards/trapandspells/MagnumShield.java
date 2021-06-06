package yugioh.model.cards.trapandspells;

import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.board.CardStatus;
import yugioh.model.board.Cell;
import yugioh.model.board.GameBoard;
import yugioh.model.cards.Monster;
import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.model.cards.cardfeaturesenums.MonsterType;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.view.ViewInterface;
import yugioh.view.gamephases.Duel;
import yugioh.view.gamephases.GameResponses;

public class MagnumShield extends SpellAndTrap {
    private static int equipEffect;
    private static Monster equippedMonster;

    public MagnumShield() {
        super("Magnum Shield", "Equip only to a Warrior-Type monster. Apply this effect, depending on its battle position.\n● Attack Position: It gains ATK equal to its original DEF.\n● Defense Position: It gains DEF equal to its original ATK.",
                4300, SpellOrTrap.SPELL, SpellOrTrapAttribute.EQUIP, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        GameBoard playerGameBoard = gameController.getCurrentTurnPlayer().getGameBoard();
        Cell spellCell = Cell.getSelectedCell();
        if (!playerGameBoard.doesMonsterZoneHaveOccupiedMonsters()) {
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        }
        ViewInterface.showResult("Magnum Shield effect activated : select a face-up card to equip");
        String input = ViewInterface.getInput();
        while (true) {
            String response = Duel.processSelect(input);
            if (input.equals("cancel")) {
                ViewInterface.showResult("you cancelled the activation of Magnum Shield");
                return;
            }
            if (input.matches("^select --monster (\\d+)$")) {
                if (response.equals(GameResponses.CARD_SELECTED.response)) {
                    Cell selectedCell = Cell.getSelectedCell();
                    if (selectedCell.getCardStatus() != CardStatus.OFFENSIVE_OCCUPIED &&
                            selectedCell.getCardStatus() != CardStatus.DEFENSIVE_OCCUPIED) {
                        ViewInterface.showResult(GameResponses.CANT_EQUIP_CARD_FOR_SPELL.response);
                    } else {
                        Monster selectedMonster = ((Monster) selectedCell.getCellCard());
                        if (selectedMonster.getMonsterType() == MonsterType.WARRIOR) {
                            if (selectedCell.getCardStatus() == CardStatus.OFFENSIVE_OCCUPIED) {
                                int effect = selectedMonster.getDef();
                                equipEffect = effect;
                                selectedMonster.addATK(effect);
                            } else {
                                int effect = selectedMonster.getAtk();
                                equipEffect = effect;
                                selectedMonster.addDEF(effect);
                            }
                        }
                        equippedMonster = selectedMonster;
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
        if (!cell.isEmpty() && cell.getCellCard().getName().equals("Magnum Shield")) {
            Monster equippedMonster = MagnumShield.equippedMonster;
            int equipEffect = MagnumShield.equipEffect;
            if (equippedMonster.getMonsterType() == MonsterType.WARRIOR) {
                if (equippedMonster.getAtk() == equipEffect) {
                    equippedMonster.addDEF(-equipEffect);
                } else {
                    equippedMonster.addATK(-equipEffect);
                }
            }
        }
    }

}