package yugioh.client.controller.menucontroller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import yugioh.client.view.NetAdapter;
import yugioh.client.view.SoundPlayable;
import yugioh.server.controller.menucontroller.AdminWelcomeMenuController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class TVRoomController extends MenuController implements Initializable {

    private boolean isEnded = false;

    public ImageView tvScreenImage;
    public MenuButton currentGames;

    public void backClicked() throws Exception {
        isEnded = true;
        SoundPlayable.playButtonSound("backButton");
        mainMenu.execute();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isEnded = false;
        String games = NetAdapter.sendRequest("get games");
        String[] gamesArray = games.split(",");
        for (String s : gamesArray) {
            MenuItem menuItem = new MenuItem();
            menuItem.setText(s);
            menuItem.setOnAction((event) -> {
                new Thread(() -> {
                    while (true) {
                        try {
                            if (isEnded) return;
                            Socket tvSocket = new Socket("localhost", 9596);

                            DataInputStream tvDataInputStream = new DataInputStream(tvSocket.getInputStream());;
                            DataOutputStream tvDataOutputStream = new DataOutputStream(tvSocket.getOutputStream());
                            tvDataOutputStream.writeUTF("get game TV: " + s);
                            tvDataOutputStream.flush();

                            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(tvDataInputStream.readAllBytes()));
                            tvScreenImage.setImage(SwingFXUtils.toFXImage(bufferedImage, null));

                            Thread.sleep(8000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            });
            currentGames.getItems().add(menuItem);
        }
    }

}
