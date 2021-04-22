package model.cards;

import model.User;

import java.util.ArrayList;

public class Deck {
    private String name;
    private User owner;
    private ArrayList<Card>mainDeck;
    private ArrayList<Card>sideDeck;
    private boolean isActive;


    public Deck(String name,User owner){

    }

    public static boolean deckNameExists(String deckName,User owner){
        //TODO: it only checks Deck names in owner decks.Is it correct?
        //todo گفتن انگار قرار نیست تست کیس بدن. هرجور برامون منطقی بود بزنیم به نظرم
        for(Deck deck:owner.getDecks()){
            if(deck.getName().equals(deckName)){
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    private void setOwner(User owner) {
        this.owner = owner;
    }

    public ArrayList<Card> getMainDeck() {
        return mainDeck;
    }

    public void addCardToMainDeck(Card card) {
        mainDeck.add(card);
    }

    public ArrayList<Card> getSideDeck() {
        return sideDeck;
    }

    public void addCardToSideDeck(Card card) {
        sideDeck.add(card);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void deleteDeck(){
        //TODO deck has to be deleted from user
    }
    public boolean isDeckValid(){
        //TODO checkForEachCardFrequency
        return true;
    }
    public boolean isMainDeckFull(){
        return mainDeck.size()==60;
    }
    public boolean isSideDeckFull(){
        return sideDeck.size()==15;
    }
    public int getCardCountInDeck(Card cardToSearch){
        int count=0;
        for(Card card:mainDeck){
            if(card.getName().equals(cardToSearch.getName())){
                count++;
            }
        }
        for(Card card:sideDeck){
            if(card.getName().equals(cardToSearch.getName())){
                count++;
            }
        }
        return count;
    }
    public void removeCardFromMainDeck(String cardName){
        for(Card card:mainDeck){//TODO : check if we have to remove all cards with that name from deck or just one card
            if(card.getName().equals(cardName)){
                mainDeck.remove(card);
                ArrayList<Card>cardsRemoved=new ArrayList<>();
                cardsRemoved.add(card);
                owner.addCardsToInventory(cardsRemoved);
                return;
            }
        }
    }
    public void removeCardFromSideDeck(String cardName){
        for(Card card:sideDeck){
            if(card.getName().equals(cardName)){
                sideDeck.remove(card);
                ArrayList<Card>cardsRemoved=new ArrayList<>();
                cardsRemoved.add(card);
                owner.addCardsToInventory(cardsRemoved);
                return;
            }
        }
    }
    public boolean cardExistsInMainDeck(String cardName){
        for(Card card:mainDeck){
            if(card.getName().equals(cardName)){
                return true;
            }
        }
        return false;
    }
    public boolean cardExistsInSideDeck(String cardName){
        for(Card card:sideDeck){
            if(card.getName().equals(cardName)){
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        String information= name+": main deck "+mainDeck.size()+", side deck "+sideDeck.size()+", ";
        if(isDeckValid()){
            return information+"valid";
        }
        else{
            return information+"invalid";
        }
    }
}
