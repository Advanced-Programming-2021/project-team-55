package view.Menus;

import controller.menucontroller.DuelMenuController;
import exceptions.MenuException;
import view.Regexes;
import view.ViewInterface;

import java.util.regex.Matcher;

public class DuelMenu extends Menu {

    private static final DuelMenuController duelMenuController = DuelMenuController.getInstance();

    @Override
    protected void execute() {
        String response =processCommand(ViewInterface.getInput());
        ViewInterface.showResult(response);

    }

    @Override
    protected String processCommand(String command) {
        String response="";
        if(command.matches(Regexes.DUEL_PLAYER.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,Regexes.DUEL_PLAYER.regex);
           try {
               duelMenuController.newPVPDuel(matcher.group(2),Integer.parseInt(matcher.group(1)));
           }
           catch (MenuException e){
               response=e.toString();
           }
        }
        else if(command.matches(Regexes.DUEL_AI.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,Regexes.DUEL_AI.regex);
            try {
                duelMenuController.newAIDuel(Integer.parseInt(matcher.group(1)));
            }
            catch (MenuException e){
                response=e.toString();
            }
        }
    }
}
