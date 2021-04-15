package view.Menus;

import java.util.Scanner;

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


}
