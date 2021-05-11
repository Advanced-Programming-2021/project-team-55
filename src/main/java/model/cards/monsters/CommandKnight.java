package model.cards.monsters;

import controller.gamephasescontrollers.GameController;
import model.Player;
import model.board.CardStatus;
import model.board.Cell;
import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;
import view.ViewInterface;
import view.gamephases.Duel;

public class CommandKnight extends Monster {

    private boolean isActive = false;
    private Player owner;

    public CommandKnight() {
        super("Command Knight", "All Warrior-Type monsters you control gain 400 ATK. If you control another monster, monsters your opponent controls cannot target this card for an attack."
                , 2100, 1000, 1000, 4, MonsterAttribute.FIRE, MonsterType.WARRIOR, CardType.EFFECTIVE);
    }

    public static void handleEffect(CardStatus cardStatus, Cell cell){//todo deactivate
        if (cell == null || cell.isEmpty() || cardStatus != CardStatus.OFFENSIVE_OCCUPIED ||
                !cell.getCellCard().getName().equals("Command Knight")) return;

        if (((CommandKnight) cell.getCellCard()).isActive) return;

        ViewInterface.showResult("Command Knight effect activated: all monsters atk will increase 400");
        Duel.getGameController().getCurrentTurnPlayer().getGameBoard().addAllMonstersATK(400);
        ((CommandKnight)cell.getCellCard()).isActive = true;
        ((CommandKnight)cell.getCellCard()).owner = Duel.getGameController().getCurrentTurnPlayer();
    }

    public static boolean handleEffect(GameController gameController, Cell cell){
        if (cell == null || cell.isEmpty() || cell.cardStatus != CardStatus.OFFENSIVE_OCCUPIED ||
                !cell.getCellCard().getName().equals("Command Knight")) return false;

        return gameController.getCurrentTurnOpponentPlayer().getGameBoard().getNumberOfMonstersOnMonsterCardZone() >= 2;
    }

    public static void deActivateEffect(Cell cell){
        if (cell == null || cell.isEmpty() || !cell.getCellCard().getName().equals("Command Knight") ||
                cell.getCardStatus() == CardStatus.OFFENSIVE_OCCUPIED) return;

        ViewInterface.showResult("Command Knight effect deActivated: all monsters atk will decrease 400");
        ((CommandKnight)cell.getCellCard()).owner.getGameBoard().addAllMonstersATK(-400);
    }

}
