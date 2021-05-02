package model.board;

import exceptions.MenuException;
import model.cards.Card;
import model.cards.Deck;
import view.gamephases.Duel;

import java.util.ArrayList;
import java.util.Collections;

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
        //todo areas numbering should be handled here;
        monsterCardZone = new Cell[5];
        spellAndTrapCardZone = new Cell[5];
        for (int i = 0; i < 5; i++) {
            monsterCardZone[i] = new Cell();
            spellAndTrapCardZone[i] = new Cell();
        }
        cellsNumbering = new ArrayList<>();
        graveyard = new ArrayList<>();
        deckZone = new ArrayList<>();
        handCards = new ArrayList<>();
    }

    public GameBoard(Deck deck) {
        ArrayList<Card> mainDeck = new ArrayList<>(deck.getMainDeck());
        Collections.shuffle(mainDeck);
        for (Card card : mainDeck) {
            Cell cell = new Cell();
            cell.addCardToCell(card);
            deckZone.add(cell);
        }
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

    public int[] getAreasNumber() {
        return areasNumber;
    }

    public Cell getFieldZone() {
        return fieldZone;
    }

    public void addCardToMonsterCardZone(Card card) throws MenuException {
        if (isMonsterCardZoneFull())
            throw new MenuException("Error:monster card zone is full");

        for (int i = 0; i < 5; i++) {
            if (monsterCardZone[i].isEmpty()) {
                monsterCardZone[i].setCard(card);
                monsterCardZone[i].setCardStatus(CardStatus.OFFENSIVE_OCCUPIED);
                return;
            }
        }
    }

    private boolean isMonsterCardZoneFull() {
        for (int i = 0; i < 5; i++) {
            if (monsterCardZone[i].isEmpty()) return false;
        }
        return true;
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
