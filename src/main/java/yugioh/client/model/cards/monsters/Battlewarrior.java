package yugioh.client.model.cards.monsters;

import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.cardfeaturesenums.CardType;
import yugioh.client.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.client.model.cards.cardfeaturesenums.MonsterType;

public class Battlewarrior extends Monster {

    public Battlewarrior() {
        super("Battle warrior", "A warrior that fights with his bare hands!!!"
                , 1300, 700, 1000, 3, MonsterAttribute.EARTH, MonsterType.WARRIOR, CardType.NORMAL);
    }

}
