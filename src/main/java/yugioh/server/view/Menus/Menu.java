package yugioh.server.view.Menus;


import javafx.embed.swing.JFXPanel;
import yugioh.server.controller.DataBaseController;
import yugioh.server.model.User;
import yugioh.server.model.UserHolder;

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
            JFXPanel jfxPanel = new JFXPanel();
            jfxPanel.getScene();
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

    abstract protected String processCommand(String command, UserHolder currentUser);

    public static String handleCommand(String command, UserHolder currentUser) {
        String result = loginMenu.processCommand(command, currentUser);
        if (result.equals("Error: invalid command"))
            result = mainMenu.processCommand(command, currentUser);
        if (result.equals("Error: invalid command"))
            result = scoreBoardMenu.processCommand(command, currentUser);
        if (result.equals("Error: invalid command"))
            result = profileMenu.processCommand(command, currentUser);
        if (result.equals("Error: invalid command"))
            result = shopMenu.processCommand(command, currentUser);
        if (result.equals("Error: invalid command"))
            result = deckMenu.processCommand(command, currentUser);
        if (result.equals("Error: invalid command"))
            result = duelMenu.processCommand(command, currentUser);

        return result;
    }

}
