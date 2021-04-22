package model.cards.monsters;

import model.cards.MonsterAttribute;
import model.cards.CardType;


public class Marshmallon {

    private String name = "Marshmallon";
    private int level = 3;
    private MonsterAttribute attribute = MonsterAttribute.LIGHT;
    private String monsterType = "Fairy";
    private CardType cardType = CardType.EFFECTIVE;
    private int atk = 300;
    private int def = 500;
    private static final String description = "Cannot be destroyed by battle. After damage calculation, if this card was attacked, and was face-down at the start of the Damage Step: The attacking player takes 1000 damage.";
    private int price = 700;


}
