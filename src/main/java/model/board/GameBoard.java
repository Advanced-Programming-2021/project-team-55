package model.board;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.exceptions.GameException;
import model.cards.Card;
import model.cards.Deck;
import view.gamephases.GameResponses;

import java.util.ArrayList;
import java.util.Collections;

public class GameBoard {

    public static final int[] areasNumber;
    private final Cell[] monsterCardZone;
    private final ArrayList<Cell> graveyard;
    private final Cell[] spellAndTrapCardZone;
    private final ArrayList<Cell> deckZone;
    private final ArrayList<Cell> handCards;
    private final Cell fieldZone;

    static {

        areasNumber = new int[5];
        areasNumber[0] = 2;
        areasNumber[1] = 1;
        areasNumber[2] = 3;
        areasNumber[3] = 0;
        areasNumber[4] = 4;
    }

    {
        //todo areas numbering should be handled here;
        monsterCardZone = new Cell[5];
        spellAndTrapCardZone = new Cell[5];
        for (int i = 0; i < 5; i++) {
            monsterCardZone[i] = new Cell();
            spellAndTrapCardZone[i] = new Cell();
        }
        deckZone = new ArrayList<>();
        handCards = new ArrayList<>();
        fieldZone = new Cell();
        fieldZone.setCardStatus(CardStatus.OCCUPIED);
        graveyard = new ArrayList<>();
    }

    public GameBoard(Deck deck) {
        ArrayList<Card> mainDeck = new ArrayList<>(deck.getMainDeck());
        Collections.shuffle(mainDeck);
        for (Card card : mainDeck) {
            Cell cell = new Cell();
            cell.addCardToCell(card);
            deckZone.add(cell);
        }
        addCardsToHandDeck(5);
    }

    public Cell[] getMonsterCardZone() {
        return monsterCardZone;
    }

    public Cell getFirstNotEmptyCell() {
        for (int i = 0; i < 5; i++) {
            if (!monsterCardZone[i].isEmpty()) return monsterCardZone[i];
        }
        return null;
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


    public Cell getFieldZone() {
        return fieldZone;
    }

    public boolean isCellInMonsterZone(Cell cell) {
        for (int i = 0; i < 5; i++) {
            if (monsterCardZone[i] == cell) {
                return true;
            }
        }
        return false;
    }
    public boolean isCellInSpellAndTrapZone(Cell cell) {
        for (int i = 0; i < 5; i++) {
            if (spellAndTrapCardZone[i] == cell) {
                return true;
            }
        }
        return false;
    }

    public void addCardToMonsterCardZone(Card card, CardStatus cardStatus) throws GameException {
        if (isMonsterCardZoneFull())
            throw new GameException(GameResponses.MONSTER_ZONE_IS_FULL.response);

        for (int i = 0; i < 5; i++) {
            if (monsterCardZone[i].isEmpty()) {
                monsterCardZone[i].addCardToCell(card);
                monsterCardZone[i].setCardStatus(cardStatus);
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

    public boolean isMonsterCardZoneEmpty() {
        for (int i = 0; i < 5; i++) {
            if (!monsterCardZone[i].isEmpty()) return false;
        }
        return true;
    }

    public int getNumberOfMonstersOnMonsterCardZone(){
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            if (!monsterCardZone[i].isEmpty()) counter++;
        }
        return counter;
    }

    public boolean isCellVisibleToOpponent(Cell cell) {
        for (Cell cell1 : monsterCardZone) {
            if (cell == cell1 && cell.getCardStatus() == CardStatus.DEFENSIVE_HIDDEN) {
                return false;
            }
        }
        for (Cell cell1 : spellAndTrapCardZone) {
            if (cell == cell1 && cell.getCardStatus() == CardStatus.HIDDEN) {
                return false;
            }
        }
        return true;
    }

    public boolean isSpellAndTrapCardZoneFull() {
        for (int i = 0; i < 5; i++) {
            if (spellAndTrapCardZone[i].isEmpty()){
                return false;
            }
        }
        return true;
    }



    public void addCardToGraveyard(Card card) {
        graveyard.add(new Cell(card));
    }

    public void addCardToSpellAndTrapCardZone(Card card,CardStatus cardStatus) throws GameException {
        if (isSpellAndTrapCardZoneFull())
            throw new GameException(GameResponses.SPELL_ZONE_IS_FULL.response);

        for (int i = 0; i < 5; i++) {
            if (spellAndTrapCardZone[i].isEmpty()) {
                spellAndTrapCardZone[i].addCardToCell(card);
                spellAndTrapCardZone[i].setCardStatus(cardStatus);
                return;
            }
        }
    }

    public void addCardsToHandDeck(int countCard) {
        for (int i = 0; i < countCard; i++) {
            Card card = deckZone.get(0).getCellCard();
            handCards.add(new Cell(card));
            deckZone.remove(0);
        }
    }

    public void addCardToHandDeck(String cardName) {
        for (int i = 0; i < deckZone.size(); i++) {
            if (deckZone.get(i).getCellCard().getName().equals(cardName)) {
                handCards.add(new Cell(deckZone.get(i).getCellCard()));
                deckZone.remove(i);
            }
        }
    }

    public void addCardToFieldZone(Card card) {
        if(!fieldZone.isEmpty()){
            addCardToGraveyard(fieldZone.getCellCard());
        }
        fieldZone.addCardToCell(card);

    }
    public boolean doesMonsterZoneHave3Monsters(){
        int countMonsters=0;
        for(Cell cell:monsterCardZone){
            if(!cell.isEmpty()){
                countMonsters++;
            }
        }
        return countMonsters>=3;
    }

    public boolean doesHandDeckHaveCard(int maxLevel, CardType cardType){
        for (int i = 0; i < 5; i++) {
            if (handCards.get(i).isEmpty() || handCards.get(i).getCellCard().getCardKind() != Card.Kind.MONSTER) continue;
            Monster monster = (Monster) handCards.get(i).getCellCard();
            if (monster.getLevel() <= maxLevel && monster.getCardType() == cardType) return true;
        }
        return false;
    }
}
