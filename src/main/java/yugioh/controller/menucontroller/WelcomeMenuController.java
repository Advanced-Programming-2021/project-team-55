package yugioh.controller.menucontroller;

import javafx.scene.input.MouseEvent;
import yugioh.model.exceptions.MenuException;

public class WelcomeMenuController extends MenuController{
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
}
