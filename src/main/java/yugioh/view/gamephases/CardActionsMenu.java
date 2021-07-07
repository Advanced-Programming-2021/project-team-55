package yugioh.view.gamephases;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
import yugioh.view.menus.WelcomeMenu;

import java.io.File;
import java.util.ArrayList;

import static yugioh.view.SoundPlayable.playButtonSound;

public class CardActionsMenu implements MainPhasesController {
    private static Stage actionsStage = new Stage();
    private static Stage errorStage = new Stage();


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

    private static Cell toBeSummonedCell;
    //    private static ImageView setImage=new ImageView(new Image(new File().toURI().toString()));
//
//    private static ImageView summonImage=new ImageView(new Image(new File().toURI().toString()));
//
//    private static ImageView attackImage=new ImageView(new Image(new File().toURI().toString()));
//
//    private static ImageView activateImage=new ImageView(new Image(new File().toURI().toString()));
//
//    private static ImageView flipSummon=new ImageView(new Image(new File().toURI().toString()));
//
//    private static ImageView changePosition=new ImageView(new Image(new File().toURI().toString()));
    private static ImageView setImageH = new ImageView(new Image(new File("src\\resources\\yugioh\\PNG\\icon\\SetH.png")
            .toURI().toString()));
    private static ImageView setImageV = new ImageView(new Image(new File("src\\resources\\yugioh\\PNG\\icon\\SetV.png").
            toURI().toString()));
    private static ImageView summonImage = new ImageView(new Image(new File("src\\resources\\yugioh\\PNG\\icon\\NormalSummon.png").
            toURI().toString()));
    private static ImageView attackImage = new ImageView(new Image(new File("src\\resources\\yugioh\\PNG\\icon\\Attack.png").
            toURI().toString()));
    private static ImageView activateImage = new ImageView(new Image(new File("src\\resources\\yugioh\\PNG\\icon\\Activate.png").toURI().toString()));
    private static ImageView flipSummonImage = new ImageView(new Image(new File("src\\resources\\yugioh\\PNG\\icon\\Flip summon.png").toURI().toString()));
    private static ImageView changePositionImage = new ImageView(new Image(new File("src\\resources\\yugioh\\PNG\\icon\\Change position.png").toURI().toString()));
    private static ImageView activeSword;
    private static Rectangle activeRectangle;
    private static int counter = 0;

