package model.board;

import model.cards.Card;
import model.cards.Monster;

public class Cell {
    private static Cell selectedCell;

    private Card card;

    private CardPosition cardPosition;

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

    public void setCard(Card card) {
        this.card = card;
        if(card instanceof Monster){
            cardPosition=CardPosition.DEFENSIVE_HIDDEN;
        }
        else{
            cardPosition=CardPosition.HIDDEN;
        }
    }

    public CardPosition getCardPosition() {
        return cardPosition;
    }

    public void selectCell() {//select cell either needs entry or must be nonstatic
        selectedCell = this;
        setSelectedCell(this);
    }
}
