package yugioh.server.view.Menus;

import yugioh.server.controller.AIPlayerController;
import yugioh.server.controller.DataBaseController;
import yugioh.server.controller.menucontroller.DuelMenuController;
import yugioh.server.model.User;
import yugioh.server.model.UserHolder;
import yugioh.server.model.exceptions.MenuException;
import yugioh.server.view.Regexes;
import yugioh.server.view.Responses;
import yugioh.server.view.ViewInterface;
import yugioh.server.view.gamephases.Duel;

import java.util.regex.Matcher;

public class DuelMenu extends Menu {

    private static final DuelMenuController duelMenuController = DuelMenuController.getInstance();

    private static User[] awaitingUsersForOneRound = new User[0];
    private static User[] awaitingUsersForThreeRounds = new User[0];

    @Override
    protected void execute() {
        AIPlayerController.setIsGameEnded(true);
//        String response = processCommand(ViewInterface.getInput());
//        ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command, UserHolder currentUser) {
        String response = "";
        if (command.matches(Regexes.DUEL_PLAYER.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.DUEL_PLAYER.regex);
            try {
                Duel.runGame(duelMenuController.newPVPDuel(matcher.group(2), Integer.parseInt(matcher.group(1)), currentUser));
            } catch (MenuException e) {
                response = e.toString();
            }
        } else if (command.matches(Regexes.DUEL_AI.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.DUEL_AI.regex);
            try {
                Duel.runGame(duelMenuController.newAIDuel(Integer.parseInt(matcher.group(1)), currentUser));
            } catch (MenuException e) {
                response = e.toString();
            }
        } else if (command.matches("get awaiting users for 1 round")) {
            response = DataBaseController.getObjectJson(awaitingUsersForOneRound);
        } else if (command.matches("get awaiting users for 3 rounds")) {
            response = DataBaseController.getObjectJson(awaitingUsersForThreeRounds);
        } else if (command.matches("duel --new --rounds (\\d+)")) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, "duel --new --rounds (\\d+)");
            int rounds = Integer.parseInt(matcher.group(1));
            if (rounds == 1) {//todo inform another player
                if (awaitingUsersForOneRound.length > 0)
                    return "success " + DataBaseController.getUserJson(awaitingUsersForOneRound[0]);
                else {
                    awaitingUsersForOneRound = new User[1];
                    awaitingUsersForOneRound[0] = currentUser.getUser();
                }
            } else {
                if (awaitingUsersForThreeRounds.length > 0)
                    return "success " + DataBaseController.getUserJson(awaitingUsersForThreeRounds[0]);
                else {
                    awaitingUsersForThreeRounds = new User[1];
                    awaitingUsersForThreeRounds[0] = currentUser.getUser();
                }
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
