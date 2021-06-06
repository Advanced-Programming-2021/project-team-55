package yugioh.controller.menucontroller;

import yugioh.model.exceptions.MenuException;
import yugioh.model.User;
import yugioh.view.Menus.WelcomeMenu;
import yugioh.view.Menus.MenuType;
import yugioh.view.Responses;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ScoreBoardMenuController extends MenuController {

    private static ScoreBoardMenuController scoreBoardMenuController;

    private ScoreBoardMenuController() {
    }

    public static ScoreBoardMenuController getInstance() {
        if (scoreBoardMenuController == null) {
            scoreBoardMenuController = new ScoreBoardMenuController();
        }
        return scoreBoardMenuController;
    }

    public LinkedHashMap<Integer, HashMap<Integer, String>> getScoreBoard() {
        return User.getScoreBoardUsers();
    }


}
