package controller.menucontroller;

import model.User;
import model.cards.Card;
import model.cards.Deck;
import model.cards.monsters.Babydragon;
import model.cards.monsters.CrabTurtle;
import model.cards.monsters.FeralImp;
import model.cards.trapandspells.AdvancedRitualArt;
import model.cards.trapandspells.SolemnWarning;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeckMenuControllerTest {
    DeckMenuController deckMenuController=DeckMenuController.getInstance();
    User user =new User("a","a","a");
    {
        User.setLoggedInUser(user);
    }
    @Test
    void getADeck() {
        Deck deck = new Deck("first deck");
        Babydragon babydragon=new Babydragon();
        CrabTurtle crabTurtle=new CrabTurtle();
        SolemnWarning solemnWarning=new SolemnWarning();
        FeralImp feralImp=new FeralImp();
        deck.addCardToMainDeck(crabTurtle);
        deck.addCardToSideDeck(babydragon);
        deck.addCardToSideDeck(feralImp);
        deck.addCardToSideDeck(solemnWarning);
        deck.addCardToMainDeck(solemnWarning);
        deck.addCardToMainDeck(feralImp);
        User.loggedInUser.addDeck(deck);
        try {
            String expected="Deck: first deck\nMain deck:\nMonsters:\n" + crabTurtle.toString() +"\n"+feralImp.toString()+
                    "\nSpell and Traps:\n" + solemnWarning.toString();
            assertEquals(expected,deckMenuController.getADeck("first deck", false));
            String expected2="Deck: first deck\nSide deck:\nMonsters:\n"+babydragon.toString()+"\n"+feralImp.toString()+"\n" +
                    "Spell and Traps:\n"+solemnWarning.toString();
            assertEquals(expected2,deckMenuController.getADeck("first deck",true));
        }
        catch (Exception e){}
    }


    @Test
    void getAllDecks() {
        Deck deck1=new Deck("first deck");
        Deck deck2=new Deck("Another deck");
        Deck deck3=new Deck("hello ");
        ArrayList<Deck>decks=new ArrayList<>();
        deck3.setActive(true);
        decks.add(deck3);
        decks.add(deck2);
        decks.add(deck1);
        user.addDeck(deck1);
        user.addDeck(deck2);
        user.addDeck(deck3);
        user.setActiveDeck(deck3);
        assertEquals(decks,deckMenuController.getAllDecks());

    }
}