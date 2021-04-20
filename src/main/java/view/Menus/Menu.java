package view.Menus;


import controller.DataBaseController;
import model.User;

import java.io.IOException;

abstract public class Menu {
    private static final LoginMenu loginMenu = new LoginMenu();
    private static final MainMenu mainMenu = new MainMenu();
    private static final ScoreBoardMenu scoreBoardMenu = new ScoreBoardMenu();
    private static final ProfileMenu profileMenu = new ProfileMenu();
    private static final ShopMenu shopMenu = new ShopMenu();
    private static final DeckMenu deckMenu = new DeckMenu();
    private static final DuelMenu duelMenu = new DuelMenu();
    public static MenuType currentMenu = MenuType.LOGIN;

    public static void run() {
        try {
            DataBaseController.usersDataBaseInitialization();
        }catch (IOException e){
            e.printStackTrace();
        }
        while (true) {
            switch (currentMenu) {
                case LOGIN: {
                    loginMenu.execute();
                    break;
                }
                case MAIN: {
                    mainMenu.execute();
                    break;
                }
                case PROFILE: {
                    profileMenu.execute();
                    break;
                }
                case SCOREBOARD: {
                    scoreBoardMenu.execute();
                    break;
                }
                case SHOP: {
                    shopMenu.execute();
                    break;
                }
                case DECK: {
                    deckMenu.execute();
                }
                case DUEL: {
                    duelMenu.execute();
                }
            }
        }
    }

    public static String getCurrentMenu() {
        return currentMenu.menuName;
    }

    public static void setCurrentMenu(MenuType currentMenu) {
        Menu.currentMenu = currentMenu;
    }

    abstract protected void execute();

    abstract protected String processCommand(String command);

}
