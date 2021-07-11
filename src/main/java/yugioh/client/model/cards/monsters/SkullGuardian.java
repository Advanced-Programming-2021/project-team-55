package yugioh.client.model.cards.monsters;

import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.cardfeaturesenums.CardType;
import yugioh.client.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.client.model.cards.cardfeaturesenums.MonsterType;

public class SkullGuardian extends Monster {

    public SkullGuardian() {
        super("Skull Guardian", "This monster can only be Ritual Summoned with the Ritual Spell Card, \"Novox's Prayer\". You must also offer monsters whose total Level Stars equal 7 or more as a Tribute from the field or your hand."
                , 7900, 2050, 2500, 7, MonsterAttribute.LIGHT, MonsterType.WARRIOR, CardType.RITUAL);
    }

}
