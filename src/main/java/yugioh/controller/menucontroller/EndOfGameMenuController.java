package yugioh.controller.menucontroller;


import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import yugioh.view.menus.EndOfGameMenu;

import java.net.URL;
import java.util.ResourceBundle;


public class EndOfGameMenuController implements Initializable {

    public Label result;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        result.setText(EndOfGameMenu.getResultString());
    }

    public void closeStage() {
        EndOfGameMenu.closeStage();
    }

}
