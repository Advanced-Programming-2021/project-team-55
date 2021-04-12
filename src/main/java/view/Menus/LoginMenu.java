package view.Menus;

import controller.LoginMenuController;

public class LoginMenu extends Menu {

    @Override
    public void run() {
        while(true){
            String response=LoginMenuController.getInstance().checkCommand(getInput());
            System.out.println(response);
        }

    }
}
