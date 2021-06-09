package yugioh.controller.menucontroller;


import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import yugioh.model.User;
import yugioh.model.exceptions.MenuException;
import yugioh.view.Menus.PopUpWindow;
import yugioh.view.Menus.WelcomeMenu;
import yugioh.view.Responses;

public class LoginMenuController extends MenuController {
    public static LoginMenuController loginMenuController;
    public TextField usernameField;
    public PasswordField passwordField;

    public LoginMenuController() {

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
            User.setLoggedInUser(user);
        }
    }

    public void loginClicked(MouseEvent mouseEvent) throws Exception {
        playButtonSound();
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.equals("") || password.equals("")) {
            new PopUpWindow(Responses.FILL_ALL_FIELDS.response).start(WelcomeMenu.stage);
        } else {
            String response = "";
            try {
                loginUser(username, password);
                response = Responses.LOGIN_SUCCESSFULLY.response;
                mainMenu.execute();

            } catch (MenuException e) {
                response = e.getMessage();
            }
            new PopUpWindow(response).start(WelcomeMenu.stage);
        }
    }

    public void backClicked(MouseEvent mouseEvent) throws Exception {
        playButtonSound();
        welcomeMenu.execute();
    }
}
