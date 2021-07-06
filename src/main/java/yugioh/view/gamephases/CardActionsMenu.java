package yugioh.view.gamephases;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.controller.gamephasescontrollers.MainPhasesController;
import yugioh.controller.menucontroller.GameMenuController;
import yugioh.model.Player;
import yugioh.model.board.CardStatus;
import yugioh.model.board.Cell;
import yugioh.model.exceptions.GameException;
import yugioh.view.menus.PopUpWindow;
import yugioh.view.menus.Toast;
import yugioh.view.menus.WelcomeMenu;

import java.util.ArrayList;

public class CardActionsMenu implements MainPhasesController {
    private static Stage actionsStage = new Stage();

    private static double xImage;
    private static double yImage;
    private static Button actionButton;
    private static Rectangle imageRectangle;
    private static ArrayList<String> handMonsterActions = new ArrayList<>();
    private static ArrayList<String> handSpellAndTrapActions = new ArrayList<>();
    private static ArrayList<String> boardMonsterActions = new ArrayList<>();
    private static ArrayList<String> boardSpellAndTrapActions = new ArrayList<>();

    private static ArrayList<String> thisActions = new ArrayList<>();
    private static Pane gamePane;
    private static int place = 6;
    private static GameController gameController;

    private static double lastMousePositionX = 0;
    private static double lastMousePositionY = 0;

    private static ImageView activeSword;
    private static Rectangle activeRectangle;


    static {
        actionsStage.initOwner(WelcomeMenu.stage);
        actionsStage.initModality(Modality.NONE);
        actionsStage.initStyle(StageStyle.UNDECORATED);
        {
            handMonsterActions.add("set");
            handMonsterActions.add("summon");

            handSpellAndTrapActions.add("set");
            handSpellAndTrapActions.add("activate");

            boardMonsterActions.add("change position");
            boardMonsterActions.add("flip summon");

            boardSpellAndTrapActions.add("activate");

        }
    }

    public static void execute(Rectangle rectangle, GameController controller) throws Exception {
        imageRectangle = rectangle;
        gameController = controller;
        start();
    }

    public static void start() throws Exception {
        Cell selectedCell = Cell.getSelectedCell();
        if (gameController.currentTurnPlayer.getGameBoard().isCellInHandZone(selectedCell)) {
            if (gameController.currentPhase.equals(GamePhase.MAIN1) || gameController.currentPhase.equals(GamePhase.MAIN2)) {
                openMainPhaseActions();
            }
        } else {
            if (gameController.currentPhase.equals(GamePhase.BATTLE)) {
                openBattlePhaseActionsForCardsInBoard();
            } else if (gameController.currentPhase.equals(GamePhase.MAIN1) || gameController.currentPhase.equals(GamePhase.MAIN2)) {
                openMainPhaseActionsForCardsInBoard();
            }
            //todo : add actions of cards in monster zone and spell zone
        }

    }

    private static void openBattlePhaseActionsForCardsInBoard() {

    }

