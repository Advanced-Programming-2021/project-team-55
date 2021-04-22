package model.cards.monsters;

import model.cards.MonsterAttribute;
import model.cards.KindOfMonster;

import static model.cards.MonsterAttribute.*;
import static model.cards.KindOfMonster.*;

public class Texchanger {

    private String name = "Texchanger";
    private int level = 1;
    private MonsterAttribute attribute = DARK;
    private String monsterType = "Cyberse";
    private KindOfMonster kindOfMonster = Effect;
    private int atk = 100;
    private int def = 100;
    private static final String description = "Once per turn, when your monster is targeted for an attack: You can negate that attack, then Special Summon 1 Cyberse Normal Monster from your hand, Deck, or GY.";
    private int price = 200;


}
