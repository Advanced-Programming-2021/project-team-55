package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class BeastKingBarbaros extends Monster {

    public BeastKingBarbaros() {
        super("Beast King Barbaros", "You can Normal Summon/Set this card without Tributing, but its original ATK becomes 1900. You can Tribute 3 monsters to Tribute Summon (but not Set) this card. If Summoned this way: Destroy all cards your opponent controls."
                , 9200, 3000, 1200, 8, MonsterAttribute.EARTH, MonsterType.BEAST_WARRIOR, CardType.EFFECTIVE);
    }

}
