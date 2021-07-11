package yugioh.server.view.Menus;

import yugioh.server.controller.menucontroller.LoginMenuController;
import yugioh.server.model.exceptions.MenuException;
import yugioh.server.view.Regexes;
import yugioh.server.view.Responses;
import yugioh.server.view.ViewInterface;

import java.util.regex.Matcher;

public class LoginMenu extends Menu {
    private static final LoginMenuController loginMenuController = LoginMenuController.getInstance();

    @Override
    protected void execute() {
        String response = processCommand(ViewInterface.getInput());
        ViewInterface.showResult(response);
    }

    @Override
    public String processCommand(String command) {
        String response = "";
        if (command.matches(Regexes.LOGIN_USER.regex)) {
            try {
                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.LOGIN_USER.regex);
                loginMenuController.loginUser(matcher.group(2), matcher.group(1));
                response = Responses.LOGIN_SUCCESSFULLY.response;
            } catch (MenuException e) {
                response = e.toString();
            }
        } else if (command.matches(Regexes.CREATE_USER.regex)) {
            try {
                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.CREATE_USER.regex);
                loginMenuController.createUser(matcher.group(3), matcher.group(2), matcher.group(1));
                response = Responses.CREATE_SUCCESSFULLY.response;
            } catch (MenuException e) {
                response = e.toString();
            }
        } else if (command.matches(Regexes.ENTER_MENU.regex)) {
            try {
                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.ENTER_MENU.regex);
                loginMenuController.enterMenu(matcher.group(1));
            } catch (MenuException e) {
                response = e.toString();
            }
        } else if (command.matches(Regexes.EXIT_MENU.regex)) {
            loginMenuController.exitMenu();
        } else if (command.matches(Regexes.SHOW_MENU.regex)) {
            response = getCurrentMenu();
        } else {
            response = Responses.INVALID_COMMAND.response;
        }
        return response;
    }
}
