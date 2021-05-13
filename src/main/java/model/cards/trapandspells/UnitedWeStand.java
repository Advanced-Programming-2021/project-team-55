package model.cards.trapandspells;

import controller.gamephasescontrollers.GameController;
import model.board.CardStatus;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.Card;
import model.cards.Monster;
import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import view.ViewInterface;
import view.gamephases.Duel;
import view.gamephases.GameResponses;

public class UnitedWeStand extends SpellAndTrap {
    private Card equippedCard;
    private int equipEffect;
    public UnitedWeStand() {
        super("United We Stand", "The equipped monster gains 800 ATK/DEF for each face-up monster you control.",
                4300, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.EQUIP, EffectiveTerm.UNLIMITED);
    }
    public static void setActivated(GameController gameController){
        GameBoard playerGameBoard=gameController.getCurrentTurnPlayer().getGameBoard();
        Cell spellCell=Cell.getSelectedCell();
        if(!playerGameBoard.doesMonsterZoneHaveOccupiedMonsters()){
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        }
        ViewInterface.showResult("United We Stand effect activated : select a face-up card to equip");
        String input=ViewInterface.getInput();
        while(true){
            String response = Duel.getMainPhase1().processSelect(input);
            if (input.equals("cancel")) {
                ViewInterface.showResult("you cancelled the activation of United We Stand");
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
                        int effect= playerGameBoard.getCountOccupiedMonstersInMonsterZone()*800;
                        ((UnitedWeStand)spellCell.getCellCard()).equipEffect= effect;
                        ((Monster)selectedCell.getCellCard()).addATK(effect);
                        ((Monster)selectedCell.getCellCard()).addDEF(effect);
                        ((UnitedWeStand)spellCell.getCellCard()).equippedCard=selectedCell.getCellCard();
                        ViewInterface.showResult(selectedCell.getCellCard().getName()+" equipped!");
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
        if(cell.getCellCard().getName().equals("United We Stand")){
            ((Monster)((UnitedWeStand)cell.getCellCard()).equippedCard).addATK
                    (-((UnitedWeStand)cell.getCellCard()).equipEffect);
        }
    }

}