package view.Menus;

import controller.menucontroller.MainMenuController;
import model.exceptions.MenuException;
import view.Regexes;
import view.Responses;
import view.ViewInterface;

import java.util.regex.Matcher;

public class MainMenu extends Menu {

    private static final MainMenuController mainMenuController = MainMenuController.getInstance();

    protected void execute() {
        String response = processCommand(ViewInterface.getInput());
        ViewInterface.showResult(response);
    }

    protected String processCommand(String command) {
        String response = "";
        if (command.matches(Regexes.LOGOUT_USER.regex)) {
            mainMenuController.logout();
            response = Responses.LOGOUT_SUCCESSFULLY.response;
        } else if (command.matches(Regexes.ENTER_MENU.regex)) {
            try {
                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.ENTER_MENU.regex);
                mainMenuController.enterMenu(matcher.group(1));
            } catch (MenuException e) {
                response = e.toString();
            }
        } else if (command.matches(Regexes.EXIT_MENU.regex)) {
            mainMenuController.exitMenu();
        } else if (command.matches(Regexes.SHOW_MENU.regex)) {
            response = getCurrentMenu();
        } else {
            response = Responses.INVALID_COMMAND.response;
        }
        return response;
    }

}
