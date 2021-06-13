package yugioh.controller.menucontroller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import yugioh.model.User;
import yugioh.model.cards.Card;
import yugioh.model.cards.Deck;
import yugioh.model.cards.SpellAndTrap;
import yugioh.model.exceptions.MenuException;
import yugioh.view.Menus.*;
import yugioh.view.Responses;

import java.net.URL;
import java.util.*;

import static yugioh.model.cards.cardfeaturesenums.EffectiveTerm.LIMITED;

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
        RivalSelectionMenu.setRounds(1);
        ArrayList<User> allUsers = User.getAllUsers();
        for (User user : allUsers) {
            rivals.getItems().add(user);
        }
    }

    public void cancel() throws Exception {
        RivalSelectionMenu.setDoCancel(true);
        RivalSelectionMenu.getStage().close();
    }

    public void startGame() {
        RivalSelectionMenu.setDoCancel(false);
        RivalSelectionMenu.getStage().close();
    }

    public void clickOneRound() {
        threeRounds.setSelected(false);
        oneRound.setSelected(true);
        RivalSelectionMenu.setRounds(1);
    }

    public void clickThreeRounds() {
        oneRound.setSelected(false);
        threeRounds.setSelected(true);
        RivalSelectionMenu.setRounds(3);
    }
}
