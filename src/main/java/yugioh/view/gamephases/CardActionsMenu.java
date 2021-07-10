package yugioh.view.gamephases;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static yugioh.view.SoundPlayable.playButtonSound;

public class CardActionsMenu implements MainPhasesController {
    private static HashMap<Cell, EventHandler<MouseEvent>> toBeRemovedSelectionEventHandlers = new HashMap<>();
    private static HashMap<Rectangle, ArrayList<EventHandler<MouseEvent>>> allCellEventHandlers = new HashMap<>();
    private static Stage actionsStage = new Stage();
    private static Stage errorStage = new Stage();
    private static double xImage;
    private static double yImage;
    private static ImageView actionButton;
    private static Rectangle imageRectangle;
    private static ArrayList<Image> handMonsterActions = new ArrayList<>();
    private static ArrayList<Image> handSpellAndTrapActions = new ArrayList<>();
    private static ArrayList<Image> boardMonsterActions = new ArrayList<>();
    private static ArrayList<Image> boardSpellAndTrapActions = new ArrayList<>();
    private static ArrayList<Image> thisActions = new ArrayList<>();
    private static Pane gamePane;
    private static Pane actionPane;
    private static int place = 6;
    private static GameController gameController;
    private static double lastMousePositionX = 0;
    private static double lastMousePositionY = 0;
    private static Cell toBeSummonedCell;
    private static Image setImageH = new Image(new File("src\\resources\\yugioh\\PNG\\icon\\SetH.png")
            .toURI().toString());
    private static Image setImageV = new Image(new File("src\\resources\\yugioh\\PNG\\icon\\SetV.png").
            toURI().toString());
    private static Image summonImage = new Image(new File("src\\resources\\yugioh\\PNG\\icon\\NormalSummon.png").
            toURI().toString());
    private static Image attackImage = new Image(new File("src\\resources\\yugioh\\PNG\\icon\\Attack.png").
            toURI().toString());
    private static Image activateImage = new Image(new File("src\\resources\\yugioh\\PNG\\icon\\Activate.png").toURI().toString());
    private static Image flipSummonImage = new Image(new File("src\\resources\\yugioh\\PNG\\icon\\Flip summon.png").toURI().toString());
    private static Image changePositionImage = new Image(new File("src\\resources\\yugioh\\PNG\\icon\\ChangePosition.png").toURI().toString());
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
        errorStage.setAlwaysOnTop(true);
        {
            handMonsterActions.add(setImageH);
            handMonsterActions.add(summonImage);

            handSpellAndTrapActions.add(setImageV);
            handSpellAndTrapActions.add(activateImage);

            boardMonsterActions.add(changePositionImage);
            boardMonsterActions.add(flipSummonImage);

            boardSpellAndTrapActions.add(activateImage);
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
        if (gameController.getGameMenuController().shouldActivateEffectsNow) {
            openActivate();
            return;
        }
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
        }

    }

    private static void openActivate() {
        if (Cell.getSelectedCell() == null || Cell.getSelectedCell().getCellCard() == null) return;
        actionButton = new ImageView();
        actionButton.fitWidthProperty().bind(actionsStage.widthProperty());
        actionButton.fitHeightProperty().bind(actionsStage.heightProperty());
        actionPane = new Pane();
        actionPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, null)));
        actionButton.setImage(activateImage);
        activateImage();
        actionPane.getChildren().add(actionButton);
        actionButton.onMouseClickedProperty().set(mouseEvent -> {
            if (actionButton.getImage().equals(activateImage)) {
                handleActivate();
            }
        });
        Scene scene = new Scene(actionPane, 60, 60);
        scene.setCursor(Cursor.OPEN_HAND);
        actionPane.setPrefHeight(60);
        actionPane.setPrefWidth(60);
        actionsStage.setX(xImage);
        actionsStage.setY(yImage);
        actionsStage.setScene(scene);
        actionsStage.show();
    }

    private static void openBattlePhaseActionsForCardsInBoard() {

    }

    private static void disableImage() {
        actionPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, null)));
        actionButton.setOpacity(0.2);
        actionButton.setDisable(true);
    }

    private static void activateImage() {
        if (actionsStage != null) {
            actionPane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, null)));
            actionButton.setOpacity(1);
            actionButton.setDisable(false);
        }
    }

    private static void openMainPhaseActionsForCardsInBoard() {
        if (Cell.getSelectedCell() == null || Cell.getSelectedCell().getCellCard() == null) return;
        if (Cell.getSelectedCell().getCellCard().isMonster()) {
            thisActions = boardMonsterActions;
        } else {
            thisActions = boardSpellAndTrapActions;
        }
        actionButton = new ImageView();

        actionButton.fitWidthProperty().bind(actionsStage.widthProperty());
        actionButton.fitHeightProperty().bind(actionsStage.heightProperty());
        actionPane = new Pane();
        actionPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, null)));
        actionPane.getChildren().add(actionButton);
        actionButton.imageProperty().addListener(new ChangeListener<Image>() {
            @Override
            public void changed(ObservableValue<? extends Image> observableValue, Image image, Image t1) {
                Label errorMessage = buildErrorStage();
                if (t1.equals(flipSummonImage)) {
                    Cell selectedCell = Cell.getSelectedCell();
                    if (selectedCell.getCardStatus() != CardStatus.DEFENSIVE_HIDDEN) {
                        change();
                    } else if (gameController.getChangedPositionCells().contains(selectedCell)) {
                        disableImage();
                        errorMessage.setText("you can't flip summon this card in this round!");
                        errorStage.show();
                    } else {
                        activateImage();
                    }
                } else if (t1.equals(changePositionImage)) {
                    if (Cell.getSelectedCell().getCardStatus() == CardStatus.DEFENSIVE_HIDDEN) {
                        change();
                    } else {
                        if (gameController.changedPositionCells.contains(Cell.getSelectedCell())) {
                            actionButton.setImage(changePositionImage);
                            errorMessage.setText("you can't change the position of this card in this round!");
                            errorStage.show();
                            disableImage();
                        } else {
                            activateImage();
                        }
                    }
                } else if (t1.equals(activateImage)) {
                    if (Cell.getSelectedCell().getCellCard().isSpell())
                        activateImage();
                    else {
                        disableImage();
                    }
                }
            }
        });
        actionButton.setImage(thisActions.get(0));
        actionButton.onMouseClickedProperty().set(mouseEvent -> {
            if (actionButton.getImage().equals(flipSummonImage)) {
                handleFlipSummon();
            } else if (actionButton.getImage().equals(changePositionImage)) {
                handleChangePosition();
            } else if (actionButton.getImage().equals(activateImage)) {
                handleActivate();
            }
        });
        Scene scene = new Scene(actionPane, 60, 60);
        scene.setCursor(Cursor.OPEN_HAND);
        actionPane.setPrefHeight(60);
        actionPane.setPrefWidth(60);
        actionsStage.setX(xImage);
        actionsStage.setY(yImage);
        actionsStage.setScene(scene);
        actionsStage.show();
    }

    private static void handleChangePosition() {
        Label errorMessage = buildErrorStage();
        try {
            playButtonSound("defence");
            if (Cell.getSelectedCell().getCardStatus() == CardStatus.DEFENSIVE_OCCUPIED)
                new CardActionsMenu().setPosition("attack", gameController);
            else new CardActionsMenu().setPosition("defense", gameController);
        } catch (GameException e) {
            errorMessage.setText(e.getMessage());
            errorStage.show();
        }
        actionsStage.close();
    }

    @NotNull
    private static Label buildErrorStage() {
        errorStage.setX(xImage - 60);
        errorStage.setY(yImage + 20);
        Label errorMessage = new Label();
        errorMessage.setTextFill(Color.RED);
        errorMessage.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, null)));
        Scene scene = WelcomeMenu.createScene(errorMessage);
        errorStage.setScene(scene);
        return errorMessage;
    }

    private static void handleFlipSummon() {
        Label errorMessage = buildErrorStage();
        try {
            playButtonSound("summon");
            new CardActionsMenu().flipSummon(gameController);
        } catch (GameException e) {
            errorMessage.setText(e.getMessage());
            errorStage.show();
        }
        actionsStage.close();
    }

    public static void makeSwordEventForSummonedMonsters(Rectangle rectangle) {
        Player currentPlayer = Duel.getGameController().getCurrentTurnPlayer();
        EventHandler<MouseEvent> eventHandler = event -> {
            if (!currentPlayer.equals(Duel.getGameController().getCurrentTurnPlayer())) return;
            if (Duel.getGameController().getCurrentPhase() != GamePhase.BATTLE) return;
            if (gameController.getGameMenuController().shouldActivateEffectsNow) return;
            if (activeRectangle == rectangle && activeSword != null) {
                removeSword();
                return;
            } else if (activeSword != null) {
                removeSword();
            }
            GameMenuController.getGameMenuController().selectCard(rectangle);
            if (checkAttackConditions(currentPlayer)) return;
            playButtonSound("sword");
            rectangle.requestFocus();
            ImageView sword = new ImageView(new Image("/yugioh/PNG/icon/sword.png"));
            GameMenuController.getGameMenuController().gameBoardPane.getChildren().add(sword);
            sword.setX(rectangle.getLayoutX() + 14);
            sword.setY(rectangle.getLayoutY() + 10);
            activeSword = sword;
            activeRectangle = rectangle;
            GameMenuController.getGameMenuController().selectCard(rectangle);
            if (handleDirectAttack(sword, rectangle)) return;
            handleSwordRotation(rectangle, sword);
            GameMenuController.getGameMenuController().selectCard(rectangle);
            Cell[] monsterCardZone = gameController.getCurrentTurnOpponentPlayer().getGameBoard().getMonsterCardZone();
            handleRivalMonsterSelection(rectangle, sword, monsterCardZone);
            event.consume();
        };
        rectangle.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        addEventHandlerToRectangleEventHandlers(rectangle, eventHandler);
    }

    private static boolean checkAttackConditions(Player currentPlayer) {
        Cell selectedCell = Cell.getSelectedCell();
        if (selectedCell == null) {
            try {
                new PopUpWindow(GameResponses.NO_CARDS_SELECTED.response).start(WelcomeMenu.getStage());
            } catch (Exception ignored) {
            }
            return true;
        }
        if (!currentPlayer.getGameBoard().isCellInMonsterZone(selectedCell) || selectedCell.getCardStatus() != CardStatus.OFFENSIVE_OCCUPIED) {
            try {
                new PopUpWindow(GameResponses.CAN_NOT_ATTACK_WITH_THIS_CARD.response).start(WelcomeMenu.getStage());
            } catch (Exception ignored) {
            }
            return true;
        }
        if (gameController.didCardAttackThisTurn(selectedCell)) {
            try {
                new PopUpWindow(GameResponses.CARD_ALREADY_ATTACKED.response).start(WelcomeMenu.getStage());
            } catch (Exception ignored) {
            }
            return true;
        }
        return false;
    }

    private static void addEventHandlerToRectangleEventHandlers(Rectangle rectangle, EventHandler<MouseEvent> eventHandler) {
        if (!allCellEventHandlers.containsKey(rectangle)) {
            ArrayList<EventHandler<MouseEvent>> eventHandlers = new ArrayList<>();
            allCellEventHandlers.put(rectangle, eventHandlers);
        }
        allCellEventHandlers.get(rectangle).add(eventHandler);
    }

    public static void removeRectangleEventHandlers(Rectangle rectangle) {
        try {
            for (EventHandler<MouseEvent> eventHandler : allCellEventHandlers.get(rectangle)) {
                rectangle.removeEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
            }
        } catch (Exception ignored) {
        }
    }

    private static void handleRivalMonsterSelection(Rectangle rectangle, ImageView sword, Cell[] monsterCardZone) {
        for (int i = 0; i < monsterCardZone.length; i++) {
            Cell cell = monsterCardZone[i];
            int finalI = i;
            EventHandler<MouseEvent> eventHandler = event3 -> {
                setLastMousePositionX(event3.getSceneX() - 400);
                setLastMousePositionY(event3.getSceneY());
                try {
                    if (gameController.currentPhase != GamePhase.BATTLE) return;
                    GameMenuController.getGameMenuController().selectCard(rectangle);
                    gameController.currentTurnPlayer.getGameBoard().setTranslationAnimation(sword, cell.getCellRectangle());
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event4 -> {
                        String result;
                        try {
                            result = Duel.getGameController().getBattlePhaseController().attack(finalI);
                            System.out.println(result);
                        } catch (GameException e) {
                            System.out.println(e.getMessage());
                            Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(0.2), event10 -> CardActionsMenu.removeSword()));
                            timeline2.play();
                        }
                    }));
                    timeline.play();
                    playButtonSound("attack");
                } catch (Exception e) {
                    try {
                        System.out.println(e.getMessage());
                        new PopUpWindow(e.getMessage()).start(WelcomeMenu.getStage());
                    } catch (Exception ignored) {
                    }
                }
                event3.consume();
            };

            cell.getCellRectangle().addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
            toBeRemovedSelectionEventHandlers.put(cell, eventHandler);
        }
    }

    private static void handleSwordRotation(Rectangle rectangle, ImageView sword) {
        GameMenuController.getGameMenuController().gameBoardPane.addEventHandler(MouseEvent.MOUSE_MOVED, event2 -> {
            double constant = 0;
            if ((gamePane.rotateProperty().get() % 360) > 179) constant = 180;
            sword.setRotate(constant - (Math.toDegrees(Math.atan((event2.getSceneX() - 400 - rectangle.getLayoutX()) / (event2.getSceneY() - rectangle.getLayoutY() - constant)))));
            event2.consume();
        });
    }

    private static boolean handleDirectAttack(ImageView sword, Rectangle rectangle) {
        if (gameController.currentTurnOpponentPlayer.getGameBoard().isMonsterCardZoneEmpty()) {
            Rectangle rectangle1 = new Rectangle();
            rectangle1.setLayoutX(330);
            rectangle1.setLayoutY(50);
            if (isBoardInverse()) {
                rectangle1.setLayoutY(620);
                sword.setRotate(sword.getRotate() + 180);
            }
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event4 -> {
                try {
                    GameMenuController.getGameMenuController().selectCard(rectangle);
                    String result = gameController.getBattlePhaseController().directAttack(gameController);
                    gameController.currentTurnPlayer.getGameBoard().setTranslationAnimation(sword, rectangle1);
                    Timeline timeline3 = new Timeline(new KeyFrame(Duration.seconds(2), event -> CardActionsMenu.removeSword()));
                    timeline3.play();
                    System.out.println(result);
                } catch (GameException e) {
                    System.out.println(e.getMessage());
                    try {
                        new PopUpWindow(e.getMessage()).start(WelcomeMenu.getStage());
                    } catch (Exception ignored) {
                    }
                    Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(0.2), event10 -> CardActionsMenu.removeSword()));
                    timeline2.play();
                }
            }));
            timeline.play();
            playButtonSound("attack");
            return true;
        }
        return false;
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
        for (Image image : thisActions) {
            if (image.equals(actionButton.getImage())) {
                if (counter == thisActions.size() - 1) {
                    counter = -1;
                }
                actionButton.setImage(thisActions.get(counter + 1));
                return;
            }
            counter++;
        }
    }

    public static void handleSet() {
        Label errorMessage = buildErrorStage();
        try {
            playButtonSound("card");
            new CardActionsMenu().setCard(gameController);
        } catch (GameException e) {
            errorMessage.setText(e.getMessage());
            errorStage.show();
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
        actionButton = new ImageView();
        actionButton.imageProperty().addListener(new ChangeListener<Image>() {
            @Override
            public void changed(ObservableValue<? extends Image> observableValue, Image image, Image t1) {
                errorStage.setX(xImage - 40);
                errorStage.setY(yImage + 20);
                Label errorMessage = new Label();
                errorMessage.setTextFill(Color.RED);
                errorMessage.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, null)));
                Scene scene = WelcomeMenu.createScene(errorMessage);
                errorStage.setScene(scene);
                if (t1.equals(setImageH) || counter == 0) {
                    if (Cell.getSelectedCell().getCellCard().isMonster() && (gameController.doPlayerSetOrSummonedThisTurn())) {
                        disableImage();
                        errorMessage.setText("you have set/summoned once in this round!");
                        errorStage.show();
                    } else if (Cell.getSelectedCell().getCellCard().isMonster() && gameController.currentTurnPlayer.getGameBoard().isMonsterZoneFull()) {
                        disableImage();
                        errorMessage.setText("monster zone is full!");
                        errorStage.show();
                    } else if (Cell.getSelectedCell().getCellCard().isMonster() &&
                            !new CardActionsMenu().hasEnoughTribute(Cell.getSelectedCell().getCellCard(), gameController.currentTurnPlayer,
                                    false)) {
                        disableImage();
                        errorMessage.setText("you don't have enough tributes!");
                        errorStage.show();
                    } else if (Cell.getSelectedCell().getCellCard().isMonster() &&
                            !new CardActionsMenu().isSummonable(Cell.getSelectedCell(), gameController)) {
                        disableImage();
                        errorMessage.setText("you need the ritual spell to set/summon!");
                        errorStage.show();
                    } else {
                        activateImage();
                    }
                } else if (t1.equals(summonImage)) {
                    //actionButton.setGraphic(summonImage);
                    if (gameController.doPlayerSetOrSummonedThisTurn()) {
                        disableImage();
                        errorMessage.setText("you have set/summoned once in this round!");
                        errorStage.show();
                    } else if (gameController.currentTurnPlayer.getGameBoard().
                            isMonsterZoneFull()) {
                        disableImage();
                        errorMessage.setText("monster zone is full!");
                        errorStage.show();
                    } else if (!new CardActionsMenu().isSummonable(Cell.getSelectedCell(), gameController)) {
                        disableImage();
                        errorMessage.setText("you need the ritual card to set/summon!");
                        errorStage.show();
                    } else if (!new CardActionsMenu().hasEnoughTribute(Cell.getSelectedCell().getCellCard(), gameController.currentTurnPlayer,
                            false)) {
                        disableImage();
                        errorMessage.setText("you don't have enough tributes!");
                        errorStage.show();
                    } else {
                        activateImage();
                    }
                } else if (t1.equals(setImageV)) {
                    activateImage();
                } else if (t1.equals(activateImage)) {
                    if (Cell.getSelectedCell().getCellCard().isSpell())
                        activateImage();
                    else {
                        disableImage();
                    }
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
        actionPane = new Pane();
        actionButton.setImage(thisActions.get(0));
        actionButton.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    if (actionButton.getImage().equals(setImageH) || actionButton.getImage().equals(setImageV)) {
                        handleSet();
                    } else if (actionButton.getImage().equals(summonImage)) {
                        handleSummon();
                    } else if (actionButton.getImage().equals(activateImage)) {
                        handleActivate();
                    }
                }
            }
        });
        actionPane.setPrefHeight(60);
        actionPane.setPrefWidth(60);
        actionButton.fitWidthProperty().bind(actionPane.widthProperty());
        actionButton.fitHeightProperty().bind(actionPane.heightProperty());
        actionPane.getChildren().add(actionButton);
        Scene scene = new Scene(actionPane, 60, 60);
        scene.setCursor(Cursor.OPEN_HAND);
        actionsStage.setX(xImage);
        actionsStage.setY(yImage);
        actionsStage.setScene(scene);
        actionsStage.show();
    }

    private static void handleActivate() {
        try {
            if (gameController.getGameMenuController().shouldActivateEffectsNow) {
                if (gameController.getGameMenuController().canBeActivatedCards.contains(Cell.
                        getSelectedCell().getCellCard().getName())) {
                    new CardActionsMenu().activateSpell(gameController);
                    gameController.getGameMenuController().shouldActivateEffectsNow = false;
                    gameController.getGameMenuController().canBeActivatedCards.clear();
                    gameController.changeTurn(true, true);
                } else {
                    Toast.makeText(WelcomeMenu.stage, "You can't activate this card!");
                }
            } else {
                new CardActionsMenu().activateSpell(gameController);
            }
        } catch (GameException e) {
            Toast.makeText(WelcomeMenu.stage, e.getMessage());
        }
        actionsStage.close();
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
        }
        actionsStage.close();
    }

    public static ImageView getActiveSword() {
        return activeSword;
    }

    public static boolean isBoardInverse() {
        return (gamePane.rotateProperty().get() % 360) > 179;
    }

    public static void removeEventHandlers() {
        try {
            for (Cell cell : toBeRemovedSelectionEventHandlers.keySet()) {
                cell.getCellRectangle().removeEventHandler(MouseEvent.MOUSE_CLICKED, toBeRemovedSelectionEventHandlers.get(cell));
            }
            toBeRemovedSelectionEventHandlers = new HashMap<>();
        } catch (Exception ignored) {
        }
    }

}
