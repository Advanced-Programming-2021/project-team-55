package model.cards;

import model.Counter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Card {

    protected static ArrayList<Card> cards;
    protected String name;
    protected String description;
    protected int price;
    protected Counter counter;

    public Card(String name, String description, int price) {
        setName(name);
        setDescription(description);
        setPrice(price);
        cards.add(this);
    }

    public static ArrayList<Card> getCards() {
        return cards;
    }

    public static Card getCardByName(String name) {
        for (Card card : cards) {
            if (card.getName().equals(name)) {
                return card;
            }
        }
        return null;
    }

    public static ArrayList<Card> sortCards(ArrayList<Card> cardsToBeSorted) {
        Collections.sort(cardsToBeSorted, new Comparator<Card>() {
            @Override
            public int compare(Card card1, Card card2) {
                return card1.name.compareTo(card2.name);
            }
        });
        return cardsToBeSorted;
    }

    public static ArrayList<Card> getMonstersSorted(ArrayList<Card> cards) {
        ArrayList<Card> monsters = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof Monster) {
                monsters.add(card);
            }
        }
        Collections.sort(monsters, new Comparator<Card>() {
            @Override
            public int compare(Card card1, Card card2) {
                return card1.name.compareTo(card2.name);
            }
        });
        return monsters;
    }

    public static ArrayList<Card> getMagicsSorted(ArrayList<Card> cards) {
        ArrayList<Card> spellAndTraps = new ArrayList<>();
        for (Card card : cards) {
            if (!(card instanceof Monster)) {
                spellAndTraps.add(card);
            }
        }
        Collections.sort(spellAndTraps, new Comparator<Card>() {
            @Override
            public int compare(Card card1, Card card2) {
                return card1.name.compareTo(card2.name);
            }
        });
        return spellAndTraps;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    protected void setPrice(int price) {
        this.price = price;
    }

    public Counter getCounter() {
        return counter;
    }

    protected void setCounter(Counter counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return name + ": " + description;
    }

}
