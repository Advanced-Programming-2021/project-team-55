package yugioh.server.controller.menucontroller;

import yugioh.server.model.exceptions.MenuException;
import yugioh.server.model.User;
import yugioh.server.view.Menus.Menu;
import yugioh.server.view.Menus.MenuType;
import yugioh.server.view.Responses;

public class ProfileMenuController extends MenuController {
    private static ProfileMenuController profileMenuController;

    private ProfileMenuController() {
    }

    public static ProfileMenuController getInstance() {
        if (profileMenuController == null) profileMenuController = new ProfileMenuController();
        return profileMenuController;
    }

    public void changeNickname(String nickname) throws MenuException {
        if (User.nicknameExists(nickname)) {
            throw new MenuException("Error: user with nickname " + nickname + " already exists");
        } else {
            User.loggedInUser.setNickname(nickname);
        }
    }

    public void changePassword(String currentPassword, String newPassword) throws MenuException {
        if (!User.loggedInUser.getPassword().equals(currentPassword)) {
            throw new MenuException("Error: current password is invalid");
        } else if (newPassword.equals(currentPassword)) {
            throw new MenuException("Error: please enter a new password");
        }
        User.loggedInUser.setPassword(newPassword);
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