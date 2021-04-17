package controller.menucontroller;

import exceptions.MenuException;

public class ShopMenuController extends MenuController{
    private static ShopMenuController shopMenuController;

    private ShopMenuController(){}

    public static ShopMenuController getInstance() {
        if (shopMenuController == null) shopMenuController = new ShopMenuController();
        return shopMenuController;
    }

    @Override
    public void enterMenu(String menu) throws MenuException {

    }

    @Override
    public void exitMenu() {

    }
}
