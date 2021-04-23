package model.board;

import model.cards.Card;
import model.cards.Deck;

import java.util.ArrayList;

public class GameBoard {
    private final int[] areasNumber;
    private final Cell[] monsterCardZone;
    private final ArrayList<Integer> cellsNumbering;
    private final ArrayList<Cell> graveyard;
    private final Cell[] spellAndTrapCardZone;
    private final ArrayList<Cell> deckZone;
    private final ArrayList<Cell> handCards;
    private Cell fieldZone;

    {
        areasNumber = new int[5];
        monsterCardZone = new Cell[5];
        cellsNumbering = new ArrayList<>();
        graveyard = new ArrayList<>();
        spellAndTrapCardZone = new Cell[5];
        deckZone = new ArrayList<>();
        handCards = new ArrayList<>();
    }

    public GameBoard(Deck deck) {

    }

    public Cell[] getMonsterCardZone() {
        return monsterCardZone;
    }

    public ArrayList<Cell> getGraveyard() {
        return graveyard;
    }

    public Cell[] getSpellAndTrapCardZone() {
        return spellAndTrapCardZone;
    }

    public ArrayList<Cell> getDeckZone() {
        return deckZone;
    }

    public ArrayList<Cell> getHandCards() {//handDeck changed to handCards
        return handCards;
    }

    //the next methods should get Card or Cell?---------------------
    public void addCardToMonsterCardZone(Card card) {

    }

    public void addCardToGraveyard(Card card) {

    }

    public void addCardToSpellAndTrapCardZone(Card card) {

    }

    public void addCardToHandDeck(Card card) {

    }

    public void addCardToFieldZone(Card card) {

    }
}
