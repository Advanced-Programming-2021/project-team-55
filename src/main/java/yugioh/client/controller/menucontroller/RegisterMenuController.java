package yugioh.client.controller.menucontroller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import yugioh.client.model.User;
import yugioh.client.model.exceptions.MenuException;
import yugioh.client.view.Responses;
import yugioh.client.view.SoundPlayable;
import yugioh.client.view.menus.PopUpWindow;
import yugioh.client.view.menus.WelcomeMenu;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterMenuController extends MenuController implements Initializable {
    public TextField usernameField;
    public TextField nicknameField;
    public PasswordField passwordField;
    public Button registerButton;
    public MediaView signupMenuBackground;
    boolean usernameFieldIsFilled = false;
    boolean passwordFieldIsFilled = false;
    boolean nicknameFieldIsFilled = false;

    public void RegisterClicked(MouseEvent mouseEvent) throws Exception {
        SoundPlayable.playButtonSound("enterButton");
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
        SoundPlayable.playButtonSound("backButton");
        welcomeMenu.execute();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src\\resources\\yugioh\\Backgrounds\\signup.mp4").toURI().toString()));
        mediaPlayer.play();
        mediaPlayer.setCycleCount(-1);
        signupMenuBackground.setMediaPlayer(mediaPlayer);
        registerButton.setDisable(true);
        usernameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.equals("")) {
                    usernameFieldIsFilled = false;
                    registerButton.setDisable(true);
                } else {
                    usernameFieldIsFilled = true;
                    if (passwordFieldIsFilled && nicknameFieldIsFilled)
                        registerButton.setDisable(false);
                }
            }
        });
        passwordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.equals("")) {
                    passwordFieldIsFilled = false;
                    registerButton.setDisable(true);
                } else {
                    passwordFieldIsFilled = true;
                    if (usernameFieldIsFilled && nicknameFieldIsFilled)
                        registerButton.setDisable(false);
                }
            }
        });
        nicknameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.equals("")) {
                    nicknameFieldIsFilled = false;
                    registerButton.setDisable(true);
                } else {
                    nicknameFieldIsFilled = true;
                    if (usernameFieldIsFilled && passwordFieldIsFilled)
                        registerButton.setDisable(false);
                }
            }
        });

    }
}
