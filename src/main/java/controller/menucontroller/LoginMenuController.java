package controller.menucontroller;


import controller.DataBaseController;
import model.exceptions.MenuException;
import model.User;
import view.LoggerMessage;
import view.Menus.Menu;
import view.Menus.MenuType;
import view.Responses;

public class LoginMenuController extends MenuController {

    private static LoginMenuController loginMenuController;

    private LoginMenuController() {

    }

    public static LoginMenuController getInstance() {
        if (loginMenuController == null) {
            loginMenuController = new LoginMenuController();
        }
        return loginMenuController;
    }

    public void loginUser(String username, String password) throws MenuException {
        User user = User.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new MenuException(Responses.USERNAME_AND_PASSWORD_DIDNT_MATCH.response);
        } else {
            Menu.currentMenu = MenuType.MAIN;
            User.setLoggedInUser(user);
        }
    }

    public void createUser(String username, String password, String nickname) throws MenuException {
        if (User.usernameExists(username)) {
            throw new MenuException("Error: user with username " + username + " exists");
        } else if (User.nicknameExists(nickname)) {
            throw new MenuException("Error: user with nickname " + nickname + " already exists");
        }
        new User(username, nickname, password);
    }

    @Override
    public void enterMenu(String menu) throws MenuException {
        if (User.loggedInUser == null) {
            throw new MenuException(Responses.LOGIN_FIRST.response);
        } else if (!menu.equals("Main")) {
            throw new MenuException(Responses.MENU_NAVIGATION_NOT_POSSIBLE.response);
        }
        Menu.setCurrentMenu(MenuType.MAIN);
    }

    @Override
    public void exitMenu() {
        for (User user : User.getAllUsers()) {
            try {
                DataBaseController.saveUserInfo(user);
            } catch (Exception e) {
                LoggerMessage.log("unable to save user data");
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

}
