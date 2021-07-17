package yugioh.server.controller.menucontroller;

import yugioh.server.model.User;
import yugioh.server.model.UserHolder;
import yugioh.server.model.exceptions.MenuException;
import yugioh.server.view.Menus.Menu;
import yugioh.server.view.Menus.MenuType;
import yugioh.server.view.Responses;

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

    public void logout(UserHolder currentUser) {
        User.getLoggedInUsers().remove(currentUser);
        System.out.println("logout requested.");
        Menu.setCurrentMenu(MenuType.LOGIN);
    }

    @Override
    public void enterMenu(String menu) throws MenuException {
        switch (menu) {
            case "Duel": {
                Menu.setCurrentMenu(MenuType.DUEL);
                break;
            }
            case "Deck": {
                Menu.setCurrentMenu(MenuType.DECK);
                break;
            }
            case "Scoreboard": {
                Menu.setCurrentMenu(MenuType.SCOREBOARD);
                break;
            }
            case "Profile": {
                Menu.setCurrentMenu(MenuType.PROFILE);
                break;
            }
            case "Shop": {
                Menu.setCurrentMenu(MenuType.SHOP);
                break;
            }
            case "Import/Export": {
                Menu.setCurrentMenu(MenuType.IMPORT_EXPORT);
                break;
            }
            default: {
                throw new MenuException(Responses.MENU_NAVIGATION_NOT_POSSIBLE.response);
            }

        }
    }

    @Override
    public void exitMenu() {
        Menu.setCurrentMenu(MenuType.LOGIN);
    }

}
