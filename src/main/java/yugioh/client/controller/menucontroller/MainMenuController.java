package yugioh.client.controller.menucontroller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import yugioh.client.model.User;
import yugioh.client.view.SoundPlayable;
import yugioh.client.view.ViewInterface;

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
        ViewInterface.showResult("menu enter Duel");
        SoundPlayable.playButtonSound("enterButton");
        duelMenu.execute();
    }

    public void enterDeckMenuClicked(MouseEvent mouseEvent) throws Exception {
        ViewInterface.showResult("menu enter Deck");
        SoundPlayable.playButtonSound("enterButton");
        deckMenu.execute();
    }

    public void enterScoreBoardMenuClicked(MouseEvent mouseEvent) throws Exception {
        ViewInterface.showResult("menu enter Scoreboard");
        SoundPlayable.playButtonSound("enterButton");
        scoreBoardMenu.execute();
    }

    public void enterProfileMenuClicked(MouseEvent mouseEvent) throws Exception {
        ViewInterface.showResult("menu enter Profile");
        SoundPlayable.playButtonSound("enterButton");
        profileMenu.execute();
    }

    public void enterShopMenuClicked(MouseEvent mouseEvent) throws Exception {
        ViewInterface.showResult("menu enter Shop");
        SoundPlayable.playButtonSound("enterButton");
        shopMenu.execute();
    }

    public void enterImportExportClicked(MouseEvent mouseEvent) throws Exception {
        ViewInterface.showResult("menu enter Import/Export");
        SoundPlayable.playButtonSound("enterButton");
        importExportMenu.execute();
    }

    public void logoutClicked(MouseEvent mouseEvent) throws Exception {
        ViewInterface.showResult("user logout");
        SoundPlayable.playButtonSound("backButton");
        logout();
    }

    public void initialize() {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src\\resources\\yugioh\\Backgrounds\\main.mp4").toURI().toString()));
        mediaPlayer.play();
        mediaPlayer.setCycleCount(-1);
        background.setMediaPlayer(mediaPlayer);
    }

    public void enterChatMenuClicked(MouseEvent mouseEvent)throws Exception {
        ViewInterface.showResult("menu enter chatRoom");
        SoundPlayable.playButtonSound("enterButton");
        chatRoom.execute();
    }
}
