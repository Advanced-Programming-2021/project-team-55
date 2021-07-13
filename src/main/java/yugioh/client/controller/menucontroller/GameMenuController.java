package yugioh.client.controller.menucontroller;


import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import yugioh.client.controller.gamephasescontrollers.GameController;
import yugioh.client.model.board.CardStatus;
import yugioh.client.model.board.Cell;
import yugioh.client.model.cards.Card;
import yugioh.client.model.cards.Monster;
import yugioh.client.view.SoundPlayable;
import yugioh.client.view.ViewInterface;
import yugioh.client.view.gamephases.CardActionsMenu;
import yugioh.client.view.gamephases.Duel;
import yugioh.client.view.gamephases.GamePhase;
import yugioh.client.view.gamephases.Graveyard;
import yugioh.client.view.menus.Toast;
import yugioh.client.view.menus.WelcomeMenu;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

public class GameMenuController extends MenuController implements Initializable {

    public static Stage pauseStage;
    private static GameMenuController gameMenuController;
    public Pane gamePane;
    public Rectangle hoveredImageRectangle;
    public ScrollPane descriptionContainer;
    public Label description;
    public Label atkLabel;
    public Label defLabel;
    public Label defValue;
    public Label atkValue;
    public Label rivalLP;
    public ProgressBar rivalLPBar;
    public Label userLP;
    public ProgressBar userLPBar;
    public Label dpLabel;
    public Label spLabel;
    public Label m1Label;
    public Label bpLabel;
    public Label m2Label;
    public Label epLabel;
    public Label currentUsername;
    public Label currentNickname;
    public Label opponentUsername;
    public Label opponentNickname;
    public ImageView currentImage;
    public ImageView opponentImage;
    public Pane gameBoardPane;
    public HBox rivalHandCardsContainer;
    public HBox userHandCardsContainer;
    public Pane rivalDeckZoneContainer;
    public Pane userDeckZoneContainer;
    public GameController gameController;
    public Polygon nextPhaseTriangle;
    public ImageView background;
    public ImageView settingImage;
    public ImageView chatImage;
    public boolean shouldSelectTributesNow = false;
    public int neededTributes;
    public boolean isTributeForSummon;
    public ArrayList<Cell> tributeCells = new ArrayList<>();
    public Rectangle userGraveyard;
    public Rectangle rivalGraveyard;
    public boolean shouldActivateEffectsNow = false;
    public ArrayList<String> canBeActivatedCards;
    public boolean choiceHasBeenMade = true;

    public static GameMenuController getGameMenuController() {
        return gameMenuController;
    }

