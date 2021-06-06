package yugioh.model.cards;

import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.board.CardStatus;
import yugioh.model.board.Cell;
import yugioh.model.board.GameBoard;
import yugioh.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.model.cards.trapandspells.*;
import yugioh.model.cards.trapandspells.*;
import yugioh.model.exceptions.GameException;
import yugioh.view.ViewInterface;
import yugioh.view.gamephases.GameResponses;

public abstract class SpellAndTrap extends Card {

    protected SpellOrTrap type;
    private SpellOrTrapAttribute attribute;
    private EffectiveTerm status;

    public SpellAndTrap(String name, String description, int price,
                        SpellOrTrap type, SpellOrTrapAttribute attribute, EffectiveTerm status) {
        super(name, description, price, Kind.MAGIC, type);
        this.type = type;
        this.attribute = attribute;
        this.status = status;
    }

    public static void activateSpellEffects(GameController gameController, SpellAndTrap spellAndTrap) {
        if (spellAndTrap.name.equals("Monster Reborn")) MonsterReborn.setActivated(gameController);
        else if (spellAndTrap.name.equals("Terraforming")) Terraforming.setActivated(gameController);
        else if (spellAndTrap.name.equals("Pot of Greed")) PotofGreed.setActivated(gameController);
        else if (spellAndTrap.name.equals("Dark Hole")) DarkHole.setActivated(gameController);
        else if (spellAndTrap.name.equals("Spell Absorption")) SpellAbsorption.setActivated(gameController);
        else if (spellAndTrap.name.equals("Harpie's Feather Duster")) HarpiesFeatherDuster.setActivated(gameController);
        else if (spellAndTrap.name.equals("Yami")) Yami.setActivated(gameController);
        else if (spellAndTrap.name.equals("Closed Forest")) ClosedForest.setActivated(gameController);
        else if (spellAndTrap.name.equals("Swords of Revealing Light"))
            SwordsofRevealingLight.setActivated(gameController);
        else if (spellAndTrap.name.equals("Twin Twisters")) TwinTwisters.setActivated(gameController);
        else if (spellAndTrap.name.equals("Mystical space typhoon")) Mysticalspacetyphoon.setActivated(gameController);
        else if (spellAndTrap.name.equals("Black Pendant")) BlackPendant.setActivated(gameController);
        else if (spellAndTrap.name.equals("United We Stand")) UnitedWeStand.setActivated(gameController);
        else if (spellAndTrap.name.equals("Sword of dark destruction"))
            Swordofdarkdestruction.setActivated(gameController);
        else if (spellAndTrap.name.equals("Magnum Shield")) MagnumShield.setActivated(gameController);
        else if (spellAndTrap.name.equals("Advanced Ritual Art")) AdvancedRitualArt.setActivated(gameController);
        else if (spellAndTrap.name.equals("Torrential Tribute")) TorrentialTribute.setActivated(gameController);
        else if (spellAndTrap.name.equals("Trap Hole")) TrapHole.setActivated(gameController);
        else if (spellAndTrap.name.equals("Mirror Force")) MirrorForce.setActivated(gameController);
        else if (spellAndTrap.name.equals("Negate Attack")) NegateAttack.setActivated(gameController);
        else if (spellAndTrap.name.equals("Magic Cylinder")) MagicCylinder.setActivated(gameController);
        else {
            ViewInterface.showResult(GameResponses.ACTIVATION_ONLY_FOR_SPELL.response);
        }
    }

    public static void updateSpellInGameBoard(GameController gameController) {
        Cell selectedCell = Cell.getSelectedCell();
        if (selectedCell == null) return;
        Card card = selectedCell.getCellCard();
        SpellAndTrap spell = (SpellAndTrap) card;
        if (spell == null) return;
        GameBoard playerGameBoard = gameController.getCurrentTurnPlayer().getGameBoard();
        if (!playerGameBoard.isCellInSpellAndTrapZone(selectedCell)) {
            playerGameBoard.getHandCards().remove(selectedCell);
            if (spell.getAttribute() == SpellOrTrapAttribute.FIELD) {
                playerGameBoard.addCardToFieldZone(card);
                gameController.currentTurnOpponentPlayer.getGameBoard().addCardToFieldZone(card);
            } else {
                try {
                    playerGameBoard.addCardToSpellAndTrapCardZone(card, CardStatus.OCCUPIED, gameController);
                } catch (GameException ignored) {
                }
            }
        } else {
            SpellAbsorption.handleEffect();
            selectedCell.setCardStatus(CardStatus.OCCUPIED);
        }
        Cell.deselectCell();
    }

    public SpellOrTrap getType() {
        return type;
    }

    public SpellOrTrapAttribute getAttribute() {
        return attribute;
    }

    public EffectiveTerm getStatus() {
        return status;
    }

}
