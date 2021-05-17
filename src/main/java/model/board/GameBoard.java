package model.board;

import controller.gamephasescontrollers.GameController;
import model.cards.Card;
import model.cards.Deck;
import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterType;
import model.exceptions.GameException;
import view.gamephases.GameResponses;

import java.util.ArrayList;
import java.util.Collections;

public class GameBoard {

    private final Cell[] monsterCardZone;
    private final ArrayList<Cell> graveyard;
    private final Cell[] spellAndTrapCardZone;
    private final ArrayList<Cell> deckZone;
    private final ArrayList<Cell> handCards;
    private final Cell fieldZone;

    {
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

    public ArrayList<Cell> getHandCards() {
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

    public void addCardToMonsterCardZone(Card card, CardStatus cardStatus, GameController gameController) throws GameException {
        if (isMonsterCardZoneFull())
            throw new GameException(GameResponses.MONSTER_ZONE_IS_FULL.response);

        for (int i = 0; i < 5; i++) {
            if (monsterCardZone[i].isEmpty()) {
                monsterCardZone[i].addCardToCell(card);
                monsterCardZone[i].setCardStatus(cardStatus);
                gameController.changedPositionCells.add(monsterCardZone[i]);
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

    public boolean isHandCardEmpty() {
        return handCards.size() == 0;
    }

    public int getNumberOfMonstersOnMonsterCardZone() {
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            if (!monsterCardZone[i].isEmpty()) counter++;
        }
        return counter;
    }

    public int getNumberOfCardsOnHandCardZone() {
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            if (!handCards.get(i).isEmpty()) counter++;
        }
        return counter;
    }

    public Cell getMonsterByIndex(int number) {
        return monsterCardZone[number - 1];
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
            if (spellAndTrapCardZone[i].isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean isSpellAndTrapCardZoneEmpty() {
        for (int i = 0; i < 5; i++) {
            if (!spellAndTrapCardZone[i].isEmpty()) return false;
        }
        return true;
    }


    public void addCardToGraveyard(Card card) {
        graveyard.add(new Cell(card));
    }

    public void addCardToSpellAndTrapCardZone(Card card, CardStatus cardStatus, GameController gameController) throws GameException {
        if (isSpellAndTrapCardZoneFull())
            throw new GameException(GameResponses.SPELL_ZONE_IS_FULL.response);

        for (int i = 0; i < 5; i++) {
            if (spellAndTrapCardZone[i].isEmpty()) {
                spellAndTrapCardZone[i].addCardToCell(card);
                spellAndTrapCardZone[i].setCardStatus(cardStatus);
                gameController.changedPositionCells.add(spellAndTrapCardZone[i]);

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
        if (!fieldZone.isEmpty()) {
            addCardToGraveyard(fieldZone.getCellCard());
        }
        fieldZone.addCardToCell(card);

    }

    public boolean doesMonsterZoneHaveMonsters(int number) {
        int countMonsters = 0;
        for (Cell cell : monsterCardZone) {
            if (!cell.isEmpty()) {
                countMonsters++;
            }
        }
        return countMonsters >= number;
    }

    public boolean doesHandDeckHaveCard(int maxLevel, CardType cardType) {
        for (int i = 0; i < 5; i++) {
            try {
                handCards.get(i).getCellCard();
            } catch (Exception e) {
                continue;
            }
            if (handCards.get(i).isEmpty() || handCards.get(i).getCellCard().getCardKind() != Card.Kind.MONSTER)
                continue;
            Monster monster = (Monster) handCards.get(i).getCellCard();
            if (monster.getLevel() <= maxLevel && monster.getCardType() == cardType) return true;
        }
        return false;
    }

    public boolean doesMonsterZoneHaveOccupiedMonsters() {
        for (int i = 0; i < 5; i++) {
            if (!monsterCardZone[i].isEmpty() && monsterCardZone[i].getCardStatus() == CardStatus.DEFENSIVE_OCCUPIED ||
                    monsterCardZone[i].getCardStatus() == CardStatus.OFFENSIVE_OCCUPIED) {
                return true;
            }
        }
        return false;
    }

    public int getCountOccupiedMonstersInMonsterZone() {
        int count = 0;
        for (int i = 0; i < 5; i++) {
            if (!monsterCardZone[i].isEmpty() && monsterCardZone[i].getCardStatus() == CardStatus.DEFENSIVE_OCCUPIED ||
                    monsterCardZone[i].getCardStatus() == CardStatus.OFFENSIVE_OCCUPIED) {
                count++;
            }
        }
        return count;
    }

    public Cell getMonsterZoneCardByMonsterName(String cardName) {
        for (int i = 0; i < 5; i++) {
            if (!monsterCardZone[i].isEmpty() && monsterCardZone[i].getCellCard().getName().equals(cardName))
                return monsterCardZone[i];
        }
        return null;
    }

    public Cell getSpellZoneCardByName(String cardName) {
        for (int i = 0; i < 5; i++) {
            if (!spellAndTrapCardZone[i].isEmpty() && spellAndTrapCardZone[i].getCellCard().getName().equals(cardName))
                return spellAndTrapCardZone[i];
        }
        return null;
    }

    public ArrayList<Card> getGraveyardMonsters() {
        ArrayList<Card> graveyardMonsters = new ArrayList<>();
        for (Cell cell : graveyard) {
            if (cell.getCellCard().isMonster()) graveyardMonsters.add(cell.getCellCard());
        }
        return graveyardMonsters;
    }

    public ArrayList<Cell> getGraveyardMonstersCell() {
        ArrayList<Cell> graveyardMonstersCell = new ArrayList<>();
        for (Cell cell : graveyard) {
            try {
                if (cell.getCellCard().isMonster()) graveyardMonstersCell.add(cell);
            } catch (Exception ignored) {
            }
        }
        return graveyardMonstersCell;
    }

    public void addAllMonstersATK(int amount) {
        for (int i = 0; i < 5; i++) {
            try {
                ((Monster) monsterCardZone[i].getCellCard()).addATK(amount);
            } catch (Exception ignored) {
            }
        }
    }

    public ArrayList<Card> getAllMonstersFromAllFieldsWithType(MonsterType monsterType) {
        ArrayList<Card> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            try {
                Monster monster = (Monster) monsterCardZone[i].getCellCard();
                if (monster.getMonsterType() == monsterType) result.add(monster);
            } catch (Exception ignored) {
            }
        }
        for (Cell cell : handCards) {
            try {
                Monster monster = (Monster) cell.getCellCard();
                if (monster.getMonsterType() == monsterType) result.add(monster);
            } catch (Exception ignored) {
            }
        }
        for (Cell cell : graveyard) {
            try {
                Monster monster = (Monster) cell.getCellCard();
                if (monster.getMonsterType() == monsterType) result.add(monster);
            } catch (Exception ignored) {
            }
        }
        return result;
    }

    public ArrayList<Cell> getMonsterZoneCells() {
        ArrayList<Cell> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            result.add(monsterCardZone[i]);
        }
        return result;
    }

}