    public void changeGameBoard() {
//        rivalHandCardsContainer.getChildren().clear();
//        rivalDeckZoneContainer.getChildren().clear();
//        userHandCardsContainer.getChildren().clear();
//        userDeckZoneContainer.getChildren().clear();
        updateCells();
        removeCardHoveredImageAndInfo(Card.backImageForAllCards);
        RotateTransition rotateTransition = new RotateTransition();
        RotateTransition rotateTransitionForBackground = new RotateTransition();
        rotateTransition.setAxis(Rotate.Z_AXIS);
        rotateTransitionForBackground.setAxis(Rotate.Z_AXIS);
        //  Point3D point3D=Rotate.Z_AXIS;
        // point3D=new Point3D(50,0,1);
        //point3D=point3D.subtract(100,0,0);
        // rotate.setAxis(point3D);
        //rotate.setAxis(Rotate.Z_AXIS.add(50,0,0));
//        Group group=new Group();
//        group.getChildren().add(gameMenuController.gameBoardPane);
//        group.getChildren().add(gameMenuController.background);
        rotateTransition.setByAngle(180);
        rotateTransitionForBackground.setByAngle(180);
        rotateTransition.setDuration(Duration.millis(1000));
        rotateTransitionForBackground.setDuration(Duration.millis(1000));
        rotateTransition.setNode(gameMenuController.gameBoardPane);
        rotateTransitionForBackground.setNode(gameMenuController.getBackground());
        rotateTransitionForBackground.play();
        rotateTransition.play();
        for (Cell cell : gameController.currentTurnPlayer.getGameBoard().getHandCards()) {
            cell.getCellRectangle().setFill(cell.getCellCard().getCardBackImagePattern());
            // userHandCardsContainer.getChildren().add(cell.getCellRectangle());
        }
        for (Cell cell : gameController.currentTurnOpponentPlayer.getGameBoard().getHandCards()) {
            cell.getCellRectangle().setFill(cell.getCellCard().getCardImagePattern());
            //rivalHandCardsContainer.getChildren().add(cell.getCellRectangle());
        }
        for (Cell cell : gameController.currentTurnPlayer.getGameBoard().getMonsterCardZone()) {
            Label label = cell.getCellInfo();
            if (cell.getCardStatus() == CardStatus.DEFENSIVE_HIDDEN) {
                label.setText("");
            }
            //if((gameBoardPane.rotateProperty().get()%360)<179)
            label.rotateProperty().set(gameBoardPane.rotateProperty().get() + 180);
//            else{
//                label.rotateProperty().set(0);
//            }
        }
        for (Cell cell : gameController.currentTurnOpponentPlayer.getGameBoard().getMonsterCardZone()) {
            Label label = cell.getCellInfo();
            if (cell.getCardStatus() == CardStatus.DEFENSIVE_HIDDEN) {
                label.setText(((Monster) (cell.getCellCard())).getAtk() + "/" +
                        ((Monster) (cell.getCellCard())).getDef());
            }

            // if((gameBoardPane.rotateProperty().get()%360)>179)
            //label.rotateProperty().set(0);
            label.rotateProperty().set(gameBoardPane.rotateProperty().get() + 180);

        }
        gameController.currentTurnPlayer.getGameBoard().getFieldZone().getCellRectangle().rotateProperty().set(gameBoardPane.rotateProperty().get() + 180);
        Cell.deselectCell();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Duel.getGameController().setGameMenuController(this);
        this.gameController = Duel.getGameController();
        updateGameStatusUIs();
        updateCells();
        gameController.currentTurnPlayer.getGameBoard().setBoardRectangles(gameBoardPane, false);
        gameController.currentTurnOpponentPlayer.getGameBoard().setBoardRectangles(gameBoardPane, true);
        gameController.currentTurnPlayer.getGameBoard().setCellsLabels(false);
        gameController.currentTurnOpponentPlayer.getGameBoard().setCellsLabels(true);
        gameMenuController = this;
        userHandCardsContainer.setPadding(new Insets(0, 30, 0, 30));
        rivalHandCardsContainer.setPadding(new Insets(0, 30, 0, 30));
        userDeckZoneContainer.setPadding(new Insets(0, 0, 0, 0));
        userHandCardsContainer.setSpacing(17);
        rivalHandCardsContainer.setSpacing(17);
        hoveredImageRectangle.setFill(Card.backImageForAllCards);
        description.setWrapText(true);
        description.setTextAlignment(TextAlignment.JUSTIFY);
    }


    private void updateCells() {
        for (Cell cell : gameController.currentTurnPlayer.getGameBoard().getMonsterCardZone()) {
            if (!cell.isEmpty()) {
                cell.getCellInfo().setText(((Monster) cell.getCellCard()).getAtk() + "/" + ((Monster) cell.getCellCard()).getDef());

            }
        }
        for (Cell cell : gameController.currentTurnOpponentPlayer.getGameBoard().getMonsterCardZone()) {
            if (!cell.isEmpty()) {
                cell.getCellInfo().setText(((Monster) cell.getCellCard()).getAtk() + "/" + ((Monster) cell.getCellCard()).getDef());
            }
        }
    }

