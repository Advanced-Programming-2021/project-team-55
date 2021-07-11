package yugioh.server.controller.menucontroller;

import yugioh.server.model.exceptions.MenuException;
import yugioh.server.model.User;
import yugioh.server.view.Menus.Menu;
import yugioh.server.view.Menus.MenuType;
import yugioh.server.view.Responses;

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

    @Override
    public void enterMenu(String menu) throws MenuException {
        throw new MenuException(Responses.MENU_NAVIGATION_NOT_POSSIBLE.response);
    }

    @Override
    public void exitMenu() {
        Menu.setCurrentMenu(MenuType.MAIN);
    }

}
