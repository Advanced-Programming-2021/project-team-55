package view;

import controller.ChatRoomController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;

import static controller.ChatRoomController.setupConnection;

public class ChatRoom extends Application {
    public static Stage stage;

    public void execute(){
        setupConnection();
        launch();
    }



    @Override
    public void start(Stage stage) throws Exception {
        URL url=getClass().getResource("/Sample/fxml/ChatRoom.fxml");
        Parent parent= FXMLLoader.load(url);
        Scene scene=new Scene(parent);
        ChatRoom.stage=stage;
        stage.setTitle(ChatRoomController.username);
        stage.setScene(scene);
        stage.show();

    }
}
