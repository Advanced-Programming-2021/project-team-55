package controller.menucontroller;

import exceptions.MenuException;

abstract public class MenuController {

    abstract protected void enterMenu(String menu) throws MenuException;

    abstract protected void exitMenu();

}
