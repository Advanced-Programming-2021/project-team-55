package yugioh.controller.menucontroller;


import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import yugioh.model.User;
import yugioh.model.board.Cell;
import yugioh.model.cards.Card;
import yugioh.model.cards.Monster;
import yugioh.view.Menus.DuelMenu;
import yugioh.view.gamephases.Duel;

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
    public Pane gameBoardPane;
    public HBox rivalHandCardsContainer;
    public HBox userHandCardsContainer;
    public Pane rivalDeckZoneContainer;
    public Pane userDeckZoneContainer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateGameStatusUIs();
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

    public void backClicked() throws Exception {
        new DuelMenu().execute();
    }

    public void updateGameStatusUIs() {
        int opponentLP = Duel.getGameController().currentTurnOpponentPlayer.getLP();
        int myLP = Duel.getGameController().currentTurnPlayer.getLP();
        rivalLP.setText(opponentLP + "");
        userLP.setText(myLP + "");
        rivalLPBar.setProgress((double) opponentLP / 8000);
        userLPBar.setProgress((double) myLP / 8000);
    }

    public static GameMenuController getGameMenuController() {
        return gameMenuController;
    }

    public void addEventForCardImage(ImageView imageView, Card card) {
        imageView.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            Platform.runLater(() -> hoveredImage.setImage(imageView.getImage()));
            if (card == null){
                Platform.runLater(() -> description.setText(""));
                return;
            }
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
        });
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
            DropShadow selectEffect = new DropShadow(BlurType.values()[1],
                    GREEN, 10, 2.0f, 0, 0);
            selectEffect.setBlurType(BlurType.ONE_PASS_BOX);
            imageView.setEffect(selectEffect);
            //here
        });
    }

}
