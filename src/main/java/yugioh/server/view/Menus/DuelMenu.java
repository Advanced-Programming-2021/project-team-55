package yugioh.server.view.Menus;

import yugioh.server.controller.AIPlayerController;
import yugioh.server.controller.menucontroller.DuelMenuController;
import yugioh.server.model.exceptions.MenuException;
import yugioh.server.view.Regexes;
import yugioh.server.view.Responses;
import yugioh.server.view.ViewInterface;
import yugioh.server.view.gamephases.Duel;

import java.util.regex.Matcher;

public class DuelMenu extends Menu {

    private static final DuelMenuController duelMenuController = DuelMenuController.getInstance();

    @Override
    protected void execute() {
        AIPlayerController.setIsGameEnded(true);
        String response = processCommand(ViewInterface.getInput());
        ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        if (command.matches(Regexes.DUEL_PLAYER.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.DUEL_PLAYER.regex);
            try {
                Duel.runGame(duelMenuController.newPVPDuel(matcher.group(2), Integer.parseInt(matcher.group(1))));
            } catch (MenuException e) {
                response = e.toString();
            }
        } else if (command.matches(Regexes.DUEL_AI.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.DUEL_AI.regex);
            try {
                Duel.runGame(duelMenuController.newAIDuel(Integer.parseInt(matcher.group(1))));
            } catch (MenuException e) {
                response = e.toString();
            }
        } else if (command.matches(Regexes.EXIT_MENU.regex)) {
            duelMenuController.exitMenu();
        } else if (command.matches(Regexes.SHOW_MENU.regex)) {
            response = getCurrentMenu();
        } else {
            response = Responses.INVALID_COMMAND.response;
        }
        return response;
    }

}