    static {
        actionsStage.initOwner(WelcomeMenu.stage);
        actionsStage.initModality(Modality.NONE);
        actionsStage.initStyle(StageStyle.UNDECORATED);

        errorStage.initOwner(WelcomeMenu.stage);
        errorStage.initModality(Modality.NONE);
        errorStage.initStyle(StageStyle.UNDECORATED);
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

    public static Cell getToBeSummonedCell() {
        return toBeSummonedCell;
    }

    public static void setToBeSummonedCell(Cell toBeSummonedCell) {
        CardActionsMenu.toBeSummonedCell = toBeSummonedCell;
    }

    public static void execute(Rectangle rectangle, GameController controller) throws Exception {
        imageRectangle = rectangle;
        gameController = controller;
        start();
    }

    public static void start() throws Exception {
        counter = 0;
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
        if (Cell.getSelectedCell() == null || Cell.getSelectedCell().getCellCard() == null) return;
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
                    actionButton.setGraphic(flipSummonImage);
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
                    } else {
                        actionButton.setGraphic(changePositionImage);
                        if (gameController.changedPositionCells.contains(Cell.getSelectedCell())) {
                            actionButton.setDisable(true);
                        } else {
                            actionButton.setDisable(false);
                        }
                    }
                } else if (t1.equals("activate")) {
                    actionButton.setGraphic(activateImage);
                    //todo: activate spell
                }
            }
        });
        actionButton.setText(thisActions.get(0));
        actionButton.onMouseClickedProperty().set(mouseEvent -> {
            if (actionButton.getText().equals("flip summon")) {
                handleFlipSummon();
            } else if (actionButton.getText().equals("change position")) {
                handleChangePosition();
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
            playButtonSound("defence");
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
            playButtonSound("summon");//todo better aud
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
            playButtonSound("sword");
            rectangle.requestFocus();
            ImageView sword = new ImageView(new Image("/yugioh/PNG/icon/sword.png"));
            GameMenuController.getGameMenuController().gameBoardPane.getChildren().add(sword);
            sword.setX(rectangle.getLayoutX() + 14);
            sword.setY(rectangle.getLayoutY() + 10);
            activeSword = sword;
            activeRectangle = rectangle;
            GameMenuController.getGameMenuController().selectCard(rectangle);
            if (gameController.currentTurnOpponentPlayer.getGameBoard().isMonsterCardZoneEmpty()) {
                try {
                    String result = gameController.getBattlePhaseController().directAttack(gameController);
                    System.out.println(result);
                } catch (GameException e) {
                    System.out.println(e.getMessage());
                }
                return;
            }
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
                        if (gameController.currentPhase != GamePhase.BATTLE) return;
                        GameMenuController.getGameMenuController().selectCard(rectangle);
                        String result = Duel.getGameController().getBattlePhaseController().attack(finalI);
                        playButtonSound("attack");
                        System.out.println(result);
                    } catch (Exception e) {
                        try {
                            System.out.println(e.getMessage());
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
        counter = 0;
        if (actionsStage != null)
            actionsStage.close();
        if (errorStage != null) {
            errorStage.close();
        }
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
            playButtonSound("card");
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
        //actionButton.setStyle("-fx-background-image: url(\"src\\resources\\yugioh\\PNG\\SetH.png\");");
//        actionButton.setStyle("-fx-background-color: #ff0000; ");
        actionButton.setPrefWidth(setImageV.getFitWidth());
        actionButton.setPrefHeight(setImageH.getFitHeight());
        actionButton.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                errorStage.setX(xImage - 35);
                errorStage.setY(yImage + 25);
                Label errorMessage = new Label();
                errorMessage.setTextFill(Color.RED);
                Scene scene = WelcomeMenu.createScene(errorMessage);
                errorStage.setScene(scene);
                if (t1.equals("set") || counter == 0) {
                    if (Cell.getSelectedCell().getCellCard().isMonster()) {
                        actionButton.setGraphic(setImageH);
                    } else {
                        actionButton.setGraphic(setImageV);
                    }
                    if (Cell.getSelectedCell().getCellCard().isMonster() && (gameController.doPlayerSetOrSummonedThisTurn())) {
                        actionButton.setDisable(true);
                        errorMessage.setText("you have set/summoned once in this round!");
                        errorStage.show();
                    } else if (Cell.getSelectedCell().getCellCard().isMonster() && gameController.currentTurnPlayer.getGameBoard().isMonsterZoneFull()) {
                        actionButton.setDisable(true);
                        errorMessage.setText("monster zone is full!");
                        errorStage.show();
                    } else if (Cell.getSelectedCell().getCellCard().isMonster() &&
                            !new CardActionsMenu().hasEnoughTribute(Cell.getSelectedCell().getCellCard(), gameController.currentTurnPlayer,
                                    false)) {
                        actionButton.setDisable(true);
                        errorMessage.setText("you don't have enough tributes!");
                        errorStage.show();
                    } else if (Cell.getSelectedCell().getCellCard().isMonster() &&
                            !new CardActionsMenu().isSummonable(Cell.getSelectedCell(), gameController)) {
                        actionButton.setDisable(true);
                        errorMessage.setText("you need the ritual spell to set/summon!");
                        errorStage.show();
                    } else {
                        actionButton.setDisable(false);
                    }
                } else if (t1.equals("summon")) {
                    actionButton.setGraphic(summonImage);
                    if (gameController.doPlayerSetOrSummonedThisTurn()) {
                        actionButton.setDisable(true);
                        errorMessage.setText("you have set/summoned once in this round!");
                        errorStage.show();
                    } else if (gameController.currentTurnPlayer.getGameBoard().
                            isMonsterZoneFull()) {
                        actionButton.setDisable(true);
                        errorMessage.setText("monster zone is full!");
                        errorStage.show();
                    } else if (!new CardActionsMenu().isSummonable(Cell.getSelectedCell(), gameController)) {
                        actionButton.setDisable(true);
                        errorMessage.setText("you need the ritual card to set/summon!");
                        errorStage.show();
                    } else if (!new CardActionsMenu().hasEnoughTribute(Cell.getSelectedCell().getCellCard(), gameController.currentTurnPlayer,
                            false)) {
                        actionButton.setDisable(true);
                        errorMessage.setText("you don't have enough tributes!");
                        errorStage.show();
                    } else {
                        actionButton.setDisable(false);
                    }
                } else if (t1.equals("activate")) {
                    actionButton.setGraphic(activateImage);
                    //todo: activate spell
                }
                actionButton.onMouseExitedProperty().set(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (errorStage.isShowing())
                            errorStage.close();
                    }
                });
                counter++;
            }
        });
        actionButton.setText(thisActions.get(0));
        actionButton.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton()== MouseButton.PRIMARY) {
                    if (actionButton.getText().equals("set")) {
                        handleSet();
                    } else if (actionButton.getText().equals("summon")) {
                        handleSummon();
                    }
                }
            }
        });
        Pane pane = new Pane();
        pane.getChildren().add(actionButton);
//        pane.setPrefWidth(actionButton.getWidth());
//        pane.setPrefHeight(actionButton.getHeight());
        pane.setPrefHeight(actionButton.getHeight());
        pane.setPrefWidth(actionButton.getWidth());
        Scene scene = WelcomeMenu.createScene(pane);
        actionsStage.setX(xImage);
        actionsStage.setY(yImage);
        actionsStage.setScene(scene);
        actionsStage.show();
    }

    private static void handleSummon() {
        try {
            playButtonSound("summon");
            new CardActionsMenu().monsterSummon(gameController);
        } catch (GameException e) {
            e.printStackTrace();
            try {
                new PopUpWindow(e.getMessage()).start(WelcomeMenu.getStage());
            } catch (Exception ignored) {
            }
            //todo show an error box
        }
        actionsStage.close();
    }

    public static ImageView getActiveSword() {
        return activeSword;
    }

    public static boolean isBoardInverse() {
        return (gamePane.rotateProperty().get() % 360) > 179;
    }

}
