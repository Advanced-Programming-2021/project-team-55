package yugioh.client.view.menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
