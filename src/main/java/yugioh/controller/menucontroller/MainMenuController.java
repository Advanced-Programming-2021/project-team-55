package yugioh.controller.menucontroller;

import yugioh.model.exceptions.MenuException;
import yugioh.model.User;
import yugioh.view.Menus.WelcomeMenu;
import yugioh.view.Menus.MenuType;
import yugioh.view.Responses;

public class MainMenuController extends MenuController {

    private static MainMenuController mainMenuController;

    private MainMenuController() {

    }

    public static MainMenuController getInstance() {
        if (mainMenuController == null) {
            mainMenuController = new MainMenuController();
        }
        return mainMenuController;
    }

    public void logout() {
        User.setLoggedInUser(null);
        WelcomeMenu.setCurrentMenu(MenuType.LOGIN);
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
