package controller.menucontroller;

import exceptions.MenuException;
import model.User;
import model.cards.Card;
import view.Menus.Menu;
import view.Menus.MenuType;
import view.Responses;

import java.util.ArrayList;

public class ShopMenuController extends MenuController {

    private static ShopMenuController shopMenuController;

    private ShopMenuController() {
    }

    public static ShopMenuController getInstance() {
        if (shopMenuController == null) shopMenuController = new ShopMenuController();
        return shopMenuController;
    }

    public void buyCard(String cardName) throws MenuException {
        Card card = Card.getCardByName(cardName);
        if (card == null) {
            throw new MenuException("Error: there is no card with this name");
        } else if (User.loggedInUser.getMoney() < card.getPrice()) {
            throw new MenuException("Error: not enough money");
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

    @Override
    public void enterMenu(String menu) throws MenuException {
        throw new MenuException(Responses.MENU_NAVIGATION_NOT_POSSIBLE.response);
    }

    @Override
    public void exitMenu() {
        Menu.setCurrentMenu(MenuType.MAIN);

    }
}
