package yugioh.client.controller.menucontroller;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
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
import yugioh.client.view.ViewInterface;
import yugioh.client.view.menus.PopUpWindow;
import yugioh.client.view.menus.WelcomeMenu;
import yugioh.server.view.Regexes;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;

public class LoginMenuController extends MenuController implements Initializable {
    public static LoginMenuController loginMenuController;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button loginButton;
    public MediaView loginMenuBackground;
    boolean passwordFieldIsFilled = false;
    boolean usernameFieldIsFilled = false;
    @FXML
    private MediaView background;

    public LoginMenuController() {

    }

    public static LoginMenuController getInstance() {
        if (loginMenuController == null) {
            loginMenuController = new LoginMenuController();
        }
        return loginMenuController;
    }

    public String loginUser(String username, String password) throws Exception {

        String result = ViewInterface.showResult( "user login --password " + password + " --username " + username );
        if (result.startsWith("Error: ")) throw new Exception(result);
        User user = User.getUserByUsername(username);

//        if (user == null || !user.getPassword().equals(password)) {
//            throw new MenuException(Responses.USERNAME_AND_PASSWORD_DIDNT_MATCH.response);
//        } else {
            User.setLoggedInUser(user);

//        }
        return result;
    }

    public void loginClicked(MouseEvent mouseEvent) throws Exception {
        SoundPlayable.playButtonSound("enterButton");
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.equals("") || password.equals("")) {
            new PopUpWindow(Responses.FILL_ALL_FIELDS.response).start(WelcomeMenu.stage);
        } else {
            String response = "";
            try {
                response = loginUser(username, password);
                if (response.startsWith("success ")) {
                    Matcher matcher = ViewInterface.getCommandMatcher(response, "success (.+)");
                    User.setToken(matcher.group(1));
                    mainMenu.execute();
                }
            } catch (MenuException e) {
                response = e.getMessage();
            }
            new PopUpWindow(response).start(WelcomeMenu.stage);
        }
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
        loginMenuBackground.setMediaPlayer(mediaPlayer);
        loginButton.setDisable(true);
        usernameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.equals("")) {
                    usernameFieldIsFilled = false;
                    loginButton.setDisable(true);
                } else {
                    usernameFieldIsFilled = true;
                    if (passwordFieldIsFilled)
                        loginButton.setDisable(false);
                }
            }
        });
        passwordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.equals("")) {
                    passwordFieldIsFilled = false;
                    loginButton.setDisable(true);
                } else {
                    passwordFieldIsFilled = true;
                    if (usernameFieldIsFilled)
                        loginButton.setDisable(false);
                }
            }
        });
    }

    public void initialize() {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src\\resources\\yugioh\\Backgrounds\\main.mp4").toURI().toString()));
        mediaPlayer.play();
        mediaPlayer.setCycleCount(-1);
        background.setMediaPlayer(mediaPlayer);
    }
}