package yugioh.client.controller.gamephasescontrollers;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import yugioh.client.model.board.Cell;
import yugioh.client.model.exceptions.GameException;
import yugioh.client.view.NetAdapter;
import yugioh.client.view.gamephases.CardSelectionMenu;
import yugioh.client.view.gamephases.Duel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CardSelectionMenuController implements Initializable {
    public static ArrayList<Cell> cardsToShow = new ArrayList<>();

    static {

    }

    public ScrollPane scrollBar;

    public static void setCardsToShow(ArrayList<Cell> cardsToShow) {
        CardSelectionMenuController.cardsToShow = cardsToShow;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GridPane cardsPane = new GridPane();
        cardsPane.setHgap(10);
        cardsPane.setVgap(10);
        cardsPane.setPadding(new Insets(10, 10, 10, 10));
        for (Cell cell : cardsToShow) {
            Rectangle rectangle = cell.getCellRectangle();
            rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Cell.setSelectedCell(Cell.getSelectedCellByRectangle(rectangle));
                    GameController gameController = Duel.getGameController();
                    try {
                        gameController.getMainPhase1Controller().specialSummon(gameController);
                    } catch (GameException e) {
                        e.printStackTrace();
                    }
                    CardSelectionMenu.cardSelectionStage.close();
                    Cell.deselectCell(true);
                    gameController.changeTurn(true, true);
                    NetAdapter.sendForwardRequestForGame("change turn true true");
                }
            });
            cardsPane.getChildren().add(cell.getCellRectangle());
        }
        scrollBar.contentProperty().set(cardsPane);
    }
}
