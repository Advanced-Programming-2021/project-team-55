package controller.menucontroller;


import exceptions.MenuException;
import model.User;
import view.Menus.Menu;
import view.Menus.MenuType;

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
        User user = User.getUserbyUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new MenuException("Username and password didn't match!");
        } else {
            Menu.currentMenu = MenuType.MAIN;
            User.setLoggedInUser(user);
        }
    }

    public void createUser(String username, String password, String nickname) throws MenuException {
        if (User.usernameExists(username)) {
            throw new MenuException("user with username " + username + " exists");
        } else if (User.nicknameExists(nickname)) {
            throw new MenuException("user with nickname " + nickname + " already exists");
        }
        new User(username, nickname, password);
    }

    @Override
    public void enterMenu(String menu) throws MenuException {
        if (User.loggedInUser == null) {
            throw new MenuException("please login first!");
        } else if (!menu.equals("Main")) {
            throw new MenuException("menu navigation is not possible");
        }
        Menu.setCurrentMenu(MenuType.MAIN);
    }

    @Override
    public void exitMenu() {
        System.exit(0);
    }
}
