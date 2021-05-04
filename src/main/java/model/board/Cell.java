package model.board;

import model.cards.Card;
import model.cards.Monster;

import static model.board.GameBoard.addCardToGraveyard;

public class Cell {

    private static Cell selectedCell;

    private Card card;

    private CardStatus cardStatus;

    public Cell() {
    }

    public Cell(Card card) {
        this.card = card;
    }


    public static void deselectCell() {//better to be same as select cell or rename
        selectedCell = null;
    }

    public static Cell getSelectedCell() {
        return selectedCell;
    }

    public static void setSelectedCell(Cell selectedCell) {
        Cell.selectedCell = selectedCell;
    }

    public Card getCellCard() {
        return this.card;
    }

    public void addCardToCell(Card card) {
        setCard(card);
    }

    public static void removeCardFromCell(Cell cell) {
        addCardToGraveyard(cell.card);
        cell.card = null;
        cell.cardStatus = null;
    }

    public void setCard(Card card) {
        this.card = card;
        if (card instanceof Monster) {
            cardStatus = CardStatus.DEFENSIVE_HIDDEN;
        } else {
            cardStatus = CardStatus.HIDDEN;
        }
    }

    public CardStatus getCardPosition() {
        return cardStatus;
    }

    public void selectCell() {//select cell either needs entry or must be nonstatic
        selectedCell = this;
        setSelectedCell(this);
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public boolean isEmpty() {
        return card == null;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

}
