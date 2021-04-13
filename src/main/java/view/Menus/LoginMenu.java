package view.Menus;

import controller.LoginMenuController;
import view.ViewInterface;

public class LoginMenu extends Menu {
    private static LoginMenuController loginMenuController= LoginMenuController.getInstance();

    public void execute() {
        String response=loginMenuController.checkCommand(ViewInterface.getInput());
        ViewInterface.showResult(response);
    }
}
