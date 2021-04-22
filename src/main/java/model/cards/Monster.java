package model.cards;

import java.util.HashMap;

public class Monster extends Card {

    private int attackPower, defensePower, level;

    HashMap<String, Class<? extends Monster>> AllMonsters = new HashMap<>();

    protected Monster(String cardName, String description, int number, int price, int attackPower, int defensePower, int level) {
        super(cardName, description, number, price);
        setAttackPower(attackPower);
        setDefensePower(defensePower);
        setLevel(level);
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getDefensePower() {
        return defensePower;
    }

    public void setDefensePower(int defensePower) {
        this.defensePower = defensePower;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
