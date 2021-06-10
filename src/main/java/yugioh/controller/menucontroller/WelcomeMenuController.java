package yugioh.controller.menucontroller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class WelcomeMenuController extends MenuController {

    static int x = 1;
    @FXML
    private MediaView background;

    public void enterRegisterMenuClicked(MouseEvent mouseEvent) {
        playButtonSound();
        try {
            registerMenu.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enterLoginMenuClicked(MouseEvent mouseEvent) {
        playButtonSound();
        try {
            loginMenu.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exitClicked(MouseEvent mouseEvent) {
        playButtonSound();
        System.exit(0);
    }

    public void initialize() {
        if (x==1) {
            AudioClip audioClip =  new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\backMusic.mp3").toURI().toString()).getSource());
            audioClip.setCycleCount(-1);
            audioClip.play();
            audioClip.setCycleCount(-1);
            x=-1;
        }
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src\\resources\\yugioh\\Backgrounds\\welcome.m4v").toURI().toString()));
        mediaPlayer.play();
        mediaPlayer.setCycleCount(-1);
        background.setMediaPlayer(mediaPlayer);
    }
}
