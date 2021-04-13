package view.Menus;

import controller.LoginMenuController;
import view.ViewInterface;

public class LoginMenu extends Menu {
    private static LoginMenuController loginMenuController= LoginMenuController.getInstance();

    public void execute() {
        String response=processCommand(ViewInterface.getInput());
        ViewInterface.showResult(response);
    }
    private String processCommand(String command){
        if(command.matches("^user login --password (.*) --username (.*)$")){
            return loginMenuController.loginUser(ViewInterface.getCommandMatcher(command,"^user login --password (.*) --username (.*)$"));
        }
        else if(command.matches("^user create --nickname (.*) --password (.*) --username (.*)$")){
            return loginMenuController.createUser(ViewInterface.getCommandMatcher(command,"^user create --nickname (.*) --password (.*) --username (.*)$"));
        }
        else {
            return "invalid command";
        }

    }
}
