package yugioh.server.controller.menucontroller;

import yugioh.server.model.UserHolder;
import yugioh.server.model.exceptions.MenuException;
import yugioh.server.model.User;
import yugioh.server.model.cards.Card;
import yugioh.server.model.cards.Deck;
import yugioh.server.model.cards.SpellAndTrap;
import yugioh.server.view.Menus.Menu;
import yugioh.server.view.Menus.MenuType;
import yugioh.server.view.Responses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static yugioh.server.model.cards.cardfeaturesenums.EffectiveTerm.LIMITED;

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

    public void createDeck(String deckName, UserHolder currentUser) throws MenuException {
        if (Deck.deckNameExists(deckName, currentUser.getUser())) {
            throw new MenuException("Error: deck with name " + deckName + " already exists");
        } else {
            currentUser.getUser().addDeck(new Deck(deckName));
        }

    }

    public void deleteDeck(String deckName, UserHolder currentUser) throws MenuException {
        Deck deck = currentUser.getUser().getDeckByName(deckName);
        if (deck == null) {
            throw new MenuException("Error: deck with name " + deckName + " does not exist");
        } else {
            currentUser.getUser().removeDeck(deck);
            currentUser.getUser().addCardsToInventory(deck.getMainDeck());
            currentUser.getUser().addCardsToInventory(deck.getSideDeck());
        }
    }

    public void activeDeck(String deckName, UserHolder currentUser) throws MenuException {
        Deck deck = currentUser.getUser().getDeckByName(deckName);
        if (deck == null) {
            throw new MenuException("Error: deck with name " + deckName + " does not exist");
        } else {
            currentUser.getUser().setActiveDeck(deck);
        }

    }

    public void addCardToDeck(String cardName, String deckName, boolean isSide, UserHolder currentUser) throws MenuException {
        Card card = Card.getCardByName(cardName);
        Deck deck = currentUser.getUser().getDeckByName(deckName);
        if (!currentUser.getUser().cardExistsInInventory(cardName)) {
            throw new MenuException("Error: card with name " + cardName + " does not exist");
        } else if (deck == null) {
            throw new MenuException("Error: deck with name " + deckName + " does not exist");
        } else if (deck.isMainDeckFull()) {
            throw new MenuException(Responses.MAIN_DECK_FULL.response);
        } else if (deck.isSideDeckFull()) {
            throw new MenuException(Responses.SIDE_DECK_FULL.response);
        } else if (deck.getCardCountInDeck(cardName) == 3) {
            throw new MenuException("Error: there are already three cards with name " + cardName + " in deck " + deckName);
        } else if (card.isSpellAndTrap() &&
                ((SpellAndTrap) card).getStatus() == LIMITED &&
                deck.isCardInDeck(cardName)) {
            throw new MenuException("Error: card " + cardName +
                    " frequency is limited by one and there is already a card with this name in deck " + deckName);
        } else {
            currentUser.getUser().removeCardFromInventory(card);
            if (isSide) {
                deck.addCardToSideDeck(card);
            } else {
                deck.addCardToMainDeck(card);
            }
        }
    }

    public void removeCardFromDeck(String cardName, String deckName, boolean isSide, UserHolder currentUser) throws MenuException {
        Deck deck = currentUser.getUser().getDeckByName(deckName);
        if (deck == null) {
            throw new MenuException("Error: deck with name " + deckName + " does not exist");
        } else if (isSide && !deck.cardExistsInSideDeck(cardName)) {
            throw new MenuException("Error: card with name " + cardName + " does not exist in side deck");
        } else if (!isSide & !deck.cardExistsInMainDeck(cardName)) {
            throw new MenuException("Error: card with name " + cardName + " does not exist in main deck");
        } else {
            if (isSide) {
                deck.removeCardFromSideDeck(cardName);
            } else {
                deck.removeCardFromMainDeck(cardName);
            }
        }
    }

    public ArrayList<Deck> getAllDecks(UserHolder currentUser) {
        ArrayList<Deck> decksToShow = currentUser.getUser().getDecks();
        Deck activeDeck = currentUser.getUser().getActiveDeck();
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

    public String getADeck(String deckName, boolean isSide, UserHolder currentUser) throws MenuException {
        for (Deck deck : currentUser.getUser().getDecks()) {
            if (deck.getName().equals(deckName)) {
                String deckInfo = "Deck: " + deckName + "\n";
                if (isSide) {
                    ArrayList<Card> monsters = Card.getMonstersSorted(deck.getSideDeck());
                    ArrayList<Card> spellAndTraps = Card.getMagicsSorted(deck.getSideDeck());
                    deckInfo += "Side deck:\nMonsters:";
                    for (Card card : monsters) {
                        deckInfo += "\n" + card;
                    }
                    deckInfo += "\nSpell and Traps:";
                    for (Card card : spellAndTraps) {
                        deckInfo += "\n" + card;
                    }
                } else {
                    ArrayList<Card> monsters = Card.getMonstersSorted(deck.getMainDeck());
                    ArrayList<Card> spellAndTraps = Card.getMagicsSorted(deck.getMainDeck());
                    deckInfo += "yugioh.server.Main deck:\nMonsters:";
                    for (Card card : monsters) {
                        deckInfo += "\n" + card;
                    }
                    deckInfo += "\nSpell and Traps:";
                    for (Card card : spellAndTraps) {
                        deckInfo += "\n" + card;
                    }
                }
                return deckInfo;
            }
        }
        throw new MenuException("Error: deck with name " + deckName + " does not exist");
    }

    public ArrayList<Card> getCards(UserHolder currentUser) {
        return Card.sortCards(currentUser.getUser().getCardsInventory());
    }

    @Override
    public void enterMenu(String menu) throws MenuException {
        throw new MenuException(Responses.MENU_NAVIGATION_NOT_POSSIBLE.response);
    }

    @Override
    public void exitMenu() {
        Menu.setCurrentMenu(MenuType.MAIN);
    }
}
