package yugioh.server.view.Menus;

import yugioh.server.controller.menucontroller.ProfileMenuController;
import yugioh.server.model.exceptions.MenuException;
import yugioh.server.view.Regexes;
import yugioh.server.view.Responses;
import yugioh.server.view.ViewInterface;

import java.util.regex.Matcher;

public class ProfileMenu extends Menu {
    private static final ProfileMenuController profileMenuController = ProfileMenuController.getInstance();


    @Override
    protected void execute() {
        String response = processCommand(ViewInterface.getInput());
        ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        if (command.matches(Regexes.CHANGE_NICKNAME.regex)) {
            try {
                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.CHANGE_NICKNAME.regex);
                profileMenuController.changeNickname(matcher.group(1));
                response = Responses.NICKNAME_CHANGED_SUCCESSFULLY.response;
            } catch (MenuException e) {
                response = e.toString();
            }
        } else if (command.matches(Regexes.CHANGE_PASSWORD.regex)) {
            try {
                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.CHANGE_PASSWORD.regex);
                profileMenuController.changePassword(matcher.group(1), matcher.group(2));
                response = Responses.PASSWORD_CHANGED_SUCCESSFULLY.response;
            } catch (MenuException e) {
                response = e.toString();
            }
        } else if (command.matches(Regexes.ENTER_MENU.regex)) {
            try {
                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.ENTER_MENU.regex);
                profileMenuController.enterMenu(matcher.group(1));
            } catch (MenuException e) {
                response = e.toString();
            }
        } else if (command.matches(Regexes.EXIT_MENU.regex)) {
            profileMenuController.exitMenu();
        } else if (command.matches(Regexes.SHOW_MENU.regex)) {
            response = getCurrentMenu();
        } else {
            response = Responses.INVALID_COMMAND.response;
        }
        return response;
    }
}
