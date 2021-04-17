package controller.menucontroller;

import exceptions.MenuException;
import model.User;

public class ProfileMenuController extends MenuController{
    private static ProfileMenuController profileMenuController;

    private ProfileMenuController(){

    }

    public static ProfileMenuController getInstance() {
        if (profileMenuController == null) profileMenuController = new ProfileMenuController();
        return profileMenuController;
    }

    public void changeNickname(String nickname) throws MenuException{
        if(User.nicknameExists(nickname)){
            throw new MenuException("user with nickname "+nickname+" already exists");
        }
        else{
            User.getUserbyNickname(nickname).setNickname(nickname);
        }
    }

    @Override
    public void enterMenu(String menu) throws MenuException {

    }

    @Override
    public void exitMenu() {

    }

}
