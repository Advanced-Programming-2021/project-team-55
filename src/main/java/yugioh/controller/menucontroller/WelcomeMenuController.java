package yugioh.controller.menucontroller;

import javafx.scene.input.MouseEvent;
import yugioh.controller.DataBaseController;
import yugioh.model.User;
import yugioh.view.LoggerMessage;

public class WelcomeMenuController extends MenuController {
    public void enterRegisterMenuClicked(MouseEvent mouseEvent) {
        try {
            registerMenu.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enterLoginMenuClicked(MouseEvent mouseEvent) {
        try {
            loginMenu.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exitClicked(MouseEvent mouseEvent) {
        for (User user : User.getAllUsers()) {
            try {
                DataBaseController.saveUserInfo(user);
            } catch (Exception e) {
                LoggerMessage.log("unable to save user data");
                e.printStackTrace();
            }
        }
        System.exit(0);
    }
}
