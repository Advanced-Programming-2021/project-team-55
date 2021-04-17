package view.Menus;

import controller.menucontroller.ProfileMenuController;
import exceptions.MenuException;
import view.Regexes;
import view.Responses;
import view.ViewInterface;

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
                response= Responses.NICKNAME_CHANGED_SUCCESSFULLY.response;
            }
            catch (MenuException e){
                response=e.toString();
            }
        }
        else {
            response=Responses.INVALID_COMMAND.response;
        }
        return response;
    }
}
