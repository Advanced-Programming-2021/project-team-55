package yugioh.controller.menucontroller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import yugioh.model.User;

import java.io.File;

public class MainMenuController extends MenuController {

    public static MainMenuController mainMenuController;
    @FXML
    private MediaView background;
    public MainMenuController() {

    }

    public static MainMenuController getInstance() {
        if (mainMenuController == null) {
            mainMenuController = new MainMenuController();
        }
        return mainMenuController;
    }

    public void logout() throws Exception {
        User.setLoggedInUser(null);
        welcomeMenu.execute();
    }

    public void enterDuelMenuClicked(MouseEvent mouseEvent) throws Exception {
        playButtonSound();
        duelMenu.execute();
    }

    public void enterDeckMenuClicked(MouseEvent mouseEvent) throws Exception
    {playButtonSound();
        deckMenu.execute();
    }

    public void enterScoreBoardMenuClicked(MouseEvent mouseEvent) throws Exception {
        playButtonSound();
        scoreBoardMenu.execute();
    }

    public void enterProfileMenuClicked(MouseEvent mouseEvent) throws Exception {
        playButtonSound();
        profileMenu.execute();
    }

    public void enterShopMenuClicked(MouseEvent mouseEvent) throws Exception {
        playButtonSound();
        shopMenu.execute();
    }

    public void enterImportExportClicked(MouseEvent mouseEvent) throws Exception {
        playButtonSound();
        importExportMenu.execute();
    }

    public void logoutClicked(MouseEvent mouseEvent) throws Exception {
        playButtonSound();
        logout();
    }
    public void initialize() {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src\\resources\\yugioh\\Backgrounds\\main.mp4").toURI().toString()));
        mediaPlayer.play();
        mediaPlayer.setCycleCount(-1);
        background.setMediaPlayer(mediaPlayer);

    }

}
