package controller.menucontroller;

import exceptions.MenuException;
import model.Card;
import model.User;

import java.util.ArrayList;

public class ShopMenuController extends MenuController{

    private static ShopMenuController shopMenuController;

    private ShopMenuController(){}

    public static ShopMenuController getInstance() {
        if (shopMenuController == null) shopMenuController = new ShopMenuController();
        return shopMenuController;
    }

    public void buyCard(String cardName)throws MenuException{
        Card card=Card.getCardByName(cardName);
        if(card==null){
            throw new MenuException("there is no card with this name");
        }
        else if(User.loggedInUser.getMoney()<card.getPrice()){
            throw new MenuException("not enough money");
        }
        else{
            User.loggedInUser.changeMoney(-card.getPrice());
            User.loggedInUser.addCardToInventory(card);
        }
    }

    public ArrayList<Card> getAllCards(){
        return Card.getCards();
    }

    @Override
    public void enterMenu(String menu) throws MenuException {
        throw new MenuException("menu navigation is not possible");
    }

    @Override
    public void exitMenu() {

    }
}
