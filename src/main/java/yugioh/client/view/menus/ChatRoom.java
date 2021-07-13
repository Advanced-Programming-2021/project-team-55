package yugioh.client.view.menus;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import yugioh.client.controller.menucontroller.ChatRoomController;

import java.net.URL;

public class ChatRoom extends WelcomeMenu{
    public static Stage chatStage;
    private double xOffset = 0;
    private double yOffset = 0;

    public static void close() {
        if(chatStage!=null){
            chatStage.close();
        }
    }

    @Override
    public void execute() throws Exception {
        close();
        chatStage =new Stage();
        chatStage.initOwner(WelcomeMenu.stage);
        chatStage.initStyle(StageStyle.UNDECORATED);
        start(chatStage);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url=getClass().getResource("/yugioh/fxml/ChatRoom.fxml");
        Parent parent= FXMLLoader.load(url);
        Scene scene=WelcomeMenu.createScene(parent);
        parent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        parent.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        stage.setScene(scene);
        stage.show();

    }
}
