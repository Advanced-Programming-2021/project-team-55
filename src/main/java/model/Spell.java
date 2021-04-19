package model;

public class Spell extends Card {

    private boolean activated = false;

    protected Spell(String cardName, String description, int number, int price) {
        super(cardName, description, number, price);
    }

    public void setActivated() {

    }
    public void deactivate (){

    }
    public boolean isActivated(){
        return activated;
    }
}
