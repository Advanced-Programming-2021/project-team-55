package view.Menus;

import controller.MenuController.MainMenuController;
import exceptions.MenuException;
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
        if (command.matches(Regexes.LOGOUTUSER.regex)) {
            mainMenuController.logout();
        } else if (command.matches(Regexes.ENTERMENU.regex)) {
            try {
                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.ENTERMENU.regex);
                mainMenuController.enterMenu(matcher.group(1));
            } catch (MenuException e) {
                response = e.toString();
            }
        } else if (command.matches(Regexes.EXITMENU.regex)) {
            mainMenuController.exitMenu();
        } else if (command.matches(Regexes.SHOWMENU.regex)) {
            response = showCurrentMenu();
        } else {
            response = Responses.INVALIDCOMMAND.response;
        }
        return response;
    }
}
