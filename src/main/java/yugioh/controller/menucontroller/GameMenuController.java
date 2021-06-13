package yugioh.controller.menucontroller;


import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import yugioh.view.Menus.DuelMenu;
import yugioh.view.gamephases.Duel;

import java.net.URL;
import java.util.ResourceBundle;

public class GameMenuController extends MenuController implements Initializable {

    public ImageView hoveredImage;
    public ScrollPane descriptionContainer;
    public Label description;
    public Label atkLabel;
    public Label defLabel;
    public Label defValue;
    public Label atkValue;
    public Label rivalLP;
    public ProgressBar rivalLPBar;
    public Label userLP;
    public ProgressBar userLPBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateGameStatusUIs();
    }

    public void backClicked() throws Exception {
        new DuelMenu().execute();
    }

    public void updateGameStatusUIs() {
        int opponentLP = Duel.getGameController().currentTurnOpponentPlayer.getLP();
        int myLP = Duel.getGameController().currentTurnPlayer.getLP();
        rivalLP.setText(opponentLP + "");
        userLP.setText(myLP + "");
        rivalLPBar.setProgress((double) opponentLP / 8000);
        userLPBar.setProgress((double) myLP / 8000);
    }

}
