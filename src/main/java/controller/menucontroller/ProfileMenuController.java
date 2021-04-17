package controller.menucontroller;

import exceptions.MenuException;

public class ProfileMenuController extends MenuController{
    private static ProfileMenuController profileMenuController;

    private ProfileMenuController(){

    }

    public static ProfileMenuController getInstance() {
        if (profileMenuController == null) profileMenuController = new ProfileMenuController();
        return profileMenuController;
    }

    @Override
    public void enterMenu(String menu) throws MenuException {

    }

    @Override
    public void exitMenu() {

    }
}
