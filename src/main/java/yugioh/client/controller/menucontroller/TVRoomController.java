package yugioh.client.controller.menucontroller;

import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import yugioh.client.view.SoundPlayable;

public class TVRoomController extends MenuController {

    public ImageView tvScreenImage;
    public MenuButton currentGames;

    public void backClicked() throws Exception{
        SoundPlayable.playButtonSound("backButton");
        mainMenu.execute();
    }

}
