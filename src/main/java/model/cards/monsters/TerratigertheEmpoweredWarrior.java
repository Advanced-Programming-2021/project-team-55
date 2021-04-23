package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class TerratigertheEmpoweredWarrior extends Monster {

    public TerratigertheEmpoweredWarrior() {
        super("Terratiger, the Empowered Warrior", "When this card is Normal Summoned: You can Special Summon 1 Level 4 or lower Normal Monster from your hand in Defense Position."
                , 3200, 1800, 1200, 4, MonsterAttribute.EARTH, MonsterType.WARRIOR, CardType.EFFECTIVE);
    }

}
