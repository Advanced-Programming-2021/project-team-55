package yugioh.controller.menucontroller;

import javafx.scene.input.MouseEvent;
import yugioh.model.exceptions.MenuException;
import yugioh.model.User;
import yugioh.view.Menus.WelcomeMenu;
import yugioh.view.Menus.MenuType;
import yugioh.view.Responses;

public class ProfileMenuController extends MenuController {
    public static ProfileMenuController profileMenuController;

    public ProfileMenuController() {
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


    public void backClicked(MouseEvent mouseEvent) throws Exception{
        mainMenu.execute();
    }
}
