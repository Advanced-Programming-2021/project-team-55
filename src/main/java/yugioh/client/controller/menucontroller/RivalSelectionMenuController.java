package yugioh.client.controller.menucontroller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import yugioh.client.controller.DataBaseController;
import yugioh.client.model.User;
import yugioh.client.view.NetAdapter;
import yugioh.client.view.SoundPlayable;
import yugioh.client.view.ViewInterface;
import yugioh.client.view.menus.PopUpWindow;
import yugioh.client.view.menus.RivalSelectionMenu;
import yugioh.client.view.menus.WelcomeMenu;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;

public class RivalSelectionMenuController extends MenuController implements Initializable {

    public static RivalSelectionMenuController deckMenuController;

    private static User[] awaitingUsersForOneRound;
    private static User[] awaitingUsersForThreeRounds;

    public ToggleButton threeRounds;
    public ToggleButton oneRound;
    public JFXButton start;
    public MediaView rivalSelectionMenuBackground;
    public GridPane waitingUsersGridPane;

    public RivalSelectionMenuController() {
        deckMenuController = this;
    }

    public static RivalSelectionMenuController getInstance() {
        return Objects.requireNonNullElseGet(deckMenuController, RivalSelectionMenuController::new);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src\\resources\\yugioh\\Backgrounds\\main.mp4").toURI().toString()));
        mediaPlayer.play();
        mediaPlayer.setCycleCount(-1);
        rivalSelectionMenuBackground.setMediaPlayer(mediaPlayer);
        RivalSelectionMenu.setDoCancel(false);
        RivalSelectionMenu.setRival(null);
        oneRound.setSelected(false);
        threeRounds.setSelected(true);
        RivalSelectionMenu.setRounds(3);
        awaitingUsersForOneRound = DataBaseController.parseAllUsers(ViewInterface.showResult("get awaiting users for 1 round"));
        for (int i = 0; i < awaitingUsersForOneRound.length; i++) {
            User user = awaitingUsersForOneRound[i];
            waitingUsersGridPane.add(new Text("1 ROUND"), 0, i);
            waitingUsersGridPane.add(new Text(user.getUsername()), 1, i);
            waitingUsersGridPane.add(new Text("SCORE: " + user.getScore()), 2, i);
        }
        awaitingUsersForThreeRounds = DataBaseController.parseAllUsers(ViewInterface.showResult("get awaiting users for 3 rounds"));
        for (int i = 0; i < awaitingUsersForThreeRounds.length; i++) {
            User user = awaitingUsersForThreeRounds[i];
            waitingUsersGridPane.add(new Text("3 ROUNDS"), 0, i + awaitingUsersForOneRound.length);
            waitingUsersGridPane.add(new Text(user.getUsername()), 1, i + awaitingUsersForOneRound.length);
            waitingUsersGridPane.add(new Text("SCORE: " + user.getScore()), 2, i + awaitingUsersForOneRound.length);
        }
    }

    public void cancel() throws Exception {
        SoundPlayable.playButtonSound("backButton");
        RivalSelectionMenu.setDoCancel(true);
        RivalSelectionMenu.getStage().close();
    }

    public void startGame() throws Exception {
        String result = NetAdapter.dataInputStream.readUTF();
        if (result.startsWith("Error: ")) {
            new PopUpWindow(result).start(RivalSelectionMenu.getStage());
            return;
        }
        Matcher matcher = ViewInterface.getCommandMatcher(result, "success (.+)");
        User rival = DataBaseController.getUserObjectByString(matcher.group(1));
        SoundPlayable.playButtonSound("enterButton");
        RivalSelectionMenu.setRival(rival);
        RivalSelectionMenu.setDoCancel(false);
        RivalSelectionMenu.getStage().close();
    }

    public void clickOneRound() {
        SoundPlayable.playButtonSound("enterButton");
        threeRounds.setSelected(false);
        oneRound.setSelected(true);
        RivalSelectionMenu.setRounds(1);
    }

    public void clickThreeRounds() {
        SoundPlayable.playButtonSound("enterButton");
        oneRound.setSelected(false);
        threeRounds.setSelected(true);
        RivalSelectionMenu.setRounds(3);
    }

    public void sendGameRequest() throws Exception {
        NetAdapter.justSendRequest("duel --new --rounds " + RivalSelectionMenu.getRounds());
        startGame();
    }
}
