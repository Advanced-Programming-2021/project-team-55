package yugioh.view;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

import java.io.File;

public interface SoundPlayable {
    default void playButtonSound(String music) {
        if (music.equals("enterButton"))
            new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\goButton.wav").toURI().toString()).getSource()).play();
        else if (music.equals("backButton"))
            new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\backButton.wav").toURI().toString()).getSource()).play();
    }
}
