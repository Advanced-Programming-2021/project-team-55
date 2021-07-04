package yugioh.model.board;

import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import yugioh.model.cards.Card;
import yugioh.model.cards.Monster;
import yugioh.model.cards.monsters.CommandKnight;
import yugioh.model.cards.trapandspells.BlackPendant;
import yugioh.model.cards.trapandspells.MagnumShield;
import yugioh.model.cards.trapandspells.Swordofdarkdestruction;
import yugioh.model.cards.trapandspells.UnitedWeStand;
import yugioh.view.gamephases.Duel;

import java.util.ArrayList;


public class Cell {

    private static Cell selectedCell;
    public CardStatus cardStatus;
    private Card card;
    private double xPosition;
    private double yPosition;
    private transient Rectangle cellRectangle;
    private static ArrayList<Cell>allCells=new ArrayList<>();
    public static void setSelectedCellByRectangle(Rectangle rectangle){
        for(Cell cell:allCells){
            if(cell.cellRectangle!=null&&cell.cellRectangle.equals(rectangle)){
                selectedCell=cell;
                return;
            }
        }
    }
    public static void setSelectedCellByImage(Paint paint){
        ImagePattern imagePattern=(ImagePattern) paint;
        for(Cell cell:allCells){
            if(!cell.isEmpty()&&(cell.getCellCard().getCardImagePattern().equals(imagePattern)||cell.getCellCard().
                    getCardBackImagePattern().equals(imagePattern))){
                selectedCell=cell;
            }
        }
    }

    public static ArrayList<Cell> getAllCells() {
        return allCells;
    }

    public Cell() {
        allCells.add(this);
    }

    public Cell(Card card) {
        setCard(card);
        allCells.add(this);
    }

    public static void deselectCell() {//better to be same as select cell or rename
        selectedCell.getCellRectangle().setEffect(null);
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
    public void setPosition(double x,double y){
        this.xPosition=x;
        this.yPosition=y;
    }

    public void setCellRectangle(Rectangle cellRectangle) {
        this.cellRectangle = cellRectangle;
    }

    public Rectangle getCellRectangle() {
        return cellRectangle;
    }

    public double getxPosition() {
        return xPosition;
    }

    public double getyPosition() {
        return yPosition;
    }
}
