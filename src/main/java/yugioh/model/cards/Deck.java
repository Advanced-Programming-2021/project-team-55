package yugioh.model.cards;

import yugioh.model.User;

import java.util.ArrayList;

public class Deck {

    private final ArrayList<Card> mainDeck;
    private final ArrayList<Card> sideDeck;
    private String name;
    private boolean isActive;

    {
        mainDeck = new ArrayList<>();
        sideDeck = new ArrayList<>();
    }


    public Deck(String name) {
        setName(name);
    }

    public static boolean deckNameExists(String deckName, User owner) {
        for (Deck deck : owner.getDecks()) {
            if (deck.getName().equals(deckName)) {
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

    public ArrayList<Card> getMainDeck() {
        return mainDeck;
    }

    public void addCardToMainDeck(Card card) {
        mainDeck.add(card);
    }

    public void addCardsToMainDeck(ArrayList<Card> cards) {
        for (Card card : cards) {
            mainDeck.add(card);
        }
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

    public boolean isDeckValid() {
        //TODO checkForEachCardFrequency
        return true;
    }

    public boolean isMainDeckFull() {
        return mainDeck.size() == 60;
    }

    public boolean isSideDeckFull() {
        return sideDeck.size() == 15;
    }

    public int getCardCountInDeck(String cardName) {
        int count = 0;
        for (Card card : mainDeck) {
            if (card.getName().equals(cardName)) {
                count++;
            }
        }
        for (Card card : sideDeck) {
            if (card.getName().equals(cardName)) {
                count++;
            }
        }
        return count;
    }

    public boolean isCardInDeck(String cardName) {
        for (Card card : mainDeck) {
            if (card.getName().equals(cardName)) {
                return true;
            }
        }
        for (Card card : sideDeck) {
            if (card.getName().equals(cardName)) {
                return true;
            }
        }
        return false;
    }

    public void removeCardFromMainDeck(String cardName) {
        for (Card card : mainDeck) {
            if (card.getName().equals(cardName)) {
                mainDeck.remove(card);
                ArrayList<Card> cardsRemoved = new ArrayList<>();
                cardsRemoved.add(card);
                return;
            }
        }
    }

    public void removeCardFromSideDeck(String cardName) {
        for (Card card : sideDeck) {
            if (card.getName().equals(cardName)) {
                sideDeck.remove(card);
                ArrayList<Card> cardsRemoved = new ArrayList<>();
                cardsRemoved.add(card);
                return;
            }
        }
    }

    public boolean cardExistsInMainDeck(String cardName) {
        for (Card card : mainDeck) {
            if (card.getName().equals(cardName)) {
                return true;
            }
        }
        return false;
    }

    public boolean cardExistsInSideDeck(String cardName) {
        for (Card card : sideDeck) {
            if (card.getName().equals(cardName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String information = "main deck : " + mainDeck.size() + " , side deck : " + sideDeck.size() + " , ";
        if (isDeckValid()) {
            return information + "valid";
        } else {
            return information + "invalid";
        }
    }

    public Deck clone() {//todo use this except reference
        Deck copiedDeck = new Deck(this.getName());
        for (Card card : mainDeck) {
            copiedDeck.addCardToMainDeck(card.clone());
        }
        for (Card card : sideDeck) {
            copiedDeck.addCardToSideDeck(card.clone());
        }
        copiedDeck.setActive(isActive());
        return copiedDeck;
    }

}
