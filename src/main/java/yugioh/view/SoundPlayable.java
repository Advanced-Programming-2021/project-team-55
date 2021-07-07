package yugioh.view;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

import java.io.File;

public interface SoundPlayable {
    static void playButtonSound(String music) {
        if (music.equals("enterButton"))
            new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\goButton.wav").toURI().toString()).getSource()).play();
        else if (music.equals("backButton"))
            new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\backButton.wav").toURI().toString()).getSource()).play();
        else if (music.equals("summon"))
            new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\summon.mp3").toURI().toString()).getSource()).play();
        else if (music.equals("tribute"))
            new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\tribute.mp3").toURI().toString()).getSource()).play();
        else if (music.equals("attack"))
            new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\attack.mp3").toURI().toString()).getSource()).play();
        else if (music.equals("card"))
            new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\card.mp3").toURI().toString()).getSource()).play();
        else if (music.equals("defence"))
            new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\defence.mp3").toURI().toString()).getSource()).play();

    }
}
