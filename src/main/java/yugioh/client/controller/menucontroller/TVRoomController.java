package yugioh.client.controller.menucontroller;

import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import yugioh.client.view.NetAdapter;
import yugioh.client.view.SoundPlayable;

import java.net.URL;
import java.util.ResourceBundle;

public class TVRoomController extends MenuController implements Initializable {

    public ImageView tvScreenImage;
    public MenuButton currentGames;

    public void backClicked() throws Exception{
        SoundPlayable.playButtonSound("backButton");
        mainMenu.execute();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String games = NetAdapter.sendRequest("get games");
        String[] gamesArray = games.split(",");
        for (String s : gamesArray) {
            MenuItem menuItem = new MenuItem();
            menuItem.setText(s);
            menuItem.setOnAction((event) -> {
                new Thread(() -> {
                    while (true) {
                        NetAdapter.justSendRequest("get game TV: " + s);

                    }
                }).start();
            });
            currentGames.getItems().add(menuItem);
        }
    }

}
