package model.cards;

import controller.gamephasescontrollers.GameController;
import model.board.CardStatus;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.trapandspells.*;
import model.exceptions.GameException;
import view.ViewInterface;
import view.gamephases.GameResponses;

public abstract class SpellAndTrap extends Card {

    private boolean isActive;

    protected SpellOrTrap type;
    private SpellOrTrapAttribute attribute;
    private EffectiveTerm status;

    public SpellAndTrap(String name, String description, int price, boolean isActive,
                        SpellOrTrap type, SpellOrTrapAttribute attribute, EffectiveTerm status) {
        super(name, description, price, Kind.MAGIC, type);
        this.isActive = isActive;
        this.type = type;
        this.attribute = attribute;
        this.status = status;
    }

    public static void activateSpellOrTrapEffects(GameController gameController, SpellAndTrap spellAndTrap){

        if(spellAndTrap.name.equals("Monster Reborn"))MonsterReborn.setActivated(gameController);
        else if(spellAndTrap.name.equals("Terraforming")) Terraforming.setActivated(gameController);
        else if(spellAndTrap.name.equals("Pot of Greed")) PotofGreed.setActivated(gameController);
        else if (spellAndTrap.name.equals("Dark Hole")) DarkHole.setActivated(gameController);
        else if (spellAndTrap.name.equals("Spell Absorption")) SpellAbsorption.setActivated(gameController);
        else if(spellAndTrap.name.equals("Harpie's Feather Duster")) HarpiesFeatherDuster.setActivated(gameController);
        else if(spellAndTrap.name.equals("Yami")) Yami.setActivated(gameController);
        else if(spellAndTrap.name.equals("Closed Forest")) ClosedForest.setActivated(gameController);
        else if(spellAndTrap.name.equals("Swords of Revealing Light"))SwordsofRevealingLight.setActivated(gameController);
        else if (spellAndTrap.name.equals("Twin Twisters")) TwinTwisters.setActivated(gameController);
        else if(spellAndTrap.name.equals("Mystical space typhoon"))Mysticalspacetyphoon.setActivated(gameController);
        else if(spellAndTrap.name.equals("Black Pendant"))BlackPendant.setActivated(gameController);
        else if(spellAndTrap.name.equals("United We Stand"))UnitedWeStand.setActivated(gameController);
        else{
            ViewInterface.showResult(GameResponses.ACTIVATION_ONLY_FOR_SPELL.response);
        }
        //...
        SpellAbsorption.handleEffect();
    }

    public static void setActivated(GameController gameController){
        return;
    }

    public void deactivate() {

    }

    public static void updateSpellInGameBoard(GameController gameController) {
        Cell selectedCell= Cell.getSelectedCell();
        Card card = selectedCell.getCellCard();
        SpellAndTrap spell = (SpellAndTrap) card;
        GameBoard playerGameBoard=gameController.getCurrentTurnPlayer().getGameBoard();
        if (!playerGameBoard.isCellInSpellAndTrapZone(selectedCell)) {
            playerGameBoard.getHandCards().remove(selectedCell);
            if (spell.getAttribute() == SpellOrTrapAttribute.FIELD) {
                playerGameBoard.addCardToFieldZone(card);
                gameController.currentTurnOpponentPlayer.getGameBoard().addCardToFieldZone(card);
            } else {
                try {
                    playerGameBoard.addCardToSpellAndTrapCardZone(card, CardStatus.OCCUPIED, gameController);
                }catch (GameException ignored){}
            }
        } else {
            selectedCell.setCardStatus(CardStatus.OCCUPIED);
        }
        Cell.deselectCell();
    }

    public boolean isActivated() {
        return isActive;
    }


    public void setActive(boolean active) {
        isActive = active;
    }

    public SpellOrTrap getType() {
        return type;
    }

    public void setType(SpellOrTrap type) {
        this.type = type;
    }

    public SpellOrTrapAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(SpellOrTrapAttribute attribute) {
        this.attribute = attribute;
    }

    public EffectiveTerm getStatus() {
        return status;
    }

    public void setStatus(EffectiveTerm status) {
        this.status = status;
    }

}
