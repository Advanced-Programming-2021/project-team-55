package yugioh.controller.menucontroller;

import javafx.scene.input.MouseEvent;
import yugioh.model.User;

public class MainMenuController extends MenuController {

    public static MainMenuController mainMenuController;

    public MainMenuController() {

    }

    public static MainMenuController getInstance() {
        if (mainMenuController == null) {
            mainMenuController = new MainMenuController();
        }
        return mainMenuController;
    }

    public void logout() throws Exception {
        User.setLoggedInUser(null);
        welcomeMenu.execute();
    }

    public void enterDuelMenuClicked(MouseEvent mouseEvent) throws Exception {
        playButtonSound();
        duelMenu.execute();
    }

    public void enterDeckMenuClicked(MouseEvent mouseEvent) throws Exception
    {playButtonSound();
        deckMenu.execute();
    }

    public void enterScoreBoardMenuClicked(MouseEvent mouseEvent) throws Exception {
        playButtonSound();
        scoreBoardMenu.execute();
    }

    public void enterProfileMenuClicked(MouseEvent mouseEvent) throws Exception {
        playButtonSound();
        profileMenu.execute();
    }

    public void enterShopMenuClicked(MouseEvent mouseEvent) throws Exception {
        playButtonSound();
        shopMenu.execute();
    }

    public void enterImportExportClicked(MouseEvent mouseEvent) throws Exception {
        playButtonSound();
        importExportMenu.execute();
    }

    public void logoutClicked(MouseEvent mouseEvent) throws Exception {
        playButtonSound();
        logout();
    }


}
