package yugioh.controller.menucontroller;


import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import yugioh.model.User;
import yugioh.model.cards.Card;
import yugioh.view.Menus.EditDeckMenu;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class EditDeckMenuController implements Initializable {

    public Label deckName;
    public ScrollPane mainDeck;
    public ScrollPane sideDeck;
    public ScrollPane inventory;

    public void back() {
        EditDeckMenu.getStage().close();
    }

    private void initializeCardsPane(ArrayList<Card> cards, ScrollPane scrollPane) {
        int cardsPerRow = 5;
        int columnCounter = 0;
        GridPane cardsPane = new GridPane();
        cardsPane.setHgap(10);
        cardsPane.setVgap(10);
        cardsPane.setPadding(new Insets(10, 10, 10, 10));
        outer:
        while (cards.size() > 0) {
            for (int j = 0; j < cardsPerRow; j++) {
                Card card = cards.get(cards.size() - 1);
                ImageView cardImage = Card.getCardImage(card, 40);
                /*cardImage.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                    if (isCardClicked) return;
                    Platform.runLater(() -> hoveredImage.setImage(cardImage.getImage()));
                    Platform.runLater(() -> description.setText(card.getDescription()));
                    Platform.runLater(() -> numberOfCard.setText(User.loggedInUser.getNumberOfSpecificCard(card.getName()) + ""));
                    if(card.getPrice()>User.loggedInUser.getMoney()){
                        buyButton.setDisable(true);
                    }
                    else{
                        buyButton.setDisable(false);
                    }
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
                    if(card.getPrice()>User.loggedInUser.getMoney()){
                        buyButton.setDisable(true);
                    }
                    else{
                        buyButton.setDisable(false);
                    }
                    Platform.runLater(() -> buyButton.setText("BUY (" + card.getPrice() + ")"));
                    selectedCard = card;
                    selectedCardImageView = cardImage;
                    event.consume();
                    event.consume();
                });*/
                cardsPane.add(cardImage, j, columnCounter);
                cards.remove(card);
                if (cards.size() == 0) break outer;
            }
            columnCounter++;
        }
        scrollPane.contentProperty().set(cardsPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCardsPane(new ArrayList<>(EditDeckMenu.getDeck().getMainDeck()), mainDeck);
        initializeCardsPane(new ArrayList<>(EditDeckMenu.getDeck().getSideDeck()), sideDeck);
        initializeCardsPane(new ArrayList<>(User.loggedInUser.getCardsInventory()), inventory);
    }
}
