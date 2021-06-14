package yugioh.model.board;

import javafx.scene.image.ImageView;
import yugioh.controller.menucontroller.GameMenuController;
import yugioh.model.cards.Card;
import yugioh.model.cards.Monster;
import yugioh.model.cards.monsters.CommandKnight;
import yugioh.model.cards.trapandspells.BlackPendant;
import yugioh.model.cards.trapandspells.MagnumShield;
import yugioh.model.cards.trapandspells.Swordofdarkdestruction;
import yugioh.model.cards.trapandspells.UnitedWeStand;
import yugioh.view.gamephases.Duel;


public class Cell {

    private static Cell selectedCell;
    public CardStatus cardStatus;
    private Card card;
    transient private ImageView cardImage;

    public Cell() {
    }

    public Cell(Card card) {

        setCard(card);
       GameMenuController.getGameMenuController().addEventForCardImage(this.cardImage,card);
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
        cardImage = Card.getCardImage(card, 90);
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


   public ImageView getCardImage() {
       return cardImage;
   }
}
