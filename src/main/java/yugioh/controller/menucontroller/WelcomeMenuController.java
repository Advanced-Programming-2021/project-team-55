package yugioh.controller.menucontroller;

import javafx.scene.input.MouseEvent;
import yugioh.controller.DataBaseController;
import yugioh.model.User;
import yugioh.model.cards.Card;
import yugioh.view.LoggerMessage;

import java.util.Date;

public class WelcomeMenuController extends MenuController {
    public void enterRegisterMenuClicked(MouseEvent mouseEvent) {
        playButtonSound();
        try {
            registerMenu.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enterLoginMenuClicked(MouseEvent mouseEvent) {
        playButtonSound();
        try {
            loginMenu.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exitClicked(MouseEvent mouseEvent) {
        playButtonSound();
        System.exit(0);
    }
}
