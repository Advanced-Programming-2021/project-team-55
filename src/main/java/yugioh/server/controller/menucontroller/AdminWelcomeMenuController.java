package yugioh.server.controller.menucontroller;

import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import yugioh.server.view.Menus.AdminShopMenu;

import java.net.URL;
import java.util.ResourceBundle;


public class AdminWelcomeMenuController implements Initializable {

    public static AdminWelcomeMenuController adminWelcomeMenuController;
    public ImageView tv;

    public void enterAdminShop(MouseEvent mouseEvent) {
        new AdminShopMenu().execute();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminWelcomeMenuController = this;
    }
}
