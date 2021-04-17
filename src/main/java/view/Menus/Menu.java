package view.Menus;


abstract public class Menu {
    public static MenuType currentMenu = MenuType.LOGIN;
    private static final LoginMenu loginMenu = new LoginMenu();
    private static final MainMenu mainMenu = new MainMenu();
    private static final ScoreBoardMenu scoreBoardMenu = new ScoreBoardMenu();

    public static void run() {
        while (true) {
            if (currentMenu == MenuType.LOGIN) {
                loginMenu.execute();
            } else if (currentMenu == MenuType.MAIN) {
                mainMenu.execute();
            } else if (currentMenu == MenuType.SCOREBOARD) {
                scoreBoardMenu.execute();
            }
        }
    }

    public static void setCurrentMenu(MenuType currentMenu) {
        Menu.currentMenu = currentMenu;
    }

    public static String showCurrentMenu() {
        return currentMenu.menuName;
//        switch (currentMenu) {//todo parham check this
//            case LOGIN: {
//                return MenuType.LOGIN.menuName;
//            }
//            case MAIN: {
//                return MenuType.MAIN.menuName;
//            }
//            case DUEL: {
//                return MenuType.DUEL.menuName;
//            }
//            case DECK: {
//                return MenuType.DECK.menuName;
//            }
//            case SCOREBOARD: {
//                return MenuType.SCOREBOARD.menuName;
//            }
//            case SHOP: {
//                return MenuType.SHOP.menuName;
//            }
//            case PROFILE: {
//                return MenuType.PROFILE.menuName;
//            }
//            case IMPORTEXPORT: {
//                return MenuType.IMPORTEXPORT.menuName;
//            }
//            default:
//                return null;
//        }
    }

    abstract protected void execute();

    abstract protected String processCommand(String command);
}
