package yugioh.model.cards.monsters;

import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;

public class CrabTurtle extends Monster {

    public CrabTurtle() {
        super("Crab Turtle", "This monster can only be Ritual Summoned with the Ritual Spell Card, \"Turtle Oath\". You must also offer monsters whose total Level Stars equal 8 or more as a Tribute from the field or your hand."
                , 10200, 2550, 2500, 8, MonsterAttribute.WATER, MonsterType.AQUA, CardType.RITUAL);
    }

}
