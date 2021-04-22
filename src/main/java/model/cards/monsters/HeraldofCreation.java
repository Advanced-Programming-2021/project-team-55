package model.cards.monsters;

import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.CardType;


public class HeraldofCreation {

    private String name = "Herald of Creation";
    private int level = 4;
    private MonsterAttribute attribute = MonsterAttribute.LIGHT;
    private String monsterType = "Spellcaster";
    private CardType cardType = CardType.EFFECTIVE;
    private int atk = 1800;
    private int def = 600;
    private static final String description = "Once per turn: You can discard 1 card, then target 1 Level 7 or higher monster in your Graveyard; add that target to your hand.";
    private int price = 2700;


}
