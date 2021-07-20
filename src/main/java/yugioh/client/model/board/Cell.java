package yugioh.client.model.board;

import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import yugioh.client.model.User;
import yugioh.client.model.cards.Card;
import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.monsters.CommandKnight;
import yugioh.client.model.cards.trapandspells.*;
import yugioh.client.view.NetAdapter;
import yugioh.client.view.gamephases.CardActionsMenu;
import yugioh.client.view.gamephases.Duel;

import java.util.ArrayList;

import static javafx.scene.paint.Color.GREEN;


public class Cell {

    private static final ArrayList<Cell> allCells = new ArrayList<>();
    public static Cell selectedCell;
    public CardStatus cardStatus;
    public transient boolean isEventSet = false;
    private Card card;
    private double xPosition;
    private double yPosition;
    private transient Rectangle cellRectangle;
    private transient Label cellInfo = new Label();

    public Cell() {
        allCells.add(this);
    }

    public Cell(Card card) {
        setCard(card);
        allCells.add(this);
    }

    public static void setSelectedCellByRectangle(Rectangle rectangle) {
        selectedCell = getSelectedCellByRectangle(rectangle);
        NetAdapter.sendForwardRequestForGame("select " + Duel.getGameController().currentTurnPlayer.getGameBoard().getCellInfo(selectedCell));
        //todo handle selecting opponent;
    }

    public static Cell getSelectedCellByRectangle(Rectangle rectangle) {
        for (Cell cell : allCells) {
            if (cell.cellRectangle != null && cell.cellRectangle.equals(rectangle)) {
                return cell;
            }
        }
        return null;
    }

    public static void setSelectedCellByImage(Paint paint) {
        ImagePattern imagePattern = (ImagePattern) paint;
        for (Cell cell : allCells) {
            if (!cell.isEmpty() && (cell.getCellCard().getCardImagePattern().equals(imagePattern) || cell.getCellCard().
                    getCardBackImagePattern().equals(imagePattern))) {
                selectedCell = cell;
            }
        }
    }

    public static ArrayList<Cell> getAllCells() {
        return allCells;
    }

    public static void deselectCell(boolean sentToOtherPlayer) {   //better to be same as select cell or rename
        try {
            if (sentToOtherPlayer)
            NetAdapter.sendForwardRequestForGame("select -d");

            selectedCell.getCellRectangle().setEffect(null);
        } catch (Exception e) {
        }
        selectedCell = null;
    }

    public static Cell getSelectedCell() {
        return selectedCell;
    }

    public static void setSelectedCell(Cell selectedCell) {
        Cell.selectedCell = selectedCell;
        DropShadow selectEffect = new DropShadow(BlurType.values()[1],
                GREEN, 10, 2.0f, 0, 0);
        selectEffect.setBlurType(BlurType.ONE_PASS_BOX);
        selectedCell.getCellRectangle().setEffect(selectEffect);
        NetAdapter.sendForwardRequestForGame("select " + Duel.getGameController().currentTurnPlayer.getGameBoard().getCellInfo(selectedCell));
        //todo handle select opponent;
    }

    public static void justSetSelectedCell(Cell cell) {
        selectedCell = cell;
    }

    public Label getCellInfo() {
        return cellInfo;
    }

    public void setCellInfo(Label cellInfo) {
        this.cellInfo = cellInfo;
    }

    public Card getCellCard() {
        return this.card;
    }

    public void addCardToCell(Card card) {
        setCard(card);
    }

    public void removeCardFromCell(GameBoard gameBoard,boolean sendToOtherPlayer) {
        if(sendToOtherPlayer)
        NetAdapter.sendForwardRequestForGame("remove card from cell "+gameBoard.equals(Duel.getGameController().currentTurnPlayer.getGameBoard()));
        CommandKnight.deActivateEffect(this);
        BlackPendant.deActivateEffect(this);
        UnitedWeStand.deActivateEffect(this);
        Forest.deActivateEffect(Duel.getGameController());
        Umiiruka.deActivateEffect(this);
        Swordofdarkdestruction.deActivateEffect(this);
        MagnumShield.deActivateEffect(this);
        CardActionsMenu.removeRectangleEventHandlers(cellRectangle);
        CardActionsMenu.removeEventHandlers();

//        Timeline timeline=new Timeline(new KeyFrame(Duration.seconds(0.5),
//                event->cellRectangle.setFill(null)));
//        timeline.play();
//        cellRectangle.setFill(null);
        try {
            cellRectangle.rotateProperty().set(gameBoard.rectanglesInitRotateValues.get(cellRectangle));

        }catch (Exception e){}
        gameBoard.addCardToGraveyard(this);
//        Rectangle tempRectangle = new Rectangle();
//        tempRectangle.setLayoutX(cellRectangle.getLayoutX());
//        tempRectangle.setLayoutY(cellRectangle.getLayoutY());
//        tempRectangle.setWidth(cellRectangle.getWidth());
//        tempRectangle.setHeight(cellRectangle.getHeight());
//        tempRectangle.setFill(cellRectangle.getFill());
//        tempRectangle.rotateProperty().set(0);
//        GameMenuController.getGameMenuController().gameBoardPane.getChildren().add(tempRectangle);
//        Duel.getGameController().currentTurnPlayer.getGameBoard().setFadeTransition(tempRectangle, 1, 0);
//       Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2),
//               event -> GameMenuController.getGameMenuController().gameBoardPane.getChildren().remove(tempRectangle)));
//       timeline.play();
        if (Cell.getSelectedCell() == this) selectedCell = null;

        cellInfo.setText("");
            cellRectangle.setFill(null);
            cellRectangle.setStrokeWidth(0);

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

    public void changeCardStatus(CardStatus cardStatus, Rectangle rectangle) {

    }

    public void setPosition(double x, double y) {
        this.xPosition = x;
        this.yPosition = y;
    }

    public Rectangle getCellRectangle() {
        return cellRectangle;
    }

    public void setCellRectangle(Rectangle cellRectangle) {
        this.cellRectangle = cellRectangle;
    }

    public double getxPosition() {
        return xPosition;
    }

    public double getyPosition() {
        return yPosition;
    }

    public void setEventIsSet() {
        isEventSet = true;
    }
}
