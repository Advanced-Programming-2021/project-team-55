package yugioh.server.view.Menus;


import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import yugioh.server.model.cards.Card;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import yugioh.server.controller.DataBaseController;
import yugioh.server.model.cards.CardsInventory;

public class AdminWelcomeMenu extends Application {
    public static Stage stage;
    public void execute(){
//        try {
//            DataBaseController.cardsDataBaseInitialization();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        Thread databaseThread=new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    DataBaseController.updateCardsInventory();
                    for(Card card: Card.allCards){
                        try {
                            DataBaseController.saveCardInfo(card);
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
        databaseThread.setDaemon(true);
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
