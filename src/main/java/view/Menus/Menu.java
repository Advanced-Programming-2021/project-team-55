package view.Menus;

abstract public class Menu {
    public static MenuType currentMenu=MenuType.LOGINMENU;
    protected static LoginMenu loginMenu=new LoginMenu();
    public static void run(){
        while(true) {
            if (currentMenu == MenuType.LOGINMENU) {
                loginMenu.execute();
            }
        }
    }
    abstract protected void execute();
    abstract protected String processCommand(String command);

    public static void setCurrentMenu(MenuType currentMenu) {
        Menu.currentMenu = currentMenu;
    }
}
