package yugioh.server.model.cards.monsters;

import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.cardfeaturesenums.CardType;
import yugioh.server.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.server.model.cards.cardfeaturesenums.MonsterType;

public class FeralImp extends Monster {

    public FeralImp() {
        super("Feral Imp", "A playful little fiend that lurks in the dark, waiting to attack an unwary enemy."
                , 2800, 1300, 1400, 4, MonsterAttribute.DARK, MonsterType.FIEND, CardType.NORMAL);
    }

}
