package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class BlueEyeswhitedragon extends Monster {

    public BlueEyeswhitedragon() {
        super("Blue-Eyes white dragon", "This legendary dragon is a powerful engine of destruction. Virtually invincible, very few have faced this awesome creature and lived to tell the tale."
                , 11300, 3000, 2500, 8, MonsterAttribute.LIGHT, MonsterType.DRAGON, CardType.NORMAL);
    }

}
