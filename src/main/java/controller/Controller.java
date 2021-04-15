package controller;

import exceptions.MenuException;
import view.ViewInterface;

abstract public class Controller {
    abstract protected void enterMenu(String menu)throws MenuException;
    abstract protected void exitMenu();

}
