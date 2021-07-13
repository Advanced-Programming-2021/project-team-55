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

import java.io.IOException;
import java.util.regex.Matcher;

public class DuelMenu extends Menu {

    private static final DuelMenuController duelMenuController = DuelMenuController.getInstance();

    private static UserHolder[] awaitingUsersForOneRound = new UserHolder[0];
    private static UserHolder[] awaitingUsersForThreeRounds = new UserHolder[0];

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
            User[] users = new User[awaitingUsersForOneRound.length];
            for (int i = 0; i < awaitingUsersForOneRound.length; i++) {
                users[i] = awaitingUsersForOneRound[i].getUser();
            }
            response = DataBaseController.getObjectJson(users);
        } else if (command.matches("get awaiting users for 3 rounds")) {
            User[] users = new User[awaitingUsersForThreeRounds.length];
            for (int i = 0; i < awaitingUsersForThreeRounds.length; i++) {
                users[i] = awaitingUsersForThreeRounds[i].getUser();
            }
            response = DataBaseController.getObjectJson(users);
        } else if (command.matches("duel --new --rounds (\\d+)")) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, "duel --new --rounds (\\d+)");
            int rounds = Integer.parseInt(matcher.group(1));
            if (rounds == 1) {
                if (awaitingUsersForOneRound.length > 0) {
                    UserHolder firstPlayer = awaitingUsersForOneRound[0];
                    try {
                        firstPlayer.getDataOutputStream().writeUTF("success " + DataBaseController.getUserJson(currentUser.getUser()));
                        firstPlayer.getDataOutputStream().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    awaitingUsersForOneRound = new UserHolder[0];
                    return "success " + DataBaseController.getUserJson(firstPlayer.getUser());
                } else {
                    awaitingUsersForOneRound = new UserHolder[1];
                    awaitingUsersForOneRound[0] = currentUser;
                }
            } else {
                if (awaitingUsersForThreeRounds.length > 0) {
                    UserHolder firstPlayer = awaitingUsersForThreeRounds[0];
                    try {
                        firstPlayer.getDataOutputStream().writeUTF("success " + DataBaseController.getUserJson(currentUser.getUser()));
                        firstPlayer.getDataOutputStream().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    awaitingUsersForThreeRounds = new UserHolder[0];
                    return "success " + DataBaseController.getUserJson(firstPlayer.getUser());
                } else {
                    awaitingUsersForThreeRounds = new UserHolder[1];
                    awaitingUsersForThreeRounds[0] = currentUser;
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
