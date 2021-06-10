package yugioh.controller.menucontroller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import yugioh.model.User;
import yugioh.model.exceptions.MenuException;
import yugioh.view.Menus.PopUpWindow;
import yugioh.view.Menus.WelcomeMenu;
import yugioh.view.Responses;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileMenuController extends MenuController implements Initializable {
    public static ProfileMenuController profileMenuController;
    public TextField nicknameField;
    public TextField oldPasswordField;
    public TextField newPasswordField;
    public Button changePasswordButton;
    boolean isNewPasswordFieldFilled=false;
    boolean isOldPasswordFieldFilled=false;

    public ProfileMenuController() {
    }

    public static ProfileMenuController getInstance() {
        if (profileMenuController == null) profileMenuController = new ProfileMenuController();
        return profileMenuController;
    }

    public void changeNickname(String nickname) throws MenuException {
        if (User.nicknameExists(nickname)) {
            throw new MenuException("Error: user with nickname " + nickname + " already exists");
        } else {
            User.loggedInUser.setNickname(nickname);
        }
    }

    public void changePassword(String currentPassword, String newPassword) throws MenuException {
        if (!User.loggedInUser.getPassword().equals(currentPassword)) {
            throw new MenuException("Error: current password is invalid");
        } else if (newPassword.equals(currentPassword)) {
            throw new MenuException("Error: please enter a new password");
        }
        User.loggedInUser.setPassword(newPassword);
    }


    public void backClicked(MouseEvent mouseEvent) throws Exception {
        playButtonSound();
        User.loggedInUser.setNickname(nicknameField.getText());
        mainMenu.execute();
    }

    public void changePasswordClicked(MouseEvent mouseEvent) throws Exception {
        playButtonSound();
        if (oldPasswordField.getText().equals("") || newPasswordField.getText().equals("")) {
            new PopUpWindow(Responses.FILL_ALL_FIELDS.response).start(WelcomeMenu.stage);
        } else {
            String response = "";
            try {
                changePassword(oldPasswordField.getText(), newPasswordField.getText());
                response = Responses.PASSWORD_CHANGED_SUCCESSFULLY.response;
            } catch (MenuException e) {
                response = e.getMessage();
            }
            new PopUpWindow(response).start(WelcomeMenu.stage);
        }
        oldPasswordField.setText("");
        newPasswordField.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changePasswordButton.setDisable(true);
        newPasswordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1.equals("")){
                    isNewPasswordFieldFilled=false;
                    changePasswordButton.setDisable(true);
                }
                else{
                    isNewPasswordFieldFilled=true;
                    if(isOldPasswordFieldFilled){
                        changePasswordButton.setDisable(false);
                    }
                }
            }
        });
        oldPasswordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1.equals("")){
                    isOldPasswordFieldFilled=false;
                    changePasswordButton.setDisable(true);
                }
                else{
                    isOldPasswordFieldFilled=true;
                    if(isNewPasswordFieldFilled){
                        changePasswordButton.setDisable(false);
                    }
                }
            }
        });
    }
}
