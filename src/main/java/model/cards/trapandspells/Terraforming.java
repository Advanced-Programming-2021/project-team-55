package model.cards.trapandspells;

import controller.gamephasescontrollers.GameController;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import view.ViewInterface;
import view.gamephases.GameResponses;

public class Terraforming extends SpellAndTrap {

    public Terraforming() {
        super("Terraforming", "Add 1 Field Spell from your Deck to your hand.",
                2500, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, EffectiveTerm.LIMITED);
    }
    public static void setActivated(GameController gameController){
        Cell fieldSpell=new Cell();
        GameBoard playerGameBoard=gameController.currentTurnPlayer.getGameBoard();
        for(Cell cell:playerGameBoard.getDeckZone()){
            if(cell.getCellCard().isSpell()&&((SpellAndTrap)cell.getCellCard()).getAttribute()==SpellOrTrapAttribute.FIELD){
                fieldSpell=cell;
                playerGameBoard.addCardToHandDeck(fieldSpell.getCellCard().getName());
                break;
            }
        }
        if(fieldSpell.isEmpty()){
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        }
        else{
            ViewInterface.showResult("Monster Reborn activated. "+fieldSpell.getCellCard().getName()+" added to hand");
            updateSpellInGameBoard(gameController);
        }
    }
}