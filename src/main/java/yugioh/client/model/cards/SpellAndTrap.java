package yugioh.client.model.cards;

import yugioh.client.controller.gamephasescontrollers.GameController;
import yugioh.client.model.board.CardStatus;
import yugioh.client.model.board.Cell;
import yugioh.client.model.board.GameBoard;
import yugioh.client.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.client.model.cards.trapandspells.*;
import yugioh.client.model.exceptions.GameException;
import yugioh.client.view.ViewInterface;
import yugioh.client.view.gamephases.GameResponses;

public class SpellAndTrap extends Card {

    private final SpellOrTrapAttribute attribute;
    private final EffectiveTerm status;
    protected SpellOrTrap type;

    public SpellAndTrap(String name, String description, int price,
                        SpellOrTrap type, SpellOrTrapAttribute attribute, EffectiveTerm status) {
        super(name, description, price, Kind.MAGIC, type);
        this.type = type;
        this.attribute = attribute;
        this.status = status;
    }

    public static void activateSpellEffects(GameController gameController, String spellAndTrapName) {
        if (spellAndTrapName.equals("Monster Reborn")) MonsterReborn.setActivated(gameController);
        else if (spellAndTrapName.equals("Terraforming")) Terraforming.setActivated(gameController);
        else if (spellAndTrapName.equals("Pot of Greed")) PotofGreed.setActivated(gameController);
        else if (spellAndTrapName.equals("Dark Hole")) DarkHole.setActivated(gameController);
        else if (spellAndTrapName.equals("Spell Absorption")) SpellAbsorption.setActivated(gameController);
        else if (spellAndTrapName.equals("Harpie's Feather Duster")) HarpiesFeatherDuster.setActivated(gameController);
        else if (spellAndTrapName.equals("Yami")) Yami.setActivated(gameController);
        else if (spellAndTrapName.equals("Closed Forest")) ClosedForest.setActivated(gameController);
        else if (spellAndTrapName.equals("Swords of Revealing Light"))
            SwordsofRevealingLight.setActivated(gameController);
        else if (spellAndTrapName.equals("Twin Twisters")) TwinTwisters.setActivated(gameController);
        else if (spellAndTrapName.equals("Umiiruka")) Umiiruka.setActivated(gameController);
        else if (spellAndTrapName.equals("Mystical space typhoon")) Mysticalspacetyphoon.setActivated(gameController);
        else if (spellAndTrapName.equals("Black Pendant")) BlackPendant.setActivated(gameController);
        else if (spellAndTrapName.equals("United We Stand")) UnitedWeStand.setActivated(gameController);
        else if (spellAndTrapName.equals("Sword of dark destruction"))
            Swordofdarkdestruction.setActivated(gameController);
        else if (spellAndTrapName.equals("Raigeki")) Raigeki.setActivated(gameController);
        else if (spellAndTrapName.equals("Magnum Shield")) MagnumShield.setActivated(gameController);
        else if (spellAndTrapName.equals("Advanced Ritual Art")) AdvancedRitualArt.setActivated(gameController);
        else if (spellAndTrapName.equals("Torrential Tribute")) TorrentialTribute.setActivated(gameController);
        else if (spellAndTrapName.equals("Trap Hole")) TrapHole.setActivated(gameController);
        else if (spellAndTrapName.equals("Mirror Force")) MirrorForce.setActivated(gameController);
        else if (spellAndTrapName.equals("Negate Attack")) NegateAttack.setActivated(gameController);
        else if (spellAndTrapName.equals("Magic Cylinder")) MagicCylinder.setActivated(gameController);
        else if (spellAndTrapName.equals("Forest")) Forest.setActivated(gameController);
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

            if (spell.getAttribute() == SpellOrTrapAttribute.FIELD) {
                playerGameBoard.addCardToFieldZone(selectedCell);
                gameController.currentTurnOpponentPlayer.getGameBoard().addCardToFieldZone(selectedCell);
            } else {
                try {

                    playerGameBoard.addCardToSpellAndTrapCardZone(card, CardStatus.OCCUPIED, gameController, !(((SpellAndTrap) card).getAttribute() == SpellOrTrapAttribute.CONTINUOUS) &&
                            !(((SpellAndTrap) card).getAttribute() == SpellOrTrapAttribute.EQUIP));
                } catch (GameException ignored) {
                }
            }
            playerGameBoard.removeCardFromHand(selectedCell);
        } else {
            SpellAbsorption.handleEffect();
            selectedCell.setCardStatus(CardStatus.OCCUPIED);
            gameController.currentTurnPlayer.getGameBoard().setFlipTransition(selectedCell.getCellCard(), selectedCell.getCellRectangle(), false, !(((SpellAndTrap) card).getAttribute() == SpellOrTrapAttribute.CONTINUOUS) &&
                    !(((SpellAndTrap) card).getAttribute() == SpellOrTrapAttribute.EQUIP));
            Cell.deselectCell();
        }
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
