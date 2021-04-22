package view.Menus;

import controller.menucontroller.ScoreBoardMenuController;
import exceptions.MenuException;
import view.Regexes;
import view.Responses;
import view.ViewInterface;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;

public class ScoreBoardMenu extends Menu {
    private static final ScoreBoardMenuController scoreBoardMenuController = ScoreBoardMenuController.getInstance();


    @Override
    protected void execute() {
        String response = processCommand(ViewInterface.getInput());
        ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        if (command.matches(Regexes.SHOW_SCOREBOARD.regex)) {
            showScoreBoard(scoreBoardMenuController.getScoreBoard());
        } else if (command.matches(Regexes.ENTER_MENU.regex)) {
            try {
                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.ENTER_MENU.regex);
                scoreBoardMenuController.enterMenu(matcher.group(1));
            } catch (MenuException e) {
                response = e.toString();
            }
        } else if (command.matches(Regexes.EXIT_MENU.regex)) {
            scoreBoardMenuController.exitMenu();
        } else if (command.matches(Regexes.SHOW_MENU.regex)) {
            response = getCurrentMenu();
        } else {
            response = Responses.INVALID_COMMAND.response;
        }
        return response;
    }

    private void showScoreBoard(LinkedHashMap<Integer,HashMap<Integer,String>> scoreBoard) {
        for (int i = 0; i < scoreBoard.size(); i++) {
            HashMap<Integer,String> userInfo=scoreBoard.get(i);
            for (int rank : userInfo.keySet()) {
                System.out.println(rank + userInfo.get(rank));
            }
        }
    }
}
