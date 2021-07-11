package controller;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import model.cards.Card;
import model.cards.CardsInventory;
import view.AdminWelcomeMenu;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.GREEN;

public class AdminShopMenuController implements Initializable {
    public ScrollPane cardsScrollPane;
    public ImageView hoveredImage;
    public Label description;
    public ScrollPane descriptionContainer;
    public Label numberOfCard;
    public Button plusButton;
    public Button minusButton;
    public TextField changeAmount;
    public CheckBox forbidBox;
    private boolean isCardClicked=false;
    private Card selectedCard;
    private ImageView selectedCardImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        minusButton.setDisable(true);
        plusButton.setDisable(true);
        description.setWrapText(true);
        forbidBox.setDisable(true);
        description.setTextAlignment(TextAlignment.JUSTIFY);
        changeAmount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1.equals("")||!t1.matches("(\\d+)")){
                    minusButton.setDisable(true);
                    plusButton.setDisable(true);
                }
                else {
                    if(Integer.parseInt(numberOfCard.getText())<Integer.parseInt(t1)){
                        minusButton.setDisable(true);
                    }
                    else
                    minusButton.setDisable(false);
                    plusButton.setDisable(false);
                }
            }
        });
        initializeCardsPane();
    }

    private void initializeCardsPane() {
        ArrayList<Card>allCards=new ArrayList<>(Card.getCards());
        int cardsPerRow = 6;
        int columnCounter = 0;
        GridPane cardsPane = new GridPane();
        cardsPane.setHgap(10);
        cardsPane.setVgap(10);
        cardsPane.setPadding(new Insets(10, 10, 10, 10));
        outer:
        while (allCards.size() > 0) {
            for (int j = 0; j < cardsPerRow; j++) {
                Card card = allCards.get(allCards.size() - 1);
                ImageView cardImage = Card.getCardImage(card, 90);
                cardImage.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                    if (isCardClicked) return;
                    Platform.runLater(() -> hoveredImage.setImage(cardImage.getImage()));
                    Platform.runLater(() -> description.setText(card.getDescription()));
                    Platform.runLater(() -> updateNumberOfCards(card));
                    Platform.runLater(()-> {
                        if (card.isForbid()) {
                            forbidBox.setSelected(true);
                        }
                        else{
                            forbidBox.setSelected(false);
                        }
                    });
                    event.consume();
                });
                DropShadow selectEffect = new DropShadow(BlurType.values()[1],
                        GREEN, 10, 2.0f, 0, 0);

                cardImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if(selectedCardImageView==null||!cardImage.equals(selectedCardImageView)) {
                        isCardClicked = true;
                        if(selectedCardImageView!=null){
                            selectedCardImageView.setEffect(null);
                        }
                        selectedCardImageView = cardImage;
                        cardImage.setEffect(selectEffect);
                        Platform.runLater(() -> hoveredImage.setImage(cardImage.getImage()));
                        Platform.runLater(() -> description.setText(card.getDescription()));
                        Platform.runLater(()-> {
                            forbidBox.setDisable(false);
                            if (card.isForbid()) {
                                forbidBox.setSelected(true);
                            }
                            else{
                                forbidBox.setSelected(false);
                            }
                        });
                        updateNumberOfCards(card);
                        selectedCard = card;
                        selectedCardImageView = cardImage;
                    } else {
                        forbidBox.setDisable(true);
                        cardImage.setEffect(null);
                        selectedCardImageView.setEffect(null);
                        selectedCardImageView=null;
                        isCardClicked = false;
                        minusButton.setDisable(true);
                        plusButton.setDisable(true);
                    }
                    event.consume();
                    event.consume();
                });
                cardsPane.add(cardImage, j, columnCounter);
                allCards.remove(card);
                if (allCards.size() == 0) break outer;
            }
            columnCounter++;
        }
        cardsScrollPane.contentProperty().set(cardsPane);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new AdminWelcomeMenu().start(AdminWelcomeMenu.stage);
    }

    public void increaseCount(MouseEvent mouseEvent) {
        CardsInventory.inventory.addCardToInventory(selectedCard,Integer.parseInt(changeAmount.getText()));
        changeAmount.setText("");
        updateNumberOfCards(selectedCard);
    }
    public void updateNumberOfCards(Card card){
        Platform.runLater(()->{numberOfCard.setText(String.valueOf(CardsInventory.inventory.cardsInventory.get(card.getName())));});
    }

    public void decreaseCount(MouseEvent mouseEvent) {
        CardsInventory.inventory.removeCardFromInventory(selectedCard,Integer.parseInt(changeAmount.getText()));
        changeAmount.setText("");
        updateNumberOfCards(selectedCard);
    }

    public void forbid() {
        selectedCard.setForbid(true);

    }

    public void unForbid() {
        selectedCard.setForbid(false);
    }

    public void changeForbid(ActionEvent actionEvent) {
        if(selectedCard.isForbid()){
            unForbid();
        }
        else{
            forbid();
        }
    }
}
