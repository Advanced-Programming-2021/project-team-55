package yugioh.controller.menucontroller;


import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.board.Cell;
import yugioh.model.cards.Card;
import yugioh.model.cards.Monster;
import yugioh.view.gamephases.CardActionsMenu;
import yugioh.view.gamephases.Duel;
import yugioh.view.menus.WelcomeMenu;
import yugioh.view.gamephases.GamePhase;
import yugioh.view.gamephases.Graveyard;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.GREEN;

public class GameMenuController extends MenuController implements Initializable {

    private static GameMenuController gameMenuController;

    public ImageView hoveredImage;
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
    private Stage pauseStage;

    public static GameMenuController getGameMenuController() {
        return gameMenuController;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Duel.getGameController().setGameMenuController(this);
        this.gameController = Duel.getGameController();
        updateGameStatusUIs();
        updateCells();
        gameMenuController = this;
        userHandCardsContainer.setPadding(new Insets(0, 30, 0, 30));
        rivalHandCardsContainer.setPadding(new Insets(0, 30, 0, 30));
        userDeckZoneContainer.setPadding(new Insets(0,0,0,0));
        userHandCardsContainer.setSpacing(17);
        rivalHandCardsContainer.setSpacing(17);
        hoveredImage.setImage(Card.getCardImage(null, 354).getImage());
        description.setWrapText(true);
        description.setTextAlignment(TextAlignment.JUSTIFY);
    }

    private void updateCells() {
        for(Cell cell:Cell.getAllCells()){
            if(!cell.isEmpty())
            addEventForCardImage(cell.getCellCard().getCardImage(), cell.getCellCard());
        }
    }

    public void pauseClicked() throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/PauseMenu.fxml");
        Pane pane= FXMLLoader.load(url);
        pane.getChildren().get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                resume();
            }
        });
        pane.getChildren().get(1).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    surrender();
                }catch (Exception e){};
            }
        });
        Scene scene = WelcomeMenu.createScene(pane);
        pauseStage = new Stage();
        scene.getStylesheets().add(
                getClass().getResource("/yugioh/CSS/Menu.css").toExternalForm());
        pauseStage.setScene(scene);
        pauseStage.initModality(Modality.APPLICATION_MODAL);
        pauseStage.show();

    }

    public void resume() {
        pauseStage.close();
    }

    public void surrender() throws Exception {
        gameController.surrender();
        pauseStage.close();
        gameController.surrender();
        gameController.endGameRound();
    }

    public void updateGameStatusUIs() {
        GameController gameController=Duel.getGameController();
        int opponentLP = gameController.currentTurnOpponentPlayer.getLP();
        int myLP = gameController.currentTurnPlayer.getLP();
        rivalLP.setText(opponentLP + "");
        userLP.setText(myLP + "");
        opponentUsername.setText(opponentUsername.getText()+gameController.currentTurnOpponentPlayer.
                getUser().getUsername());
        opponentNickname.setText(opponentNickname.getText()+gameController.currentTurnOpponentPlayer.
                getUser().getNickname());
        currentUsername.setText(currentUsername.getText()+gameController.currentTurnPlayer.
                getUser().getUsername());
        currentNickname.setText(currentNickname.getText()+gameController.currentTurnPlayer.
                getUser().getNickname());
        currentImage.setImage(new Image(gameController.currentTurnPlayer.getUser().getProfileImageString()));
        currentImage.setPreserveRatio(true);
        currentImage.setFitWidth(50);
        opponentImage.setImage(new Image(gameController.currentTurnOpponentPlayer.getUser().getProfileImageString()));
        opponentImage.setPreserveRatio(true);
        opponentImage.setFitWidth(50);
        rivalLPBar.setProgress((double) opponentLP / 8000);
        userLPBar.setProgress((double) myLP / 8000);
    }


    public void addEventForCardImage(ImageView imageView, Card card) {
        imageView.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            Platform.runLater(() -> hoveredImage.setImage(imageView.getImage()));
            if (card == null){
                Platform.runLater(() -> description.setText(""));
            }
            else{
                Platform.runLater(() -> description.setText(card.getDescription()));
            if (card instanceof Monster) {
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
            }});
        CardActionsMenu cardActionsMenu=new CardActionsMenu();
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
            if(Cell.getSelectedCell()!=null&&!Cell.getSelectedCell().isEmpty()) {
                Cell.getSelectedCell().getCellCard().getCardImage().setEffect(null);
                Cell.getSelectedCell().getCellCard().getCardBackImage().setEffect(null);
            }
            if(Cell.getSelectedCell()!=null&&Cell.getSelectedCell().getCellCard().getCardImage().equals(imageView)){
                Cell.setSelectedCell(null);
            }else {
                DropShadow selectEffect = new DropShadow(BlurType.values()[1],
                        GREEN, 10, 2.0f, 0, 0);
                selectEffect.setBlurType(BlurType.ONE_PASS_BOX);
                Cell.setSelectedCellByImage(imageView);
                imageView.setEffect(selectEffect);
                try {
                    cardActionsMenu.execute();
                }catch (Exception e){e.printStackTrace();}
            }
            event.consume();
        });
    }

    public void showGraveyardForUser() {
        new Graveyard().execute(Duel.getGameController().getCurrentTurnPlayer().getGameBoard().getGraveyard());
    }

    public void closeGraveyard() {
        Graveyard.close();
    }

    public void showGraveyardForOpponent() {
        new Graveyard().execute(Duel.getGameController().getCurrentTurnOpponentPlayer().getGameBoard().getGraveyard());
    }

    public void focusOpacityOnPhase(GamePhase gamePhase) {
        dpLabel.setOpacity(0.5);
        spLabel.setOpacity(0.5);
        m1Label.setOpacity(0.5);
        bpLabel.setOpacity(0.5);
        m2Label.setOpacity(0.5);
        epLabel.setOpacity(0.5);
        switch (gamePhase) {
            case DRAW:
                dpLabel.setOpacity(1);
                break;
            case STANDBY:
                spLabel.setOpacity(1);
                break;
            case MAIN1:
                m1Label.setOpacity(1);
                break;
            case BATTLE:
                bpLabel.setOpacity(1);
                break;
            case MAIN2:
                m2Label.setOpacity(1);
                break;
            case END:
                epLabel.setOpacity(1);
                break;
        }
    }
}
