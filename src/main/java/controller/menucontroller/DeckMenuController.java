package controller.menucontroller;

import exceptions.MenuException;
import model.Card;
import model.Deck;
import model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DeckMenuController extends MenuController {
    private static DeckMenuController deckMenuController;

    private DeckMenuController() {
    }

    public static DeckMenuController getInstance() {
        if (deckMenuController == null) {
            return new DeckMenuController();
        } else {
            return deckMenuController;
        }
    }

    public void createDeck(String deckName) throws MenuException {
        if (Deck.deckNameExists(deckName, User.loggedInUser)) {
            throw new MenuException("deck with name " + deckName + " already exists");
        } else {
            User.loggedInUser.addDeck(new Deck(deckName, User.loggedInUser));
        }

    }

    public void deleteDeck(String deckName) throws MenuException {
        Deck deck = User.loggedInUser.getDeckByName(deckName);
        if(deck==null){
            throw new MenuException("deck with name " + deckName + " does not exist");
        }
        else {
            User.loggedInUser.removeDeck(deck);
            User.loggedInUser.addCardsToInventory(deck.getMainDeck());
            User.loggedInUser.addCardsToInventory(deck.getSideDeck());
        }
    }

    public void activeDeck(String deckName)throws MenuException {
        Deck deck=User.loggedInUser.getDeckByName(deckName);
        if(deck==null){
            throw new MenuException("deck with name "+deckName+" does not exist");
        }
        else{
            User.loggedInUser.setActiveDeck(deck);
        }

    }

    public void addCardToDeck(String cardName, String deckName, boolean isSide)throws MenuException {
        Card card=Card.getCardByName(cardName);
        Deck deck=User.loggedInUser.getDeckByName(deckName);
        if(!User.loggedInUser.cardExistsInInventory(card)){
            throw new MenuException("card with name "+cardName+" does not exist");
        }
        else if(deck==null){
            throw new MenuException("deck with name "+deckName+" does not exist");
        }
        else if(deck.isMainDeckFull()){
            throw new MenuException("main deck is full");
        }
        else if(deck.isSideDeckFull()){
            throw new MenuException("side deck is full");
        }
        else if(deck.getCardCountInDeck(card)==3){
            throw new MenuException("there are already three cards with name "+cardName+" in deck " +deckName);
        }
        else{
            User.loggedInUser.removeCardFromInventory(card);
            if(isSide){
                deck.addCardToSideDeck(card);
            }
            else{
                deck.addCardToMainDeck(card);
            }
        }
    }

    public void removeCardFromDeck(String cardName, String deckName, boolean isSide)throws MenuException {
        Deck deck=User.loggedInUser.getDeckByName(deckName);
        if(deck==null){
            throw new MenuException("deck with name "+deckName+" does not exist");
        }
        else if(isSide&&!deck.cardExistsInSideDeck(cardName)){
            throw new MenuException("card with name "+cardName+" does not exist in side deck");
        }
        else if(!isSide&!deck.cardExistsInMainDeck(cardName)){
            throw new MenuException("card with name "+cardName+" does not exist in main deck");
        }
        else{
            if(isSide){
                deck.removeCardFromSideDeck(cardName);
            }
            else{
                deck.removeCardFromMainDeck(cardName);
            }
        }
    }

    public ArrayList<Deck> getAllDecks() {
        ArrayList<Deck>decksToShow=User.loggedInUser.getDecks();
        Deck activeDeck=User.loggedInUser.getActiveDeck();
        boolean activeDeckRemoved=false;
        if(activeDeck!=null){
            decksToShow.remove(activeDeck);
            activeDeckRemoved=true;
        }
        Collections.sort(decksToShow, new Comparator<Deck>() {
            @Override
            public int compare(Deck deck1, Deck deck2) {
                return deck1.getName().compareTo(deck2.getName());
            }
        });
        if(activeDeckRemoved){
            decksToShow.add(0,activeDeck);
        }
        return decksToShow;
    }

    public Deck getADeck(String deckName) {

        return new Deck("", User.loggedInUser);
    }

    public ArrayList<Card> getCards() {
        //TODO get cards from deck
        return new ArrayList<>();
    }

    @Override
    public void enterMenu(String menu) throws MenuException {

    }

    @Override
    public void exitMenu() {

    }
}
