package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class Texchanger extends Monster {

    public Texchanger() {
        super("Texchanger", "Once per turn, when your monster is targeted for an attack: You can negate that attack, then Special Summon 1 Cyberse Normal Monster from your hand, Deck, or GY."
                , 200, 100, 100, 1, MonsterAttribute.DARK, MonsterType.CYBERSE, CardType.EFFECTIVE);
    }

}
