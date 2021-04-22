package model.cards;

public abstract class Monster extends Card {//todo seems useless, can be deleted

    private int attackPower, defensePower, level;

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
