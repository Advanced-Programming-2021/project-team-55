package model.cards;

import controller.gamephasescontrollers.GameController;
import model.board.CardStatus;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.trapandspells.MonsterReborn;
import model.cards.trapandspells.PotofGreed;
import model.cards.trapandspells.Terraforming;
import model.exceptions.GameException;

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
    public static void activateSpellEffects(GameController gameController,SpellAndTrap spellAndTrap){
        if(spellAndTrap.name.equals("Monster Reborn"))MonsterReborn.setActivated(gameController);
        else if(spellAndTrap.name.equals("Terraforming")) Terraforming.setActivated(gameController);
        else if(spellAndTrap.name.equals("Pot of Greed")) PotofGreed.setActivated(gameController);
        //...
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
                }catch (GameException e){}
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
