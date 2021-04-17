package controller;

import controller.menucontroller.MenuController;
import exceptions.MenuException;

public class DataBaseController extends MenuController {
    private static DataBaseController dataBaseController;

    private DataBaseController(){

    }

    public static DataBaseController getInstance() {
        if (dataBaseController == null) dataBaseController = new DataBaseController();
        return dataBaseController;
    }

    @Override
    public void enterMenu(String menu) throws MenuException {

    }

    @Override
    public void exitMenu() {

    }



}
