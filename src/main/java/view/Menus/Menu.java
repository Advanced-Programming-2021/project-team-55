package view.Menus;

import java.util.Scanner;

public abstract class Menu {
    public static MenuType currentMenu=MenuType.LOGINMENU;
    protected static Scanner input=new Scanner(System.in);
    public abstract void run();
    protected String getInput() {
        return input.nextLine();
    }
}
