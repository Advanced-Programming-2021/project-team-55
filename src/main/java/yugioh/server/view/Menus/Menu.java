package yugioh.server.view.Menus;


import yugioh.server.controller.DataBaseController;

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
            DataBaseController.cardsDataBaseInitialization();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        while (true) {
//            switch (currentMenu) {
//                case LOGIN: {
//                    loginMenu.execute();
//                    break;
//                }
//                case MAIN: {
//                    mainMenu.execute();
//                    break;
//                }
//                case PROFILE: {
//                    profileMenu.execute();
//                    break;
//                }
//                case SCOREBOARD: {
//                    scoreBoardMenu.execute();
//                    break;
//                }
//                case SHOP: {
//                    shopMenu.execute();
//                    break;
//                }
//                case DECK: {
//                    deckMenu.execute();
//                    break;
//                }
//                case DUEL: {
//                    duelMenu.execute();
//                    break;
//                }
//            }
//        }

    }

    public static String getCurrentMenu() {
        return currentMenu.menuName;
    }

    public static void setCurrentMenu(MenuType currentMenu) {
        Menu.currentMenu = currentMenu;
    }

    abstract protected void execute();

    abstract protected String processCommand(String command);

    public static String handleCommand(String command) {
        String result = loginMenu.processCommand(command);
        if (result.equals("Error: invalid command"))
            result = mainMenu.processCommand(command);
        if (result.equals("Error: invalid command"))
            result = scoreBoardMenu.processCommand(command);
        if (result.equals("Error: invalid command"))
            result = profileMenu.processCommand(command);
        if (result.equals("Error: invalid command"))
            result = shopMenu.processCommand(command);
        if (result.equals("Error: invalid command"))
            result = deckMenu.processCommand(command);
        if (result.equals("Error: invalid command"))
            result = duelMenu.processCommand(command);

        return result;
    }

}
