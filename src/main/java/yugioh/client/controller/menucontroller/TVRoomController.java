package yugioh.client.controller.menucontroller;

import javafx.scene.input.MouseEvent;
import yugioh.client.view.SoundPlayable;

public class TVRoomController extends MenuController{
    public void backClicked(MouseEvent mouseEvent) throws Exception{
        SoundPlayable.playButtonSound("backButton");
        mainMenu.execute();
    }
}
