package yugioh.server.controller.menucontroller;

import yugioh.server.model.UserHolder;
import yugioh.server.model.exceptions.MenuException;
import yugioh.server.model.User;
import yugioh.server.model.cards.Card;
import yugioh.server.view.Menus.Menu;
import yugioh.server.view.Menus.MenuType;
import yugioh.server.view.Responses;

import java.util.ArrayList;

public class ShopMenuController extends MenuController {

    private static ShopMenuController shopMenuController;

    private ShopMenuController() {
    }

    public static ShopMenuController getInstance() {
        if (shopMenuController == null) shopMenuController = new ShopMenuController();
        return shopMenuController;
    }

    public void buyCard(String cardName, UserHolder userHolder) throws MenuException {
        Card card = Card.getCardByName(cardName);
        if (card == null) {
            throw new MenuException(Responses.NO_CARD_EXISTS.response);
        } else if (userHolder.getUser().getMoney() < card.getPrice()) {
            throw new MenuException(Responses.NOT_ENOUGH_MONEY.response);
        } else {
            userHolder.getUser().changeMoney(-card.getPrice());
            ArrayList<Card> cardsToAdd = new ArrayList<>();
            cardsToAdd.add(card);
            userHolder.getUser().addCardsToInventory(cardsToAdd);
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
