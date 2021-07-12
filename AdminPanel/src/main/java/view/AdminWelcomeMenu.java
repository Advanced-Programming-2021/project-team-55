package view;

import controller.DatabaseController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import model.cards.Card;
import model.cards.CardsInventory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class AdminWelcomeMenu extends Application {
    public static Stage stage;
    public void execute(){
        try {
            DatabaseController.cardsDataBaseInitialization();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Thread databaseThread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    DatabaseController.updateCardsInventory();
                    for(Card card: Card.allCards){
                        try {
                            DatabaseController.saveCardInfo(card);
                        } catch (IOException e) {e.printStackTrace();}
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //databaseThread.setDaemon();
        databaseThread.setName("database thread");
        databaseThread.start();
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url=getClass().getResource("/yugioh/fxml/AdminWelcomeMenu.fxml");
        Parent parent = FXMLLoader.load(url);
        AdminWelcomeMenu.stage=stage;
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
