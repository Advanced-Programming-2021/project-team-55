package model.cards;

public abstract class SpellAndTrap extends Card {//todo seems useless, can be deleted

    private boolean activated = false;

    protected SpellAndTrap(String cardName, String description, int price) {
        super(cardName, description, price);
    }

    public void setActivated() {

    }
    public void deactivate (){

    }
    public boolean isActivated(){
        return activated;
    }

}
