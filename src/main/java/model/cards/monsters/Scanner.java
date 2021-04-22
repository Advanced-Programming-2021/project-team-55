package model.cards.monsters;

import model.cards.MonsterAttribute;
import model.cards.CardType;


public class Scanner {

    private String name = "Scanner";
    private int level = 1;
    private MonsterAttribute attribute = MonsterAttribute.LIGHT;
    private String monsterType = "Machine";
    private CardType cardType = CardType.EFFECTIVE;
    private int atk = 0;
    private int def = 0;
    private static final String description = "Once per turn, you can select 1 of your opponent's monsters that is removed from play. Until the End Phase, this card's name is treated as the selected monster's name, and this card has the same Attribute, Level, ATK, and DEF as the selected monster. If this card is removed from the field while this effect is applied, remove it from play.";
    private int price = 8000;


}
