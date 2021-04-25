package controller.menucontroller;

import exceptions.MenuException;
import model.User;
import model.cards.Card;
import model.cards.Deck;
import view.Menus.Menu;
import view.Menus.MenuType;

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
            User.loggedInUser.addDeck(new Deck(deckName));
        }

    }

    public void deleteDeck(String deckName) throws MenuException {
        Deck deck = User.loggedInUser.getDeckByName(deckName);
        if (deck == null) {
            throw new MenuException("deck with name " + deckName + " does not exist");
        } else {
            User.loggedInUser.removeDeck(deck);
            User.loggedInUser.addCardsToInventory(deck.getMainDeck());
            User.loggedInUser.addCardsToInventory(deck.getSideDeck());
        }
    }

    public void activeDeck(String deckName) throws MenuException {
        Deck deck = User.loggedInUser.getDeckByName(deckName);
        if (deck == null) {
            throw new MenuException("deck with name " + deckName + " does not exist");
        } else {
            User.loggedInUser.setActiveDeck(deck);
        }

    }

    public void addCardToDeck(String cardName, String deckName, boolean isSide) throws MenuException {
        Card card = Card.getCardByName(cardName);
        Deck deck = User.loggedInUser.getDeckByName(deckName);
        if (!User.loggedInUser.cardExistsInInventory(cardName)) {
            throw new MenuException("card with name " + cardName + " does not exist");
        } else if (deck == null) {
            throw new MenuException("deck with name " + deckName + " does not exist");
        } else if (deck.isMainDeckFull()) {
            throw new MenuException("main deck is full");
        } else if (deck.isSideDeckFull()) {
            throw new MenuException("side deck is full");
        } else if (deck.getCardCountInDeck(cardName) == 3) {
            throw new MenuException("there are already three cards with name " + cardName + " in deck " + deckName);
        } else {
            User.loggedInUser.removeCardFromInventory(card);
            if (isSide) {
                deck.addCardToSideDeck(card);
            } else {
                deck.addCardToMainDeck(card);
            }
        }
    }

    public void removeCardFromDeck(String cardName, String deckName, boolean isSide) throws MenuException {
        Deck deck = User.loggedInUser.getDeckByName(deckName);
        if (deck == null) {
            throw new MenuException("deck with name " + deckName + " does not exist");
        } else if (isSide && !deck.cardExistsInSideDeck(cardName)) {
            throw new MenuException("card with name " + cardName + " does not exist in side deck");
        } else if (!isSide & !deck.cardExistsInMainDeck(cardName)) {
            throw new MenuException("card with name " + cardName + " does not exist in main deck");
        } else {
            if (isSide) {
                deck.removeCardFromSideDeck(cardName);
            } else {
                deck.removeCardFromMainDeck(cardName);
            }
        }
    }

    public ArrayList<Deck> getAllDecks() {
        ArrayList<Deck> decksToShow = User.loggedInUser.getDecks();
        Deck activeDeck = User.loggedInUser.getActiveDeck();
        boolean activeDeckRemoved = false;
        if (activeDeck != null) {
            decksToShow.remove(activeDeck);
            activeDeckRemoved = true;
        }
        Collections.sort(decksToShow, new Comparator<Deck>() {
            @Override
            public int compare(Deck deck1, Deck deck2) {
                return deck1.getName().compareTo(deck2.getName());
            }
        });
        if (activeDeckRemoved) {
            decksToShow.add(0, activeDeck);
        }
        return decksToShow;
    }

    public String getADeck(String deckName, boolean isSide) throws MenuException {
        for (Deck deck : User.loggedInUser.getDecks()) {
            if (deck.getName().equals(deckName)) {
                String deckInfo = "Deck: " + deckName + "\n";
                if (isSide) {
                    ArrayList<Card> monsters = Card.getMonstersSorted(deck.getSideDeck());
                    ArrayList<Card> spellAndTraps = Card.getMagicsSorted(deck.getSideDeck());
                    deckInfo += "Side deck:\nMonsters:";
                    for (Card card : monsters) {
                        deckInfo += "\n"+card;
                    }
                    deckInfo += "\nSpell and Traps:";
                    for (Card card : spellAndTraps) {
                        deckInfo +="\n"+card ;
                    }
                } else {
                    ArrayList<Card> monsters = Card.getMonstersSorted(deck.getMainDeck());
                    ArrayList<Card> spellAndTraps = Card.getMagicsSorted(deck.getMainDeck());
                    deckInfo += "Main deck:\nMonsters:";
                    for (Card card : monsters) {
                        deckInfo += "\n"+card;
                    }
                    deckInfo += "\nSpell and Traps:";
                    for (Card card : spellAndTraps) {
                        deckInfo +="\n"+card ;
                    }
                }
                return deckInfo;
            }
        }
        throw new MenuException("deck with name " + deckName + " does not exist");
    }

    public ArrayList<Card> getCards() {
        return Card.sortCards(User.loggedInUser.getCardsInventory());
    }

    @Override
    public void enterMenu(String menu) throws MenuException {
        throw new MenuException("menu navigation is not possible");
    }

    @Override
    public void exitMenu() {
        Menu.setCurrentMenu(MenuType.MAIN);
    }
}
