package view.Menus;

import controller.LoginMenuController;
import view.Regexes;
import view.Responses;
import exceptions.MenuException;
import view.ViewInterface;

import java.util.regex.Matcher;

public class LoginMenu extends Menu {
    private static LoginMenuController loginMenuController = LoginMenuController.getInstance();

    protected void execute() {
        String response = processCommand(ViewInterface.getInput());
        ViewInterface.showResult(response);
    }

    protected String processCommand(String command) {
        String response="";
        if (command.matches(Regexes.LOGINUSER.regex)) {
            try {
                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.LOGINUSER.regex);
                loginMenuController.loginUser(matcher.group(2), matcher.group(1));
                response= Responses.LOGINSUCCESSFULL.response;
            } catch (MenuException e) {
                response= e.toString();
            }
        } else if (command.matches(Regexes.CREATEUSER.regex)) {
            try {
                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.CREATEUSER.regex);
                loginMenuController.createUser(matcher.group(3), matcher.group(2), matcher.group(1));
                response= Responses.CREATESUCCESSFULLY.response;
            } catch (MenuException e) {
                response= e.toString();
            }
        } else if (command.matches(Regexes.ENTERMENU.regex)) {
            try {
                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.ENTERMENU.regex);
                loginMenuController.enterMenu(matcher.group(1));
            } catch (MenuException e) {
                response= e.toString();
            }
        } else if (command.matches(Regexes.EXITMENU.regex)) {
            loginMenuController.exitMenu();
        } else {
            response= Responses.INVALIDCOMMAND.response;
        }
        return response;
    }
}
