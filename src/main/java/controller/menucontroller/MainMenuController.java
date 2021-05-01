package controller.menucontroller;

import exceptions.MenuException;
import model.User;
import view.Menus.Menu;
import view.Menus.MenuType;

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
                throw new MenuException("Error: menu navigation is not possible");
            }

        }
    }

    @Override
    public void exitMenu() {
        Menu.setCurrentMenu(MenuType.LOGIN);
    }

}
