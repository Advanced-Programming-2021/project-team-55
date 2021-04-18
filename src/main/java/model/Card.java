package model;

import java.util.ArrayList;

public class Card {
    protected String cardName, description;//Changed name to cardName due to similarity problem
    protected int number, price;
    protected Counter counter;
    protected static ArrayList<Card> allCards;

    static {
        allCards=new ArrayList<>();
    }

    protected Card(String cardName, String description, int number, int price) {
        setCardName(cardName);
        setDescription(description);
        setNumber(number);
        setPrice(price);
        allCards.add(this);
    }

    protected void setCardName(String cardName) {
        this.cardName = cardName;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected void setPrice(int price) {
        this.price = price;
    }

    protected void setNumber(int number) {
        this.number = number;
    }

    protected void setCounter(Counter counter) {
        this.counter = counter;
    }

    public String getCardName() {
        return cardName;
    }

    public String getDescription() {
        return description;
    }

    public int getNumber() {
        return number;
    }

    public int getPrice() {
        return price;
    }

    public Counter getCounter() {
        return counter;
    }

    public static ArrayList<Card> getAllCards() {
        return allCards;
    }

    public static Card getCardByCardName(String cardName) {
        for (Card card : allCards
        ) {
            if (cardName.equals(card.cardName))
                return card;
        }
        return null;
    }
}
