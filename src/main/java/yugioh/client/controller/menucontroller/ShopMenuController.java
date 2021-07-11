package yugioh.client.controller.menucontroller;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.TextAlignment;
import yugioh.client.model.User;
import yugioh.client.model.cards.Card;
import yugioh.client.model.exceptions.MenuException;
import yugioh.client.view.Responses;
import yugioh.client.view.SoundPlayable;
import yugioh.client.view.menus.PopUpWindow;
import yugioh.client.view.menus.ShopMenu;
import yugioh.client.view.menus.WelcomeMenu;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.GREEN;

public class ShopMenuController extends MenuController implements Initializable {

    public static ShopMenuController shopMenuController;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public ImageView hoveredImage;
    @FXML
    public Button buyButton;
    @FXML
    public Label description;
    @FXML
    public ScrollPane descriptionContainer;
    public Label userCoins;
    public Label numberOfCard;
    public MediaView shopMenuBackground;
    private Card selectedCard;
    private ImageView selectedCardImageView;
    private boolean isCardClicked = false;

    public ShopMenuController() {
    }

    public static ShopMenuController getInstance() {
        if (shopMenuController == null) shopMenuController = new ShopMenuController();
        return shopMenuController;
    }

    public void buyCard() throws Exception {
        SoundPlayable.playButtonSound("enterButton");
        Card card = selectedCard;
        try {
            if (card == null) {
                throw new MenuException(Responses.NO_CARD_EXISTS.response);
            } else if (User.loggedInUser.getMoney() < card.getPrice()) {
                throw new MenuException(Responses.NOT_ENOUGH_MONEY.response);
            } else {
                User.loggedInUser.changeMoney(-card.getPrice());
                ArrayList<Card> cardsToAdd = new ArrayList<>();
                cardsToAdd.add(card);
                User.loggedInUser.addCardsToInventory(cardsToAdd);
                Platform.runLater(() -> userCoins.setText(User.loggedInUser.getMoney() + ""));
                Platform.runLater(() -> numberOfCard.setText(User.loggedInUser.getNumberOfSpecificCard(card.getName()) + ""));
                isCardClicked = false;
                selectedCardImageView.setOpacity(1);
                new PopUpWindow("Card added successfully").start(ShopMenu.stage);
            }
        } catch (Exception e) {
            new PopUpWindow(e.getMessage()).start(WelcomeMenu.stage);
        }
    }

    public ArrayList<Card> getAllCards() {
        return Card.sortCards(Card.getCards());
    }

    public void back() throws Exception {
        SoundPlayable.playButtonSound("backButton");
        mainMenu.execute();
    }

    private void initializeCardsPane() {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src\\resources\\yugioh\\Backgrounds\\main.mp4").toURI().toString()));
        mediaPlayer.play();
        mediaPlayer.setCycleCount(-1);
        shopMenuBackground.setMediaPlayer(mediaPlayer);
        ArrayList<Card> allCards = new ArrayList<>(getAllCards());
        int cardsPerRow = 7;
        int columnCounter = 0;
        GridPane cardsPane = new GridPane();
        cardsPane.setHgap(10);
        cardsPane.setVgap(10);
        cardsPane.setPadding(new Insets(10, 10, 10, 10));
        Platform.runLater(() -> userCoins.setText(User.loggedInUser.getMoney() + ""));
        outer:
        while (allCards.size() > 0) {
            for (int j = 0; j < cardsPerRow; j++) {
                Card card = allCards.get(allCards.size() - 1);
                ImageView cardImage = Card.getCardImage(card, 86);
                if (!User.loggedInUser.cardExistsInInventory(card.getName())) cardImage.setOpacity(0.5);
                cardImage.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                    if (isCardClicked) return;
                    Platform.runLater(() -> hoveredImage.setImage(cardImage.getImage()));
                    Platform.runLater(() -> description.setText(card.getDescription()));
                    Platform.runLater(() -> numberOfCard.setText(User.loggedInUser.getNumberOfSpecificCard(card.getName()) + ""));
                    buyButton.setDisable(card.getPrice() > User.loggedInUser.getMoney());
                    Platform.runLater(() -> buyButton.setText("BUY (" + card.getPrice() + ")"));
                    selectedCard = card;
                    selectedCardImageView = cardImage;
                    event.consume();
                });
                DropShadow selectEffect = new DropShadow(BlurType.values()[1],
                        GREEN, 10, 2.0f, 0, 0);
                cardImage.focusedProperty().addListener((ObservableValue<? extends Boolean> observable,
                                                         Boolean oldValue, Boolean newValue) -> {
                    if (newValue) {
                        isCardClicked = true;
                        selectedCardImageView = cardImage;
                        cardImage.setEffect(selectEffect);
                    } else {
                        cardImage.setEffect(null);
                        selectedCardImageView.setEffect(null);
                        isCardClicked = false;
                    }
                });
                cardImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    cardImage.requestFocus();
                    Platform.runLater(() -> hoveredImage.setImage(cardImage.getImage()));
                    Platform.runLater(() -> description.setText(card.getDescription()));
                    buyButton.setDisable(card.getPrice() > User.loggedInUser.getMoney());
                    Platform.runLater(() -> buyButton.setText("BUY (" + card.getPrice() + ")"));
                    selectedCard = card;
                    selectedCardImageView = cardImage;
                    event.consume();
                    event.consume();
                });
                cardsPane.add(cardImage, j, columnCounter);
                allCards.remove(card);
                if (allCards.size() == 0) break outer;
            }
            columnCounter++;
        }
        scrollPane.contentProperty().set(cardsPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shopMenuController = this;
        initializeCardsPane();
        hoveredImage.setImage(Card.getCardImage(null, 354).getImage());
        description.setWrapText(true);
        description.setTextAlignment(TextAlignment.JUSTIFY);
    }

    public void enterCardGeneratorMenu(MouseEvent mouseEvent) throws Exception {
        SoundPlayable.playButtonSound("enter");
        cardGeneratorMenu.execute();

    }
}
