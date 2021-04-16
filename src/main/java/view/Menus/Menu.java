package view.Menus;


import controller.MenuController.ScoreBoardMenuController;

abstract public class Menu {
    public static MenuType currentMenu = MenuType.LOGIN;
    private static LoginMenu loginMenu = new LoginMenu();
    private static MainMenu mainMenu=new MainMenu();
    private static ScoreBoardMenu scoreBoardMenu=new ScoreBoardMenu();

    public static void run() {
        while (true) {
            if (currentMenu == MenuType.LOGIN) {
                loginMenu.execute();
            }
            else if(currentMenu==MenuType.MAIN){
                mainMenu.execute();
            }
            else if(currentMenu==MenuType.SCOREBOARD){
                scoreBoardMenu.execute();
            }
        }
    }

    abstract protected void execute();

    abstract protected String processCommand(String command);

    public static void setCurrentMenu(MenuType currentMenu) {
        Menu.currentMenu = currentMenu;
    }

    public static String showCurrentMenu() {
        switch (currentMenu) {
            case LOGIN: {
                return MenuType.LOGIN.menuName;
            }
            case MAIN: {
                return MenuType.MAIN.menuName;
            }
            case DUEL: {
                return MenuType.DUEL.menuName;
            }
            case DECK: {
                return MenuType.DECK.menuName;
            }
            case SCOREBOARD: {
                return MenuType.SCOREBOARD.menuName;
            }
            case SHOP: {
                return MenuType.SHOP.menuName;
            }
            case PROFILE: {
                return MenuType.PROFILE.menuName;
            }
            case IMPORTEXPORT: {
                return MenuType.IMPORTEXPORT.menuName;
            }
            default:
                return null;
        }
    }
}
