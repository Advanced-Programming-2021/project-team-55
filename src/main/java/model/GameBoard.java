package model;

import java.util.ArrayList;

public class GameBoard {
    private int[] areasNumber;
    private Cell[] monsterCardZone;
    private ArrayList<Integer> cellsNumbering;
    private ArrayList<Cell> graveyard;
    private Cell[] spellAndTrapCardZone;
    private ArrayList<Cell> deckZone;
    private ArrayList<Cell> handCards;
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
    public GameBoard(Deck deck){

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
    public void addCardToMonsterCardZone(Card card){

    }
    public void addCardToGraveyard(Card card){

    }
    public void addCardToSpellAndTrapCardZone(Card card){

    }
    public void addCardToHandDeck(Card card){

    }
    public void addCardToFieldZone(Card card){

    }
}
