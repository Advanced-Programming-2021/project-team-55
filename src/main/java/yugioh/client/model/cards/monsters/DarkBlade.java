package yugioh.client.model.cards.monsters;

import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.cardfeaturesenums.CardType;
import yugioh.client.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.client.model.cards.cardfeaturesenums.MonsterType;

public class DarkBlade extends Monster {

    public DarkBlade() {
        super("Dark Blade", "They say he is a dragon-manipulating warrior from the dark world. His attack is tremendous, using his great swords with vicious power."
                , 3500, 1800, 1500, 4, MonsterAttribute.DARK, MonsterType.WARRIOR, CardType.NORMAL);
    }

}
