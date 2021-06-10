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
       // Media media = new Media("https://hajifirouz3.cdn.asset.aparat.com/aparat-video/4178b30cc9793b2e8c1f8a5d5c316caa34213308-480p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6IjMwY2MzZTZmZWVjNDkzNTI3NzBiZmIzYjQ0NTI2M2I4IiwiZXhwIjoxNjIzMzY1MDUxLCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.-S8XrQKpMXebjDrEKbsodNhpQDFBUDS3ehjMAfbTA_Y");
        //Media media = new Media("https://hajifirouz3.cdn.asset.aparat.com/aparat-video/4178b30cc9793b2e8c1f8a5d5c316caa34213308-480p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6IjMwY2MzZTZmZWVjNDkzNTI3NzBiZmIzYjQ0NTI2M2I4IiwiZXhwIjoxNjIzMzY1MDUxLCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.-S8XrQKpMXebjDrEKbsodNhpQDFBUDS3ehjMAfbTA_Y");

       // MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src\\resources\\yugioh\\Backgrounds\\welcome.m4v").toURI().toString()));
        //mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(-1);
        background.setMediaPlayer(mediaPlayer);

    }
}
