package yugioh.controller.menucontroller;


import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.paint.Color;
import yugioh.model.User;
import yugioh.model.cards.Card;
import yugioh.view.menus.DeckMenu;
import yugioh.view.menus.EditDeckMenu;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class EditDeckMenuController implements Initializable {//todo debug

    public Label deckName;
    public ScrollPane mainDeck;
    public ScrollPane sideDeck;
    public ScrollPane inventory;
    public Label selectedCardName;
    public Button toAndFromMainDeck;
    public Button toAndFromSideDeck;

    private Card selectedCard;
    private ImageView selectedCardImageView;

    private ArrayList<Card> inventoryCards;

    public void back() throws Exception {
        new DeckMenu().execute();
    }

    public void initializeCardsPane(ArrayList<Card> cards, ScrollPane scrollPane) {
        int cardsPerRow = 6;
        int columnCounter = 0;
        GridPane cardsPane = new GridPane();
        cardsPane.setHgap(10);
        cardsPane.setVgap(10);
        cardsPane.setPadding(new Insets(10, 10, 10, 10));
        outer:
        while (cards.size() > 0) {
            for (int j = 0; j < cardsPerRow; j++) {
                Card card = cards.get(cards.size() - 1);
                ImageView cardImage = card.getCardImageForDeck(52);
                DropShadow selectEffect = new DropShadow(BlurType.values()[1],
                        Color.GREEN, 10, 2.0f, 0, 0);
                cardImage.focusedProperty().addListener((ObservableValue<? extends Boolean> observable,
                                                         Boolean oldValue, Boolean newValue) -> {
                    if (newValue) {
                        selectedCardImageView = cardImage;
                        cardImage.setEffect(selectEffect);
                    } else {
                        cardImage.setEffect(null);
                        selectedCardImageView.setEffect(null);
                    }
                });
                cardImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    cardImage.requestFocus();
                    Platform.runLater(() -> selectedCardName.setText(card.getName()));
                    if (scrollPane == inventory) {
                        Platform.runLater(() -> toAndFromSideDeck.setDisable(false));
                        Platform.runLater(() -> toAndFromMainDeck.setDisable(false));
                        Platform.runLater(() -> toAndFromMainDeck.setText("<"));
                        Platform.runLater(() -> toAndFromSideDeck.setText("<"));
                    } else if (scrollPane == mainDeck) {
                        Platform.runLater(() -> toAndFromMainDeck.setText(">"));
                        Platform.runLater(() -> toAndFromMainDeck.setDisable(false));
                        Platform.runLater(() -> toAndFromSideDeck.setDisable(true));
                    } else if (scrollPane == sideDeck) {
                        Platform.runLater(() -> toAndFromSideDeck.setText(">"));
                        Platform.runLater(() -> toAndFromSideDeck.setDisable(false));
                        Platform.runLater(() -> toAndFromMainDeck.setDisable(true));
                    }
                    selectedCard = card;
                    selectedCardImageView = cardImage;
                    event.consume();
                });
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
        selectedCardName.setText("");
        deckName.setText(EditDeckMenu.getDeck().getName());
        ArrayList<Card> cardsInventory = new ArrayList<>(User.loggedInUser.getCardsInventory());
        ArrayList<Card> mainDeckCopy = new ArrayList<>(EditDeckMenu.getDeck().getMainDeck());
        ArrayList<Card> sideDeckCopy = new ArrayList<>(EditDeckMenu.getDeck().getSideDeck());
        removeRepeatedCards(cardsInventory, mainDeckCopy);
        removeRepeatedCards(cardsInventory, sideDeckCopy);
        initializeCardsPane(new ArrayList<>(EditDeckMenu.getDeck().getMainDeck()), mainDeck);
        initializeCardsPane(new ArrayList<>(EditDeckMenu.getDeck().getSideDeck()), sideDeck);
        initializeCardsPane(new ArrayList<>(cardsInventory), inventory);
        inventoryCards = cardsInventory;
    }

    private void removeRepeatedCards(ArrayList<Card> cardsInventory, ArrayList<Card> sideDeckCopy) {
        for (int i = cardsInventory.size() - 1; i >= 0; i--) {
            Card card = cardsInventory.get(i);
            Card correspondCard = Card.getArrayListCard(card.getName(), sideDeckCopy);
            if (correspondCard != null) {
                sideDeckCopy.remove(correspondCard);
                cardsInventory.remove(card);
            }
        }
    }

    public void moveToOrFormMainDeck() {
        if (toAndFromMainDeck.getText().equals(">")) {
            EditDeckMenu.getDeck().getMainDeck().remove(selectedCard);
            inventoryCards.add(selectedCard);
        } else {
            EditDeckMenu.getDeck().getMainDeck().add(selectedCard);
            inventoryCards.remove(selectedCard);
        }
        initializeCardsPane(new ArrayList<>(EditDeckMenu.getDeck().getMainDeck()), mainDeck);
        initializeCardsPane(new ArrayList<>(inventoryCards), inventory);
    }

    public void moveToOrFormSideDeck() {
        if (toAndFromSideDeck.getText().equals(">")) {
            EditDeckMenu.getDeck().getSideDeck().remove(selectedCard);
            inventoryCards.add(selectedCard);
        } else {
            EditDeckMenu.getDeck().getSideDeck().add(selectedCard);
            inventoryCards.remove(selectedCard);
        }
        initializeCardsPane(new ArrayList<>(EditDeckMenu.getDeck().getSideDeck()), sideDeck);
        initializeCardsPane(new ArrayList<>(inventoryCards), inventory);
    }

}
