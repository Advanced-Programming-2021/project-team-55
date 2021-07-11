package controller;

import javafx.scene.input.MouseEvent;
import view.AdminShopMenu;

public class AdminWelcomeMenuController {
    public void enterAdminShop(MouseEvent mouseEvent) {
        new AdminShopMenu().execute();
    }
}
