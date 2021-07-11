package yugioh.server.model.cards.monsters;

import yugioh.server.controller.gamephasescontrollers.GameController;
import yugioh.server.model.board.CardStatus;
import yugioh.server.model.board.Cell;
import yugioh.server.model.cards.Card;
import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.cardfeaturesenums.CardType;
import yugioh.server.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.server.model.cards.cardfeaturesenums.MonsterType;
import yugioh.server.view.ViewInterface;

import java.util.ArrayList;

public class Scanner extends Monster {

    private static Cell convertedScannerThisTurn = null;

    public Scanner() {
        super("Scanner", "Once per turn, you can select 1 of your opponent's monsters that is removed from play. Until the End Phase, this card's name is treated as the selected monster's name, and this card has the same Attribute, Level, ATK, and DEF as the selected monster. If this card is removed from the field while this effect is applied, remove it from play."
                , 8000, 0, 0, 1, MonsterAttribute.LIGHT, MonsterType.MACHINE, CardType.EFFECTIVE);
    }

    public static void handleEffect(GameController gameController) {
        Cell scannerCell = gameController.getCurrentTurnPlayer().getGameBoard().getMonsterZoneCardByMonsterName("Scanner");
        if (scannerCell == null) return;

        ArrayList<Card> opponentGraveyardMonsters = gameController.getCurrentTurnOpponentPlayer().getGameBoard().getGraveyardMonsters();
        if (opponentGraveyardMonsters.size() == 0) return;

        ViewInterface.showResult("Scanner activated. you can select a monster from opponent graveyard: (enter number)");
        int counter = 0;
        for (Card card : opponentGraveyardMonsters) {
            counter++;
            System.out.println(counter + ". " + card);
        }
        int inputNumber;
        while (true) {
            String selectionCommand = ViewInterface.getInput();
            if (!selectionCommand.matches("\\d+")) {
                if (selectionCommand.equals("cancel")) {
                    ViewInterface.showResult("you cancelled the effect of your card!");
                    return;
                }
                ViewInterface.showResult("Error: you should enter a number!");
                continue;
            }
            inputNumber = Integer.parseInt(selectionCommand);
            if (inputNumber < 1 || inputNumber > counter) {
                ViewInterface.showResult("Error: there is no monster with this number!");
                continue;
            }
            break;
        }
        scannerCell.addCardToCell(opponentGraveyardMonsters.get(inputNumber - 1).clone());
        scannerCell.setCardStatus(CardStatus.OFFENSIVE_OCCUPIED);
        convertedScannerThisTurn = scannerCell;
        ViewInterface.showResult("your scanner converted to " + opponentGraveyardMonsters.get(inputNumber - 1).getName());
    }

    public static void deActivateEffect() {
        if (convertedScannerThisTurn != null)
            convertedScannerThisTurn.setCard(new yugioh.server.model.cards.monsters.Scanner());
        convertedScannerThisTurn = null;
    }

}
