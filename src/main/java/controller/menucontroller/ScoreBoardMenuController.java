package controller.menucontroller;

import exceptions.MenuException;
import model.User;
import view.Menus.Menu;
import view.Menus.MenuType;

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
        throw new MenuException("Error: menu navigation is not possible");
    }

    @Override
    public void exitMenu() {
        Menu.setCurrentMenu(MenuType.MAIN);
    }

}
