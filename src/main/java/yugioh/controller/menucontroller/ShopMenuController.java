package yugioh.controller.menucontroller;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import yugioh.model.User;
import yugioh.model.cards.Card;
import yugioh.model.exceptions.MenuException;
import yugioh.view.Responses;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShopMenuController extends MenuController implements Initializable {

    public static ShopMenuController shopMenuController;
    public ScrollPane scrollPane;
    public ImageView hoveredImage;
    public Button buyButton;
    public Text descriptionScrollPane;
    public ScrollPane cardDescription;

    public ShopMenuController() {
    }

    public static ShopMenuController getInstance() {
        if (shopMenuController == null) shopMenuController = new ShopMenuController();
        return shopMenuController;
    }

    public void buyCard(String cardName) throws MenuException {
        Card card = Card.getCardByName(cardName);
        if (card == null) {
            throw new MenuException(Responses.NO_CARD_EXISTS.response);
        } else if (User.loggedInUser.getMoney() < card.getPrice()) {
            throw new MenuException(Responses.NOT_ENOUGH_MONEY.response);
        } else {
            User.loggedInUser.changeMoney(-card.getPrice());
            ArrayList<Card> cardsToAdd = new ArrayList<>();
            cardsToAdd.add(card);
            User.loggedInUser.addCardsToInventory(cardsToAdd);
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
        int cardsPerRow = 6;
        int columnCounter = 0;
        GridPane cardsPane = new GridPane();
        cardsPane.setHgap(10);
        cardsPane.setVgap(10);
        outer:
        while (allCards.size() > 0) {
            for (int j = 0; j < cardsPerRow; j++) {
                Card card = allCards.get(allCards.size() - 1);
                ImageView cardImage = Card.getCardImage(card, 86);
                cardImage.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                    Platform.runLater(() -> hoveredImage.setImage(cardImage.getImage()));
                    Platform.runLater(() -> descriptionScrollPane.setText(card.getDescription()));//todo uncomment
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
        descriptionScrollPane.wrappingWidthProperty().bind(cardDescription.widthProperty());
        cardDescription.setFitToWidth(true);
    }

}
