package yugioh.controller.menucontroller;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import yugioh.model.User;
import yugioh.model.exceptions.MenuException;
import yugioh.view.Menus.PopUpWindow;
import yugioh.view.Menus.WelcomeMenu;
import yugioh.view.Responses;

public class RegisterMenuController extends MenuController {
    public TextField usernameField;
    public TextField nicknameField;
    public PasswordField passwordField;

    public void RegisterClicked(MouseEvent mouseEvent) throws Exception {
        String username = usernameField.getText();
        String nickname = nicknameField.getText();
        String password = passwordField.getText();
        if (username.equals("") || nickname.equals("") || password.equals("")) {
            new PopUpWindow(Responses.FILL_ALL_FIELDS.response).start(WelcomeMenu.stage);
        } else {
            String response = "";
            try {
                createUser(username, password, nickname);
                response = Responses.CREATE_SUCCESSFULLY.response;
                loginMenu.execute();
            } catch (MenuException e) {
                response = e.getMessage();
            }
            new PopUpWindow(response).start(WelcomeMenu.stage);
        }
        usernameField.setText("");
        nicknameField.setText("");
        passwordField.setText("");

    }

    public void createUser(String username, String password, String nickname) throws MenuException {
        if (User.usernameExists(username)) {
            throw new MenuException("Error: user with username " + username + " exists");
        } else if (User.nicknameExists(nickname)) {
            throw new MenuException("Error: user with nickname " + nickname + " already exists");
        }
        new User(username, nickname, password);
    }

    public void backClicked(MouseEvent mouseEvent) throws Exception {
        welcomeMenu.execute();
    }

}
