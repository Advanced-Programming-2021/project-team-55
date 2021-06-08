package yugioh.controller.menucontroller;

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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import yugioh.model.User;
import yugioh.model.cards.Card;
import yugioh.model.exceptions.MenuException;
import yugioh.view.Menus.PopUpWindow;
import yugioh.view.Menus.WelcomeMenu;
import yugioh.view.Responses;

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
        Card card = selectedCard;
        try {
            if (card == null) {
                throw new MenuException(Responses.NO_CARD_EXISTS.response);
            } else if (User.loggedInUser.getMoney() < card.getPrice()) {
                throw new MenuException(Responses.NOT_ENOUGH_MONEY.response);
            } else if (User.loggedInUser.getCardsInventory().contains(selectedCard)) {
                throw new MenuException("Error: you already owned this card!");
            } else {
                User.loggedInUser.changeMoney(-card.getPrice());
                ArrayList<Card> cardsToAdd = new ArrayList<>();
                cardsToAdd.add(card);
                User.loggedInUser.addCardsToInventory(cardsToAdd);
                Platform.runLater(() -> userCoins.setText(User.loggedInUser.getMoney() + ""));
                selectedCardImageView.setOpacity(0.5);
                isCardClicked = false;
            }
        }catch (Exception e){
            new PopUpWindow(e.getMessage()).start(WelcomeMenu.stage);
        }
    }

    public ArrayList<Card> getAllCards() {
        return Card.sortCards(Card.getCards());
    }

    public void back() throws Exception {
        mainMenu.execute();
    }

    private void initializeCardsPane() {
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
                if (User.loggedInUser.getCardsInventory().contains(card)) cardImage.setOpacity(0.5);
                cardImage.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                    if (isCardClicked) return;
                    Platform.runLater(() -> hoveredImage.setImage(cardImage.getImage()));
                    Platform.runLater(() -> description.setText(card.getDescription()));
                    Platform.runLater(() -> buyButton.setText("BUY (" + card.getPrice() + ")"));
                    selectedCard = card;
                    selectedCardImageView = cardImage;
                    event.consume();
                });
                DropShadow selectEffect = new DropShadow(BlurType.values()[1],
                        GREEN, 10, 2.0f, 0, 0);
                cardImage.focusedProperty().addListener((ObservableValue<? extends Boolean> observable,
                                                         Boolean oldValue, Boolean newValue) -> {
                    isCardClicked = true;
                    if (newValue) {
                        selectedCardImageView=cardImage;
                        cardImage.setEffect(selectEffect);
                    } else {
                        cardImage.setEffect(null);
                        if(selectedCardImageView!=null)
                            selectedCardImageView = new ImageView(selectedCardImageView.getImage());
                    }
                });
                cardImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    cardImage.requestFocus();
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

}
