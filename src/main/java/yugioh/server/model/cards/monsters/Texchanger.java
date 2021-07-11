package yugioh.server.model.cards.monsters;

import yugioh.server.controller.CheatController;
import yugioh.server.controller.gamephasescontrollers.GameController;
import yugioh.server.model.board.Cell;
import yugioh.server.model.cards.Card;
import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.cardfeaturesenums.CardType;
import yugioh.server.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.server.model.cards.cardfeaturesenums.MonsterType;
import yugioh.server.model.exceptions.GameException;
import yugioh.server.view.ViewInterface;

import java.util.ArrayList;

public class Texchanger extends Monster {

    private static ArrayList<Cell> usedTexChangerCellsThisTurn = new ArrayList<>();

    public Texchanger() {
        super("Texchanger", "Once per turn, when your monster is targeted for an attack: You can negate that attack, then Special Summon 1 Cyberse Normal Monster from your hand, Deck, or GY."
                , 200, 100, 100, 1, MonsterAttribute.DARK, MonsterType.CYBERSE, CardType.EFFECTIVE);
    }

    public static boolean handleEffect(GameController gameController, Cell attackedCell) {
        if (!attackedCell.getCellCard().getName().equals("Texchanger")) return false;

        if (usedTexChangerCellsThisTurn.contains(attackedCell)) return false;

        ArrayList<Card> monsters = gameController.getCurrentTurnOpponentPlayer().getGameBoard().getAllMonstersFromAllFieldsWithType(MonsterType.CYBERSE);

        if (monsters.size() == 0) {
            ViewInterface.showResult("Error: Texchanger effect activated: but there isn't any Cyberse monster.");
            return true;
        }

        ViewInterface.showResult("Texchanger effect activated:");
        ViewInterface.showResult("Turn changed temporary.");
        gameController.changeTurn(true, false);

        int counter = 0;
        for (Card card : monsters) {
            counter++;
            System.out.println(counter + ". " + card);
        }
        int inputNumber;
        while (true) {
            String selectionCommand = ViewInterface.getInput();
            if (!selectionCommand.matches("\\d+")) {
                if (selectionCommand.equals("cancel")) {
                    ViewInterface.showResult("you cancelled the effect of your card!");
                    gameController.changeTurn(true, true);
                    return true;
                }
                ViewInterface.showResult("Error: you should enter a number!");
                continue;
            }
            inputNumber = Integer.parseInt(selectionCommand);
            if (inputNumber < 1 || inputNumber > counter) {
                ViewInterface.showResult("Error: there is no monster with this number!");
                continue;
            }
            int numberOfMonsters = gameController.getCurrentTurnPlayer().getGameBoard().getNumberOfMonstersOnMonsterCardZone();
            if (((Monster) monsters.get(inputNumber - 1)).getLevel() == 5 || ((Monster) monsters.get(inputNumber - 1)).getLevel() == 6) {
                if (numberOfMonsters < 1) {
                    ViewInterface.showResult("Error: there is not enough monsters to tribute for this monster. try another monster or cancel the process");
                    continue;
                }
            }
            if (((Monster) monsters.get(inputNumber - 1)).getLevel() >= 7) {
                if (numberOfMonsters < 2) {
                    ViewInterface.showResult("Error: there is not enough monsters to tribute for this monster. try another monster or cancel the process");
                    continue;
                }
            }
            break;
        }

        Cell oldSelectedCell = Cell.getSelectedCell();
        try {
            CheatController.getInstance().addOptionalCardAndSelect(monsters.get(inputNumber - 1).getName(), gameController);
            ViewInterface.showResult("you can now special summon this monster");
            gameController.getMainPhase1Controller().specialSummon(gameController);
        } catch (GameException ignored) {
        }

        ViewInterface.showResult("now turn changed to main player!");
        gameController.changeTurn(true, true);
        Cell.setSelectedCell(oldSelectedCell);
        usedTexChangerCellsThisTurn.add(attackedCell);
        return true;
    }

    public static void makeArrayEmpty() {
        usedTexChangerCellsThisTurn = new ArrayList<>();
    }

}
