package yugioh.model.cards.monsters;

import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.board.Cell;
import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;
import yugioh.model.exceptions.GameException;
import yugioh.view.ViewInterface;
import yugioh.view.gamephases.Duel;

public class TerratigertheEmpoweredWarrior extends Monster {

    public TerratigertheEmpoweredWarrior() {
        super("Terratiger, the Empowered Warrior", "When this card is Normal Summoned: You can Special Summon 1 Level 4 or lower Normal Monster from your hand in Defense Position."
                , 3200, 1800, 1200, 4, MonsterAttribute.EARTH, MonsterType.WARRIOR, CardType.EFFECTIVE);
    }

    public static void handleEffect(GameController gameController, Cell summonedCell) {
        try {
            if (!summonedCell.getCellCard().getName().equals("Terratiger, the Empowered Warrior")) return;
        } catch (Exception e) {
            return;
        }

        ViewInterface.showResult("\"Terratiger, the Empowered Warrior\" effect activated:");
        if (gameController.getCurrentTurnPlayer().getGameBoard().doesHandDeckHaveCard(4, CardType.NORMAL)) {
            ViewInterface.showResult("select a normal monster with at most level 4 to set:");
            while (true) {
                String selectionCommand = ViewInterface.getInput();
                if (!selectionCommand.startsWith("select --hand")) {
                    if (selectionCommand.equals("cancel")) {
                        ViewInterface.showResult("you cancelled the effect of your card!");
                        return;
                    }
                    ViewInterface.showResult("Error: you should select a card from hand!");
                    continue;
                }
                String result = Duel.processSelect(selectionCommand);
                if (!result.equals("card selected")) {
                    ViewInterface.showResult("Error: try again");
                    continue;
                }
                if (Cell.getSelectedCell().getCellCard().getCardKind() != Kind.MONSTER) {
                    ViewInterface.showResult("Error: you should select a monster!");
                    continue;
                }
                if (((Monster) Cell.getSelectedCell().getCellCard()).getCardType() != CardType.NORMAL || ((Monster) Cell.getSelectedCell().getCellCard()).getLevel() > 4) {
                    ViewInterface.showResult("Error: selected monster does not have the requirements");
                    continue;
                }
                try {
                    gameController.getMainPhase1Controller().setCard(gameController);
                    break;
                } catch (GameException e) {
                    ViewInterface.showResult(e.getMessage());
                }
            }
        } else {
            ViewInterface.showResult("you don't have a normal monster with at most level 4 to set!");
        }
    }

}
