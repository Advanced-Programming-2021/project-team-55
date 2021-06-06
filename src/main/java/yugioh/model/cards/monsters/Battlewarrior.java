package yugioh.model.cards.monsters;

import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;

public class Battlewarrior extends Monster {

    public Battlewarrior() {
        super("Battle warrior", "A warrior that fights with his bare hands!!!"
                , 1300, 700, 1000, 3, MonsterAttribute.EARTH, MonsterType.WARRIOR, CardType.NORMAL);
    }

}
