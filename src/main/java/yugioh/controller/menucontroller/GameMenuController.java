package yugioh.controller.menucontroller;


import javafx.fxml.Initializable;
import yugioh.view.Menus.DuelMenu;

import java.net.URL;
import java.util.ResourceBundle;

public class GameMenuController extends MenuController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void backClicked() throws Exception {
        new DuelMenu().execute();
    }
}
