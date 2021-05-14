package model.cards.trapandspells;

import controller.gamephasescontrollers.GameController;
import model.board.CardStatus;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.Card;
import model.cards.Monster;
import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.MonsterType;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import view.ViewInterface;
import view.gamephases.Duel;
import view.gamephases.GameResponses;

public class MagnumShield extends SpellAndTrap {
    private static int equipEffect;
    private static Monster equippedMonster;
    public MagnumShield() {
        super("Magnum Shield", "Equip only to a Warrior-Type monster. Apply this effect, depending on its battle position.\n● Attack Position: It gains ATK equal to its original DEF.\n● Defense Position: It gains DEF equal to its original ATK.",
                4300, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.EQUIP, EffectiveTerm.UNLIMITED);
    }
    public static void setActivated(GameController gameController){
        GameBoard playerGameBoard=gameController.getCurrentTurnPlayer().getGameBoard();
        Cell spellCell=Cell.getSelectedCell();
        if(!playerGameBoard.doesMonsterZoneHaveOccupiedMonsters()){
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        }
        ViewInterface.showResult("Magnum Shield effect activated : select a face-up card to equip");
        String input=ViewInterface.getInput();
        while(true){
            String response = Duel.getMainPhase1().processSelect(input);
            if (input.equals("cancel")) {
                ViewInterface.showResult("you cancelled the activation of Magnum Shield");
                return;
            }
            if (input.matches("^select --monster (\\d+)$")) {
                if (response.equals(GameResponses.CARD_SELECTED.response)) {
                    Cell selectedCell=Cell.getSelectedCell();
                    if(selectedCell.getCardStatus()!= CardStatus.OFFENSIVE_OCCUPIED&&
                            selectedCell.getCardStatus()!=CardStatus.DEFENSIVE_OCCUPIED){
                        ViewInterface.showResult(GameResponses.CANT_EQUIP_CARD_FOR_SPELL.response);
                    }
                    else{
                        Monster selectedMonster=((Monster)selectedCell.getCellCard());
                        if(selectedMonster.getMonsterType()== MonsterType.WARRIOR) {
                            if (selectedCell.getCardStatus() == CardStatus.OFFENSIVE_OCCUPIED) {
                                int effect = selectedMonster.getDef();
                                ((MagnumShield) spellCell.getCellCard()).equipEffect = effect;
                                selectedMonster.addATK(effect);
                            } else {
                                int effect = selectedMonster.getAtk();
                                ((MagnumShield) spellCell.getCellCard()).equipEffect = effect;
                                selectedMonster.addDEF(effect);
                            }
                        }
                        ((MagnumShield)spellCell.getCellCard()).equippedMonster=selectedMonster;
                        ViewInterface.showResult(selectedMonster.getName()+" equipped!");
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
    public static void deActivateEffect(Cell cell){
        if(cell.getCellCard().getName().equals("Magnum Shield")){
            Monster equippedMonster=((MagnumShield)cell.getCellCard()).equippedMonster;
            int equipEffect=((MagnumShield)cell.getCellCard()).equipEffect;
            if(equippedMonster.getMonsterType()==MonsterType.WARRIOR) {
                if (equippedMonster.getAtk() == equipEffect) {
                    equippedMonster.addDEF(-equipEffect);
                } else {
                    equippedMonster.addATK(-equipEffect);
                }
            }
        }
    }

}