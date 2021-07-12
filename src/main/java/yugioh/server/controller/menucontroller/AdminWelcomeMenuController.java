package yugioh.server.controller.menucontroller;

import javafx.scene.input.MouseEvent;
import yugioh.server.view.Menus.AdminShopMenu;


public class AdminWelcomeMenuController {
    public void enterAdminShop(MouseEvent mouseEvent) {
        new AdminShopMenu().execute();
    }
}