    public void pauseClicked() throws Exception {
        CardActionsMenu.close();

        SoundPlayable.playButtonSound("surrender");
        URL url = getClass().getResource("/yugioh/fxml/PauseMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        pane.getChildren().get(0).setOnMouseEntered(mouseEvent -> SoundPlayable.playButtonSound("weNeverSurrender"));
        pane.getChildren().get(1).setOnMouseEntered(mouseEvent -> SoundPlayable.playButtonSound("neverSurrender"));
        pane.getChildren().get(0).setOnMouseClicked(mouseEvent -> resume());
        pane.getChildren().get(1).setOnMouseClicked(mouseEvent -> {
            try {
                SoundPlayable.playButtonSound("evilLaugh");
                surrender();
            } catch (Exception ignored) {
            }
        });
        Scene scene = WelcomeMenu.createScene(pane);
        pauseStage = new Stage();
        scene.getStylesheets().add(
                getClass().getResource("/yugioh/CSS/Menu.css").toExternalForm());
        pauseStage.setScene(scene);
        pauseStage.initStyle(StageStyle.UTILITY);
        pauseStage.initOwner(WelcomeMenu.getStage());
        pauseStage.initModality(Modality.APPLICATION_MODAL);
        pauseStage.showAndWait();
    }

    public void resume() {
        pauseStage.close();
    }

    public void surrender() {
        ViewInterface.showResult("surrender");//todo complete function

        gameController.surrender();
        pauseStage.close();
        gameController.endGameRound();
    }

    public void updateGameStatusUIs() {
        GameController gameController = Duel.getGameController();
        int opponentLP = gameController.currentTurnOpponentPlayer.getLP();
        int myLP = gameController.currentTurnPlayer.getLP();
        rivalLP.setText(opponentLP + "");
        userLP.setText(myLP + "");
        opponentUsername.setText("Username : " + gameController.currentTurnOpponentPlayer.
                getUser().getUsername());
        opponentNickname.setText("Nickname : " + gameController.currentTurnOpponentPlayer.
                getUser().getNickname());
        currentUsername.setText("Username : " + gameController.currentTurnPlayer.
                getUser().getUsername());
        currentNickname.setText("Nickname : " + gameController.currentTurnPlayer.
                getUser().getNickname());
        if (gameController.currentTurnPlayer.getUser().isImageIsChanged())
            currentImage.setImage(new Image(gameController.currentTurnPlayer.getUser().getProfileImageFile().toURI().toString()));
        else currentImage.setImage(new Image(gameController.currentTurnPlayer.getUser().getProfileImageString()));
        currentImage.setPreserveRatio(true);
        currentImage.setFitHeight(85);
        if (gameController.currentTurnOpponentPlayer.getUser().isImageIsChanged())
            opponentImage.setImage(new Image(gameController.currentTurnOpponentPlayer.getUser().getProfileImageFile().toURI().toString()));
        else
            opponentImage.setImage(new Image(gameController.currentTurnOpponentPlayer.getUser().getProfileImageString()));
        opponentImage.setPreserveRatio(true);
        opponentImage.setFitHeight(85);
        rivalLPBar.setProgress((double) opponentLP / 8000);
        userLPBar.setProgress((double) myLP / 8000);
    }

    public void removeCardHoveredImageAndInfo(Paint paint) {
        Platform.runLater(() -> hoveredImageRectangle.setFill(paint));
        Platform.runLater(() -> description.setText(""));
        Platform.runLater(() -> defLabel.setText(""));
        Platform.runLater(() -> atkLabel.setText(""));
        Platform.runLater(() -> defValue.setText(""));
        Platform.runLater(() -> atkValue.setText(""));
    }

    public void addEventForCardImageRectangle(Rectangle rectangle, Cell cellToAddEvent) {

        rectangle.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            Card card = cellToAddEvent.getCellCard();
            if (card == null || rectangle.getFill() == null || (rectangle.getFill().equals(card.getCardBackImagePattern()) && !gameController.
                    currentTurnPlayer.getGameBoard().isCellInMonsterZone(Cell.getSelectedCellByRectangle(rectangle)) &&
                    !gameController.currentTurnPlayer.getGameBoard().isCellInSpellAndTrapZone
                            (Cell.getSelectedCellByRectangle(rectangle)))) {
                removeCardHoveredImageAndInfo(rectangle.getFill());
            } else {
                Platform.runLater(() -> hoveredImageRectangle.setFill(Cell.getSelectedCellByRectangle(rectangle).getCellCard().getCardImagePattern()));
                Platform.runLater(() -> description.setText(card.getDescription()));
                if (card instanceof Monster) {
                    defLabel.setText("DEF:");
                    atkLabel.setText("ATK:");
                    defValue.setOpacity(1);
                    atkValue.setOpacity(1);
                    defLabel.setOpacity(1);
                    atkLabel.setOpacity(1);
                    Platform.runLater(() -> defValue.setText(((Monster) card).getDef() + ""));
                    Platform.runLater(() -> atkValue.setText(((Monster) card).getAtk() + ""));
                } else {
                    defLabel.setOpacity(0);
                    atkLabel.setOpacity(0);
                    defValue.setOpacity(0);
                    atkValue.setOpacity(0);
                }
                event.consume();
            }
        });
        CardActionsMenu.setGamePane(gameBoardPane);
        rectangle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (shouldSelectTributesNow) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (!gameController.currentTurnPlayer.getGameBoard().isCellInMonsterZone(Cell.getSelectedCellByRectangle(rectangle))) {
                        Toast.makeText(WelcomeMenu.stage, "select a monster to tribute");
                        return;
                    }
                    if (rectangle.getEffect() != null) {
                        rectangle.setEffect(null);
                        tributeCells.remove(Cell.getSelectedCellByRectangle(rectangle));
                    } else {
                        Cell tributeCell = Cell.getSelectedCellByRectangle(rectangle);
                        tributeCells.add(tributeCell);
                        DropShadow tributeEffect = new DropShadow(BlurType.values()[1],
                                RED, 10, 2.0f, 0, 0);
                        tributeEffect.setBlurType(BlurType.ONE_PASS_BOX);
                        rectangle.setEffect(tributeEffect);
                        if (neededTributes == tributeCells.size()) {
                            for (Cell cell : tributeCells) {
                                Rectangle graveyard = GameMenuController.gameMenuController.userGraveyard;
                                if (CardActionsMenu.isBoardInverse())
                                    graveyard = GameMenuController.gameMenuController.rivalGraveyard;
                                //    gameController.getBattlePhaseController().moveCardToGraveyard(cell, graveyard, gameController.currentTurnPlayer);
                                cell.getCellRectangle().setEffect(null);
                                cell.removeCardFromCell(gameController.currentTurnPlayer.getGameBoard());
                                // gameController.currentTurnPlayer.getGameBoard().addCardToGraveyard(cell.getCellCard());
                            }
                            if (isTributeForSummon)
                                gameController.getMainPhase1Controller().continueMonsterSummon(gameController, false);
                            else gameController.getMainPhase1Controller().continueSetMonster(gameController);
                            shouldSelectTributesNow = false;
                            tributeCells.clear();
                            neededTributes = 0;
                        }
                    }
                }
            } else if (shouldActivateEffectsNow) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (gameController.currentTurnPlayer.getGameBoard().isCellInSpellAndTrapZone(Cell.getSelectedCellByRectangle(rectangle))) {
                        if (Cell.getSelectedCell() != null && !Cell.getSelectedCell().isEmpty() && Cell.getSelectedCell().getCellRectangle().equals(rectangle)) {
                            Cell.deselectCell();
                        } else {
                            Cell.deselectCell();
                            selectCard(rectangle);
                        }
                        try {
                            CardActionsMenu.setCoordinates(event.getSceneX() + 200, event.getSceneY() + 35);
                            CardActionsMenu.execute(rectangle, gameController);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                if (event.getButton() == MouseButton.PRIMARY) {
                    Cell selectedCell = Cell.getSelectedCell();
                    ImagePattern rectangleImage = (ImagePattern) rectangle.getFill();
                    if (selectedCell != null && !selectedCell.isEmpty()) {
                        selectedCell.getCellRectangle().setEffect(null);
                        CardActionsMenu.close();
                    }
                    if (selectedCell != null && selectedCell.getCellCard() != null &&
                            (selectedCell.getCellCard().getCardImagePattern().equals(rectangleImage) ||
                                    selectedCell.getCellCard().getCardBackImagePattern().equals(rectangleImage))) {
                        CardActionsMenu.close();
                        Cell.deselectCell();
                    } else if (CardActionsMenu.getActiveSword() == null) {
                        selectCard(rectangle);
                        if (!gameController.currentTurnOpponentPlayer.getGameBoard().isCellInGameBoard(Cell.getSelectedCell())
                                && !gameController.currentTurnPlayer.getGameBoard().isCellInDeckZone(Cell.getSelectedCell())) {
                            try {
                                CardActionsMenu.setCoordinates(event.getSceneX() + 200, event.getSceneY() + 35);
                                CardActionsMenu.setLastMousePositionX(event.getSceneX() - 700);
                                CardActionsMenu.setLastMousePositionY(200);
                                CardActionsMenu.execute(rectangle, gameController);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    event.consume();
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    if (Cell.getSelectedCell() != null && (Cell.getSelectedCell().getCellCard().getCardImagePattern().equals
                            (rectangle.getFill()) || Cell.getSelectedCell().getCellCard().getCardBackImagePattern().equals
                            (rectangle.getFill()))) {
                        CardActionsMenu.change();
                    }
                    event.consume();
                }
//            if (event.getButton() == MouseButton.PRIMARY) {
//                Cell selectedCell = Cell.getSelectedCell();
//                ImagePattern rectangleImage = (ImagePattern) rectangle.getFill();
//                if (selectedCell != null && !selectedCell.isEmpty()) {
//                    selectedCell.getCellRectangle().setEffect(null);
//                    CardActionsMenu.close();
//                }
//                if (selectedCell != null && selectedCell.getCellCard() != null &&
//                        (selectedCell.getCellCard().getCardImagePattern().equals(rectangleImage) ||
//                                selectedCell.getCellCard().getCardBackImagePattern().equals(rectangleImage))) {
//                    CardActionsMenu.close();
//                    Cell.deselectCell();
//                } else if (CardActionsMenu.getActiveSword() == null) {
//                    selectCard(rectangle);
//                    if (!gameController.currentTurnOpponentPlayer.getGameBoard().isCellInGameBoard(Cell.getSelectedCell())
//                            && !gameController.currentTurnPlayer.getGameBoard().isCellInDeckZone(Cell.getSelectedCell())) {
//                        try {
//                            CardActionsMenu.setCoordinates(event.getSceneX() + 195, event.getSceneY() + 60);
//                            CardActionsMenu.setLastMousePositionX(event.getSceneX() - 700);
//                            CardActionsMenu.setLastMousePositionY(200);
//                            CardActionsMenu.execute(rectangle, gameController);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                event.consume();
//            } else if (event.getButton() == MouseButton.SECONDARY) {
//                if (Cell.getSelectedCell() != null && (Cell.getSelectedCell().getCellCard().getCardImagePattern().equals
//                        (rectangle.getFill()) || Cell.getSelectedCell().getCellCard().getCardBackImagePattern().equals
//                        (rectangle.getFill()))) {
//                    CardActionsMenu.change();
//                }
//                event.consume();
//            }
//        }});
            }
        });
    }


    public void selectCard(Rectangle rectangle) {
        DropShadow selectEffect = new DropShadow(BlurType.values()[1],
                GREEN, 10, 2.0f, 0, 0);
        selectEffect.setBlurType(BlurType.ONE_PASS_BOX);
        Cell.setSelectedCellByRectangle(rectangle);
        rectangle.setEffect(selectEffect);
    }

    public void showGraveyardForUser() {
        try {
            if (Graveyard.getGraveyardStage().isShowing()) {
                Graveyard.getGraveyardStage().close();
                return;
            }
        } catch (Exception ignored) {
        }

        if (CardActionsMenu.isBoardInverse())
            new Graveyard().execute(Duel.getGameController().getCurrentTurnOpponentPlayer().getGameBoard().getGraveyard());
        else
            new Graveyard().execute(Duel.getGameController().getCurrentTurnPlayer().getGameBoard().getGraveyard());
    }

    public void closeGraveyard() {
        Graveyard.close();
    }

    public void showGraveyardForOpponent() {
        try {
            if (Graveyard.getGraveyardStage().isShowing()) {
                Graveyard.getGraveyardStage().close();
                return;
            }
        } catch (Exception ignored) {
        }

        if (CardActionsMenu.isBoardInverse())
            new Graveyard().execute(Duel.getGameController().getCurrentTurnPlayer().getGameBoard().getGraveyard());
        else
            new Graveyard().execute(Duel.getGameController().getCurrentTurnOpponentPlayer().getGameBoard().getGraveyard());
    }

    public void focusOpacityOnPhase(GamePhase gamePhase) {
        double length = 0.1;
        if (gamePhase == GamePhase.DRAW) length = 3.5;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(length), event -> {
            dpLabel.setEffect(null);
            spLabel.setEffect(null);
            m1Label.setEffect(null);
            bpLabel.setEffect(null);
            m2Label.setEffect(null);
            epLabel.setEffect(null);
            dpLabel.setOpacity(0.5);
            spLabel.setOpacity(0.5);
            m1Label.setOpacity(0.5);
            bpLabel.setOpacity(0.5);
            m2Label.setOpacity(0.5);
            epLabel.setOpacity(0.5);
            Glow glow = new Glow();
            glow.setLevel(5);
            switch (gamePhase) {
                case DRAW:
                    dpLabel.setOpacity(1);
                    dpLabel.setEffect(glow);
                    break;
                case STANDBY:
                    spLabel.setOpacity(1);
                    spLabel.setEffect(glow);
                    break;
                case MAIN1:
                    m1Label.setOpacity(1);
                    m1Label.setEffect(glow);
                    break;
                case BATTLE:
                    bpLabel.setOpacity(1);
                    bpLabel.setEffect(glow);
                    break;
                case MAIN2:
                    m2Label.setOpacity(1);
                    m2Label.setEffect(glow);
                    break;
                case END:
                    epLabel.setOpacity(1);
                    epLabel.setEffect(glow);
                    break;
            }
        }));
        timeline.play();
    }

    public void nextPhase() {
        Duel.getGameController().changePhase();
    }

    public Label getEpLabel() {
        return epLabel;
    }

    public ImageView getBackground() {
        return background;
    }

    public void rotateSettings() {
        settingImage.rotateProperty().setValue(settingImage.rotateProperty().getValue() + 45);
    }

    public void rotateBackSettings() {
        settingImage.rotateProperty().setValue(settingImage.rotateProperty().getValue() - 15);
    }

    public void setHoveredImageChat() {
        chatImage.setImage(new Image(new File("src/resources/yugioh/PNG/Field/chatHoverIcon.png").toURI().toString()));
    }

    public void resetHoveredImageChat() {
        chatImage.setImage(new Image(new File("src/resources/yugioh/PNG/Field/chatIcon.png").toURI().toString()));
    }
}
