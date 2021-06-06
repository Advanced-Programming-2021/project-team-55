package yugioh.controller.menucontroller;

import javafx.scene.input.MouseEvent;
import yugioh.model.exceptions.MenuException;
import yugioh.model.User;
import yugioh.model.cards.Card;
import yugioh.view.Menus.WelcomeMenu;
import yugioh.view.Menus.MenuType;
import yugioh.view.Responses;

import java.util.ArrayList;

public class ShopMenuController extends MenuController {

    public static ShopMenuController shopMenuController;

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

    public void backClicked(MouseEvent mouseEvent) throws Exception{
        mainMenu.execute();
    }
}
