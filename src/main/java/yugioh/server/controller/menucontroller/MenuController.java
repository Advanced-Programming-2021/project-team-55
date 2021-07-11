package controller.menucontroller;

import model.exceptions.MenuException;

abstract public class MenuController {

    abstract protected void enterMenu(String menu) throws MenuException;

    abstract protected void exitMenu();

}
