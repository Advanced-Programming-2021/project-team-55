package model;

public class Monster extends Card {
    private int attackPower, defensePower, level;

    protected Monster(String cardName, String description, int number, int price, int attackPower, int defensePower, int level) {
        super(cardName, description, number, price);
        setAttackPower(attackPower);
        setDefensePower(defensePower);
        setLevel(level);
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public void setDefensePower(int defensePower) {
        this.defensePower = defensePower;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefensePower() {
        return defensePower;
    }

    public int getLevel() {
        return level;
    }
}
