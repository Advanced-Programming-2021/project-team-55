package yugioh.controller.menucontroller;

import javafx.scene.input.MouseEvent;
import yugioh.model.exceptions.MenuException;
import yugioh.model.User;
import yugioh.view.Menus.WelcomeMenu;
import yugioh.view.Menus.MenuType;
import yugioh.view.Responses;

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

    public void logout() throws Exception{
        User.setLoggedInUser(null);
        welcomeMenu.execute();
    }

    public void enterDuelMenuClicked(MouseEvent mouseEvent) throws Exception{
        duelMenu.execute();
    }

    public void enterDeckMenuClicked(MouseEvent mouseEvent) throws Exception{
        deckMenu.execute();
    }

    public void enterScoreBoardMenuClicked(MouseEvent mouseEvent) throws Exception{
        scoreBoardMenu.execute();
    }

    public void enterProfileMenuClicked(MouseEvent mouseEvent) throws Exception{
        profileMenu.execute();
    }

    public void enterShopMenuClicked(MouseEvent mouseEvent) throws Exception{
        shopMenu.execute();
    }

    public void enterImportExportClicked(MouseEvent mouseEvent) throws Exception{
        importExportMenu.execute();
    }

    public void logoutClicked(MouseEvent mouseEvent) throws Exception{
        logout();
    }

//    @Override
//    public void enterMenu(String menu) throws MenuException {
//        switch (menu) {
//            case "Duel": {
//                WelcomeMenu.setCurrentMenu(MenuType.DUEL);
//                break;
//            }
//            case "Deck": {
//                WelcomeMenu.setCurrentMenu(MenuType.DECK);
//                break;
//            }
//            case "Scoreboard": {
//                WelcomeMenu.setCurrentMenu(MenuType.SCOREBOARD);
//                break;
//            }
//            case "Profile": {
//                WelcomeMenu.setCurrentMenu(MenuType.PROFILE);
//                break;
//            }
//            case "Shop": {
//                WelcomeMenu.setCurrentMenu(MenuType.SHOP);
//                break;
//            }
//            case "Import/Export": {
//                WelcomeMenu.setCurrentMenu(MenuType.IMPORT_EXPORT);
//                break;
//            }
//            default: {
//                throw new MenuException(Responses.MENU_NAVIGATION_NOT_POSSIBLE.response);
//            }
//
//        }
//    }

}
