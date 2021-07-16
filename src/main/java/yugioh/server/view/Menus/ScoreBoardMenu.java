package yugioh.server.view.Menus;

import yugioh.server.controller.DataBaseController;
import yugioh.server.controller.menucontroller.ScoreBoardMenuController;
import yugioh.server.model.User;
import yugioh.server.model.UserHolder;
import yugioh.server.view.Regexes;
import yugioh.server.view.Responses;

import java.util.ArrayList;

public class ScoreBoardMenu extends Menu {
    private static final ScoreBoardMenuController scoreBoardMenuController = ScoreBoardMenuController.getInstance();


    @Override
    protected void execute() {
//        String response = processCommand(ViewInterface.getInput());
//        ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command, UserHolder currentUser) {
        String response = "";
        if (command.matches(Regexes.SHOW_SCOREBOARD.regex)) {
            response = showScoreBoard();
        } else if (command.matches(Regexes.EXIT_MENU.regex)) {
            scoreBoardMenuController.exitMenu();
        } else if (command.matches(Regexes.SHOW_MENU.regex)) {
            response = getCurrentMenu();
        } else {
            response = Responses.INVALID_COMMAND.response;
        }
        return response;
    }

    private String showScoreBoard() {//todo auto refresh
        ArrayList<User> users = User.getAllUsers();
        ScoreBoardItem[] scoreBoardItems = new ScoreBoardItem[users.size()];
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            scoreBoardItems[i] = (new ScoreBoardItem(user.getUsername(), user.getScore()));
        }
        return DataBaseController.getObjectJson(scoreBoardItems);
    }
}

class ScoreBoardItem {
    String username;
    int score;

    public ScoreBoardItem(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }
}
