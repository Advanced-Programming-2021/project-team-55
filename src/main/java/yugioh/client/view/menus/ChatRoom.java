package yugioh.client.view.menus;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import yugioh.client.controller.menucontroller.ChatRoomController;

import java.net.URL;

public class ChatRoom extends WelcomeMenu{
    @Override
    public void execute() throws Exception {
        start(stage);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url=getClass().getResource("/yugioh/fxml/ChatRoom.fxml");
        Parent parent= FXMLLoader.load(url);
        Scene scene=new Scene(parent);
        stage.setScene(scene);
        stage.show();

    }
}
