package model;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;

public class Deck {
    private String name;
    private User owner;
    private ArrayList<Card> mainDeck;
    private ArrayList<Card> sideDeck;
    private boolean isActive;

    {
        mainDeck = new ArrayList<>();
        sideDeck = new ArrayList<>();
    }

    public Deck(String name, User owner) {
        setName(name);
        setOwner(owner);
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setOwner(User owner) {
        this.owner = owner;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public ArrayList<Card> getDeck(String deckType) {
        if (deckType.equals("mainDeck"))
            return mainDeck;
        else if (deckType.equals("sideDeck"))
            return sideDeck;
        else {
            //exception error
            return null;
        }
    }
    public void deleteDeck(){
        mainDeck.clear();
        sideDeck.clear();
    }
    public void addCartToDeck(Card card, String deckType){
        getDeck(deckType).add(card);
    }
    public boolean isDeckValid(){
        return false;//needs to be written
    }
    public boolean isMainDeckFull(){
        return false;//needs to be written
    }
    public boolean isSideDeckFull(){
        return false;//needs to be written
    }
    public int getACardsCount(){
        return -1;//needs to be written
    }
    public void removeACardFromDeck(Card card){
        //needs to be written.be careful with where the card should be removed from
    }
    public String toString(){
        return null;//needs to be written
    }
}
