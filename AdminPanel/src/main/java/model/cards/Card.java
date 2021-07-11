package model.cards;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import model.cards.cardfeaturesenums.SpellOrTrap;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Card {

    public static ArrayList<Card> allCards;

    static {
        allCards = new ArrayList<>();
    }

    public boolean isCustom = false;
    protected String name;
    protected String description;
    protected int price;
    protected SpellOrTrap magicType;

    protected Kind cardKind;

    protected String image;

    transient protected ImageView cardImage;
    //todo add forbid field to server/client Card class
    protected boolean isForbid;


    public Card(String name, String description, int price, Kind cardKind, SpellOrTrap magicType) {
        setName(name);
        setDescription(description);
        setPrice(price);
        this.cardKind = cardKind;
        this.magicType = magicType;
        if (getCardByName(name) == null) {
            allCards.add(this);
        }
    }

    public static ArrayList<Card> getCards() {
        return allCards;
    }

    public static void setCards(ArrayList<Card> cards) {
        Card.allCards = cards;
    }

    public static Card getCardByName(String name) {
        for (Card card : allCards) {
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



    public static ImageView getCardImage(Card card, int width) {
        String name;
        if (card == null) name = "src\\resources\\Sample\\PNG\\cardsImages\\Unknown.jpg";
        else name = card.image;
        File imageFile = new File(name);
        if (!imageFile.exists()) {
            System.out.println(name);
        }
        ImageView imageView = new ImageView(new Image(imageFile.toURI().toString()));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(width);
        return imageView;
    }

    public static Card getCardNameByImage(String image) {
        for (Card card : allCards) {
            if (card.image.equals(image)) {
                return card;
            }
        }
        return null;
    }

    public static Card getArrayListCard(String cardName, ArrayList<Card> cards) {
        for (Card card : cards) {
            if (card.getName().equals(cardName)) return card;
        }
        return null;
    }

    public static void addCardToAllCards(Card card) {
        if (getCardByName(card.name) == null) {
            allCards.add(card);
        }
    }

    public String getImage() {
        return image;
    }

    public void setImage(ImageView imageView) {
        this.cardImage = imageView;
    }

    public String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    protected void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + ": " + description;
    }

    public boolean isMonster() {
        return cardKind == Kind.MONSTER;
    }

    public boolean isSpell() {
        if (cardKind == Kind.MONSTER) {
            return false;
        } else {
            return magicType == SpellOrTrap.SPELL;
        }
    }

    public boolean isSpellAndTrap() {
        return cardKind != Kind.MONSTER;
    }

    public Kind getCardKind() {
        return cardKind;
    }


    public void setIsCustom() {
        isCustom = true;
    }

    public void setForbid(boolean forbid) {
        isForbid = forbid;
    }

    public boolean isForbid() {
        return isForbid;
    }

    public enum Kind {MONSTER, MAGIC}
}
