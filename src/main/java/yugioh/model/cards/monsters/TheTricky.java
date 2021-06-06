package yugioh.model.cards.monsters;

import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.board.Cell;
import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;
import yugioh.view.ViewInterface;
import yugioh.view.gamephases.Duel;

public class TheTricky extends Monster {

    public TheTricky() {
        super("The Tricky", "You can Special Summon this card (from your hand) by discarding 1 card."
                , 4300, 2000, 1200, 5, MonsterAttribute.WIND, MonsterType.SPELLCASTER, CardType.EFFECTIVE);
    }

    public static boolean handleEffect(GameController gameController, Cell selectedCell) {
        if (!selectedCell.getCellCard().getName().equals("The Tricky")) return false;

        if (!gameController.getMainPhase1Controller().canSpecialSummon(gameController)) {
            ViewInterface.showResult("Error: The Tricky card effect activated but you can't special summon right now!");
            return false;
        }
        ViewInterface.showResult("you can special summon your \"The Tricky\" card by giving one of your hand cards. do you want? yes/no");
        while (true) {
            String userInput = ViewInterface.getInput();
            if (userInput.equals("no") || userInput.equals("cancel")) return false;
            else if (userInput.equals("yes")) break;
            else ViewInterface.showResult("Error: wrong input! input yes/no");
        }
        ViewInterface.showResult("now select a card from your hand to tribute:");
        while (true) {
            String selectionCommand = ViewInterface.getInput();
            if (!selectionCommand.startsWith("select --hand")) {
                if (selectionCommand.equals("cancel")) {
                    ViewInterface.showResult("you cancelled the effect of your card!");
                    return false;
                }
                ViewInterface.showResult("Error: you should select a card from your hand!");
                continue;
            }
            String result = Duel.processSelect(selectionCommand);
            if (selectedCell.equals(Cell.getSelectedCell())) {
                ViewInterface.showResult("Error: select another card to tribute!");
                continue;
            }
            if (!result.equals("card selected")) {
                ViewInterface.showResult("Error: try again!");
                continue;
            }
            gameController.getCurrentTurnPlayer().getGameBoard().getHandCards().remove(Cell.getSelectedCell());
            Cell.setSelectedCell(selectedCell);
            gameController.shouldSpecialSummonNow = true;
            break;
        }

        ViewInterface.showResult("you can now special summon:");
        return true;
    }

}
