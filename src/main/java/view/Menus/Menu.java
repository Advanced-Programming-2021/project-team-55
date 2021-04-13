package view.Menus;

import java.util.Scanner;

public class Menu {
    public static MenuType currentMenu=MenuType.LOGINMENU;
    protected static LoginMenu loginMenu=new LoginMenu();
    public static void run(){
        while(true) {
            if (currentMenu == MenuType.LOGINMENU) {
                loginMenu.execute();
            }
        }
    }


}
