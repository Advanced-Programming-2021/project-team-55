package model.board;

import model.cards.Card;

public class Cell {
    private Card card;
    private static Cell selectedCell;

    public Card getCellCard() {
        return this.card;
    }

    public void addCardToCell(Card card) {
        setCard(card);
    }

    private void setCard(Card card) {
        this.card = card;
    }

    public void selectCell() {//select cell either needs entry or must be nonstatic
        selectedCell = this;
        setSelectedCell(this);
    }

    public static void deselectCell() {//better to be same as select cell or rename
        selectedCell = null;
    }

    public static void setSelectedCell(Cell selectedCell) {
        Cell.selectedCell = selectedCell;
    }

    public static Cell getSelectedCell() {
        return selectedCell;
    }
}
