package yugioh.client.model.cards.monsters;

import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.cardfeaturesenums.CardType;
import yugioh.client.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.client.model.cards.cardfeaturesenums.MonsterType;

public class WarriorDaiGrepher extends Monster {

    public WarriorDaiGrepher() {
        super("Warrior Dai Grepher", "The warrior who can manipulate dragons. Nobody knows his mysterious past."
                , 3400, 1700, 1600, 4, MonsterAttribute.EARTH, MonsterType.WARRIOR, CardType.NORMAL);
    }

}
