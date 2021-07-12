package yugioh.server.view.Menus;

import yugioh.server.controller.menucontroller.MainMenuController;
import yugioh.server.model.UserHolder;
import yugioh.server.model.exceptions.MenuException;
import yugioh.server.view.Regexes;
import yugioh.server.view.Responses;
import yugioh.server.view.ViewInterface;

import java.util.regex.Matcher;

public class MainMenu extends Menu {

    private static final MainMenuController mainMenuController = MainMenuController.getInstance();

    protected void execute() {
//        String response = processCommand(ViewInterface.getInput());
//        ViewInterface.showResult(response);
    }

    protected String processCommand(String command, UserHolder currentUser) {
        String response = "";
        if (command.matches(Regexes.LOGOUT_USER.regex)) {
            mainMenuController.logout(currentUser);
            response = Responses.LOGOUT_SUCCESSFULLY.response;
        } else if (command.matches(Regexes.EXIT_MENU.regex)) {
            mainMenuController.exitMenu();
        } else if (command.matches(Regexes.EXIT_CHATROOM.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.EXIT_CHATROOM.regex);
            response = matcher.group(1) + " gomsho";
        } else if (command.matches(Regexes.SHOW_MENU.regex)) {
            response = getCurrentMenu();
        } else {
            response = Responses.INVALID_COMMAND.response;
        }
        return response;
    }

}
