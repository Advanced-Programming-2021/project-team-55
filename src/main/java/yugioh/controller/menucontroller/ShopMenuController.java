package yugioh.controller.menucontroller;

import javafx.fxml.Initializable;
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
    public GridPane cardsPane;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shopMenuController = this;
        ArrayList<Card> allCards = getAllCards();
        int cardsPerRow = 6;
        int columnCounter = 0;
        while (allCards.size() > 0) {
            for (int j = 0; j < cardsPerRow; j++) {
                cardsPane.add(new Text(allCards.get(0).getName()), j, columnCounter);
            }
            columnCounter++;
        }
    }
}
