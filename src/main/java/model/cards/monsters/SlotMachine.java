package model.cards.monsters;

import model.cards.MonsterAttribute;
import model.cards.KindOfMonster;

import static model.cards.MonsterAttribute.*;
import static model.cards.KindOfMonster.*;

public class SlotMachine {

    private String name = "Slot Machine";
    private int level = 7;
    private MonsterAttribute attribute = DARK;
    private String monsterType = "Machine";
    private KindOfMonster kindOfMonster = Normal;
    private int atk = 2000;
    private int def = 2300;
    private static final String description = "The machine's ability is said to vary according to its slot results.";
    private int price = 7500;


}
