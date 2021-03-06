package model.board;

import model.cards.Card;
import model.cards.Monster;
import model.cards.monsters.CommandKnight;
import model.cards.trapandspells.BlackPendant;
import model.cards.trapandspells.MagnumShield;
import model.cards.trapandspells.Swordofdarkdestruction;
import model.cards.trapandspells.UnitedWeStand;
import view.gamephases.Duel;


public class Cell {

    private static Cell selectedCell;
    public CardStatus cardStatus;
    private Card card;

    public Cell() {
    }

    public Cell(Card card) {
        this.card = card;
    }


    public static void deselectCell() {
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

    public void removeCardFromCell(GameBoard gameBoard) {
        CommandKnight.deActivateEffect(this);
        BlackPendant.deActivateEffect(this);
        UnitedWeStand.deActivateEffect(this);
        Swordofdarkdestruction.deActivateEffect(this);
        MagnumShield.deActivateEffect(this);
        if (Cell.getSelectedCell() == this) selectedCell = null;
        gameBoard.addCardToGraveyard(this.card);
        this.card = null;
        this.cardStatus = null;
        Duel.getGameController().changedPositionCells.remove(this);
    }

    public void setCard(Card card) {
        this.card = card;
        if (card instanceof Monster) {
            cardStatus = CardStatus.DEFENSIVE_HIDDEN;
        } else {
            cardStatus = CardStatus.HIDDEN;
        }
    }

    public boolean isEmpty() {
        return card == null;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        CommandKnight.handleEffect(cardStatus, this);
        this.cardStatus = cardStatus;
        CommandKnight.deActivateEffect(this);
    }


}
