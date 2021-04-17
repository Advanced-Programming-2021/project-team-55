package controller.MenuController;

import exceptions.MenuException;

abstract public class MenuController {
    abstract public void enterMenu(String menu) throws MenuException;

    abstract public void exitMenu();

}
