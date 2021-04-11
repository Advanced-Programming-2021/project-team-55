package controller;

import view.ViewInterface;

public class Controller {
    public static MenuType currentMenu;

    static {
        currentMenu=MenuType.LOGINMENU;
    }

    public void run(){
        while(true){
            assessController(ViewInterface.getInput());
        }
    }
    private void assessController(String input){
        switch (currentMenu){
            case LOGINMENU:{
                //LoginMenuController.getInstance().checkCommand(input);
            }
        }
    }
}
