package yugioh.controller.gamephasescontrollers;


import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import yugioh.model.cards.Card;
import yugioh.view.gamephases.Graveyard;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class GraveyardController implements Initializable {

    public ScrollPane graveyard;

    private void initializeCardsPane(ArrayList<Card> cards, ScrollPane scrollPane) {
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
                if (card == null) {
                    cards.remove(cards.size() - 1);
                    continue;
                }
                ImageView cardImage = card.getCardImageForDeck(52);
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
        initializeCardsPane(Graveyard.getGraveyardCards(), graveyard);
    }

}
