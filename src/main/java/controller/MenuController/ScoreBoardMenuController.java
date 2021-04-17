package controller.MenuController;

import model.User;

import java.util.LinkedHashMap;

public class ScoreBoardMenuController {
    private static ScoreBoardMenuController scoreBoardMenuController;

    private ScoreBoardMenuController() {
    }

    public static ScoreBoardMenuController getInstance() {
        if (scoreBoardMenuController == null) {
            scoreBoardMenuController = new ScoreBoardMenuController();
        }
        return scoreBoardMenuController;
    }

    public LinkedHashMap<Integer, String> getScoreBoard() {
        return User.getScoreBoardUsers();
    }
}
