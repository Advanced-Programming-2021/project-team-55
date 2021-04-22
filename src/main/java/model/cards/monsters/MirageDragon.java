package model.cards.monsters;

import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.CardType;


public class MirageDragon {

    private String name = "Mirage Dragon";
    private int level = 4;
    private MonsterAttribute attribute = MonsterAttribute.LIGHT;
    private String monsterType = "Dragon";
    private CardType cardType = CardType.EFFECTIVE;
    private int atk = 1600;
    private int def = 600;
    private static final String description = "Your opponent cannot activate Trap Cards during the Battle Phase.";
    private int price = 2500;


}