    private static void openMainPhaseActionsForCardsInBoard() {
        if (Cell.getSelectedCell().getCellCard().isMonster()) {
            thisActions = boardMonsterActions;
        } else {
            thisActions = boardSpellAndTrapActions;
        }
        actionButton = new Button();
        actionButton.setMinWidth(70);
        Pane pane = new Pane();
        pane.getChildren().add(actionButton);
        pane.setMaxWidth(actionButton.getMaxWidth());
        pane.setMaxHeight(30);
        actionButton.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.equals("flip summon")) {
                    Cell selectedCell = Cell.getSelectedCell();
                    if (selectedCell.getCardStatus() != CardStatus.DEFENSIVE_HIDDEN) {
                        change();
                    } else if (gameController.getChangedPositionCells().contains(selectedCell)) {
                        actionButton.setDisable(true);
                    } else {
                        actionButton.setDisable(false);
                    }
                } else if (t1.equals("change position")) {
                    if (Cell.getSelectedCell().getCardStatus() == CardStatus.DEFENSIVE_HIDDEN) {
                        change();
                    } else if (gameController.changedPositionCells.contains(Cell.getSelectedCell())) {
                        actionButton.setDisable(true);
                    } else {
                        actionButton.setDisable(false);
                    }
                } else if (t1.equals("activate")) {
                    //todo: activate spell
                }
            }
        });
        actionButton.setText(thisActions.get(0));
        actionButton.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (actionButton.getText().equals("flip summon")) {
                    handleFlipSummon();
                } else if (actionButton.getText().equals("change position")) {
                    handleChangePosition();
                }
            }
        });
        Scene scene = WelcomeMenu.createScene(pane);
        actionsStage.setX(xImage);
        actionsStage.setY(yImage);
        actionsStage.setScene(scene);
        actionsStage.show();
    }

    private static void handleChangePosition() {
        try {
            if (Cell.getSelectedCell().getCardStatus() == CardStatus.DEFENSIVE_OCCUPIED)
                new CardActionsMenu().setPosition("attack", gameController);
            else new CardActionsMenu().setPosition("defense", gameController);
        } catch (GameException e) {
            e.printStackTrace();
            //todo show an error box
        }
        actionsStage.close();
    }

    private static void handleFlipSummon() {
        try {
            new CardActionsMenu().flipSummon(gameController);
        } catch (GameException e) {
            e.printStackTrace();
            //todo show an error box
        }
        actionsStage.close();
    }

    public static void makeSwordEventForSummonedMonsters(Rectangle rectangle) {
        Player currentPlayer = Duel.getGameController().getCurrentTurnPlayer();
        rectangle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (!currentPlayer.equals(Duel.getGameController().getCurrentTurnPlayer())) return;
            if (Duel.getGameController().getCurrentPhase() != GamePhase.BATTLE) return;
            if (activeRectangle == rectangle && activeSword != null) {
                removeSword();
                return;
            } else if (activeSword != null) {
                removeSword();
            }
            rectangle.requestFocus();
            ImageView sword = new ImageView(new Image("/yugioh/PNG/icon/sword.png"));
            GameMenuController.getGameMenuController().gameBoardPane.getChildren().add(sword);
            sword.setX(rectangle.getLayoutX() + 14);
            sword.setY(rectangle.getLayoutY() + 10);
            activeSword = sword;
            activeRectangle = rectangle;
            GameMenuController.getGameMenuController().gameBoardPane.addEventHandler(MouseEvent.MOUSE_MOVED, event2 -> {
                double constant = 0;
                if ((gamePane.rotateProperty().get() % 360) > 179) constant = 180;
                sword.setRotate(constant - (Math.toDegrees(Math.atan((event2.getSceneX() - 400 - rectangle.getLayoutX()) / (event2.getSceneY() - rectangle.getLayoutY() - constant)))));
                event2.consume();
            });
            GameMenuController.getGameMenuController().selectCard(rectangle);
            Cell[] monsterCardZone = gameController.getCurrentTurnOpponentPlayer().getGameBoard().getMonsterCardZone();
            for (int i = 0; i < monsterCardZone.length; i++) {
                Cell cell = monsterCardZone[i];
                int finalI = i;
                cell.getCellRectangle().addEventHandler(MouseEvent.MOUSE_CLICKED, event3 -> {
                    try {
                        GameMenuController.getGameMenuController().selectCard(rectangle);
                        String result = Duel.getGameController().getBattlePhaseController().attack(finalI);
                        new PopUpWindow(result).start(WelcomeMenu.getStage());
                    } catch (Exception e) {
                        try {
                            new PopUpWindow(e.getMessage()).start(WelcomeMenu.getStage());
                        } catch (Exception ignored) {
                        }
                    }
                    event3.consume();
                });
            }
            event.consume();
        });
    }

    public static void removeSword() {
        if (activeSword != null)
            GameMenuController.getGameMenuController().gameBoardPane.getChildren().remove(activeSword);
        activeSword = null;
        activeRectangle = null;
    }

    public static void close() {
        if (actionsStage != null)
            actionsStage.close();
    }

    public static void setCoordinates(double x, double y) {
        xImage = x;
        yImage = y;
    }

    public static void change() {
        int counter = 0;
        for (String action : thisActions) {
            if (action.equals(actionButton.getText())) {
                if (counter == thisActions.size() - 1) {
                    counter = -1;
                }
                actionButton.setText(thisActions.get(counter + 1));
                return;
            }
            counter++;
        }
    }

    public static void handleSet() {
        try {
            new CardActionsMenu().setCard(gameController);
        } catch (GameException e) {
            e.printStackTrace();
            //todo show an error box
        }
        actionsStage.close();
    }

    public static void setGamePane(Pane pane) {
        gamePane = pane;
    }

    public static double getLastMousePositionX() {
        return lastMousePositionX;
    }

    public static void setLastMousePositionX(double lastMousePositionX) {
        CardActionsMenu.lastMousePositionX = lastMousePositionX;
    }

    public static double getLastMousePositionY() {
        return lastMousePositionY;
    }

    public static void setLastMousePositionY(double lastMousePositionY) {
        CardActionsMenu.lastMousePositionY = lastMousePositionY;
    }

    public static void openMainPhaseActions() {
        if (Cell.getSelectedCell().getCellCard().isMonster()) {
            thisActions = handMonsterActions;
        } else {
            thisActions = handSpellAndTrapActions;

        }
        actionButton = new Button();
        actionButton.setMinWidth(70);
        Pane pane = new Pane();
        pane.getChildren().add(actionButton);
        pane.setMaxWidth(70);
        pane.setMaxHeight(30);
        actionButton.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.equals("set")) {
                    if (Cell.getSelectedCell().getCellCard().isMonster() && (gameController.doPlayerSetOrSummonedThisTurn()
                            || gameController.currentTurnPlayer.getGameBoard().isMonsterZoneFull())) {
                        actionButton.setDisable(true);
                    } else {
                        actionButton.setDisable(false);
                    }
                } else if (t1.equals("summon")) {
                    if (gameController.doPlayerSetOrSummonedThisTurn() || gameController.currentTurnPlayer.getGameBoard().
                            isMonsterZoneFull() || !new CardActionsMenu().isSummonable(Cell.getSelectedCell(), gameController)) {
                        actionButton.setDisable(true);
                    } else {
                        actionButton.setDisable(false);
                    }
                } else if (t1.equals("activate")) {
                    //todo: activate spell
                }
            }
        });
        actionButton.setText(thisActions.get(0));
        actionButton.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (actionButton.getText().equals("set")) {
                    handleSet();
                } else if (actionButton.getText().equals("summon")) {
                    handleSummon();
                }
            }
        });
        Scene scene = WelcomeMenu.createScene(pane);
        actionsStage.setX(xImage);
        actionsStage.setY(yImage);
        actionsStage.setScene(scene);
        actionsStage.show();
    }

    private static void handleSummon() {
        try {
            new CardActionsMenu().monsterSummon(gameController);
        } catch (GameException e) {
            e.printStackTrace();
            //todo show an error box
        }
        actionsStage.close();
    }

    public static ImageView getActiveSword() {
        return activeSword;
    }

    public static Rectangle getActiveRectangle() {
        return activeRectangle;
    }
}
