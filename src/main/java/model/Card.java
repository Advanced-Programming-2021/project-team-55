package model;

import java.util.ArrayList;

public class Card {
    protected static ArrayList<Card> cards;
    protected String name;
    protected String description;
    protected int number;
    protected int price;
    protected Counter counter;

    public Card(String name, String description, int price, int number) {
        setName(name);
        setDescription(description);
        setPrice(price);
        setNumber(number);
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

    public int getNumber() {
        return number;
    }

    protected void setNumber(int number) {
        this.number = number;
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

}
