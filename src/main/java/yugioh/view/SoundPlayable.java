package yugioh.view;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public interface SoundPlayable {
    default void playButtonSound(){
        new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\button.wav").toURI().toString()).getSource()).play();
    }
}
