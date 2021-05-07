package model.cards.monsters;

import controller.gamephasescontrollers.GameController;
import model.board.CardStatus;
import model.board.Cell;
import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;
import model.exceptions.GameException;
import view.ViewInterface;

public class Marshmallon extends Monster {

    public Marshmallon() {
        super("Marshmallon", "Cannot be destroyed by battle. After damage calculation, if this card was attacked, and was face-down at the start of the Damage Step: The attacking player takes 1000 damage."
                , 700, 300, 500, 3, MonsterAttribute.LIGHT, MonsterType.FAIRY, CardType.EFFECTIVE);
    }
    public static void handleEffect(GameController gameController, Cell attackerCell,Cell attackedCell){
        if(attackedCell.getCellCard().getName().equals("Marshmallon")){
            if(attackedCell.getCardStatus()== CardStatus.DEFENSIVE_HIDDEN) {
                gameController.getCurrentTurnPlayer().decreaseLP(1000);
                ViewInterface.showResult("\nMarshmallon effect activated: attacking player takes 1000 damage.");
            }
        }
        return ;
    }

}
