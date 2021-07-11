package yugioh.client.view;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

import java.io.File;

public interface SoundPlayable {
    static void playButtonSound(String music) {
        switch (music) {
            case "enterButton":
                new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\goButton.wav").toURI().toString()).getSource()).play();
                break;
            case "backButton":
                new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\backButton.wav").toURI().toString()).getSource()).play();
                break;
            case "summon":
                new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\summon.mp3").toURI().toString()).getSource()).play();
                break;
            case "tribute":
                new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\tribute.mp3").toURI().toString()).getSource()).play();
                break;
            case "attack":
                new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\attack.mp3").toURI().toString()).getSource()).play();
                break;
            case "card":
                new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\card.mp3").toURI().toString()).getSource()).play();
                break;
            case "defence":
                new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\defence.mp3").toURI().toString()).getSource()).play();
                break;
            case "graveYard":
                new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\graveYard.mp3").toURI().toString()).getSource()).play();
                break;
            case "sword":
                new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\sword.mp3").toURI().toString()).getSource()).play();
                break;
            case "forHonor":
                new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\forHonor.mp3").toURI().toString()).getSource()).play();
                break;
            case "surrender":
                new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\surrender.mp3").toURI().toString()).getSource()).play();
                break;
            case "neverSurrender":
                new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\neverSurrender.mp3").toURI().toString()).getSource()).play();
                break;
            case "weNeverSurrender":
                new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\weNeverSurrender.mp3").toURI().toString()).getSource()).play();
                break;
            case "evilLaugh":
                new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\evilLaugh.mp3").toURI().toString()).getSource()).play();
                break;
            case "explosion":
                new AudioClip(new Media(new File("src\\resources\\yugioh\\Audio\\explosion.wav").toURI().toString()).getSource()).play();
                break;
        }

    }
}
