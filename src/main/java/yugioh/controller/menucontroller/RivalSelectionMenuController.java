package yugioh.controller.menucontroller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import yugioh.model.User;
import yugioh.view.SoundPlayable;
import yugioh.view.menus.PopUpWindow;
import yugioh.view.menus.RivalSelectionMenu;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class RivalSelectionMenuController extends MenuController implements Initializable {

    public static RivalSelectionMenuController deckMenuController;

    public ChoiceBox<User> rivals;
    public ToggleButton threeRounds;
    public ToggleButton oneRound;
    public JFXButton start;

    public RivalSelectionMenuController() {
        deckMenuController = this;
    }

    public static RivalSelectionMenuController getInstance() {
        return Objects.requireNonNullElseGet(deckMenuController, RivalSelectionMenuController::new);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RivalSelectionMenu.setDoCancel(false);
        RivalSelectionMenu.setRival(null);
        oneRound.setSelected(false);
        threeRounds.setSelected(true);
        RivalSelectionMenu.setRounds(3);
        ArrayList<User> allUsers = User.getAllUsers();
        for (User user : allUsers) {
            if (User.loggedInUser == user) continue;
            rivals.getItems().add(user);
        }
    }

    public void cancel() throws Exception {
        SoundPlayable.playButtonSound("backButton");
        RivalSelectionMenu.setDoCancel(true);
        RivalSelectionMenu.getStage().close();
    }

    public void startGame() throws Exception {
        if (rivals.getValue() == null) {
            new PopUpWindow("Error: select a user first!").start(RivalSelectionMenu.getStage());
            return;
        }
        SoundPlayable.playButtonSound("enterButton");
        RivalSelectionMenu.setRival(rivals.getValue());
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

}
