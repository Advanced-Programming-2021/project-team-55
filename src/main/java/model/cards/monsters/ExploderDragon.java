package model.cards.monsters;

import model.cards.MonsterAttribute;
import model.cards.KindOfMonster;

import static model.cards.MonsterAttribute.*;
import static model.cards.KindOfMonster.*;

public class ExploderDragon {

    private String name = "Exploder Dragon";
    private int level = 3;
    private MonsterAttribute attribute = EARTH;
    private String monsterType = "Dragon";
    private KindOfMonster kindOfMonster = Effect;
    private int atk = 1000;
    private int def = 0;
    private static final String description = "If this card is destroyed by battle and sent to the Graveyard: Destroy the monster that destroyed it. Neither player takes any battle damage from attacks involving this attacking card.";
    private int price = 1000;


}
