package yugioh.view.Menus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import yugioh.controller.DataBaseController;
import yugioh.model.User;
import yugioh.model.cards.Card;
import yugioh.view.LoggerMessage;

import java.io.IOException;
import java.net.URL;

public class WelcomeMenu extends Application {
    private static final LoginMenu loginMenu = new LoginMenu();
    private static final MainMenu mainMenu = new MainMenu();
    private static final ScoreBoardMenu scoreBoardMenu = new ScoreBoardMenu();
    private static final ProfileMenu profileMenu = new ProfileMenu();
    private static final ShopMenu shopMenu = new ShopMenu();
    private static final DeckMenu deckMenu = new DeckMenu();
    private static final DuelMenu duelMenu = new DuelMenu();
    public static Stage stage;
    public static MenuType currentMenu = MenuType.LOGIN;

    public static void run() throws Exception {
        try {
            DataBaseController.usersDataBaseInitialization();
            DataBaseController.cardsDataBaseInitialization();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread saveDataThread=new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    for (User user : User.getAllUsers()) {
                        try {
                            DataBaseController.saveUserInfo(user);
                        } catch (Exception e) {
                            LoggerMessage.log("unable to save user data");
                            e.printStackTrace();
                        }
                    }
                    for(Card card: Card.getCards()){
                        try {
                            DataBaseController.saveCardInfo(card);
                        }
                        catch (Exception e){
                            LoggerMessage.log("unable to save card data");
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        saveDataThread.setDaemon(true);
        saveDataThread.start();
        new WelcomeMenu().start(new Stage());
    }

    public static String getCurrentMenu() {
        return currentMenu.menuName;
    }

    public static void setCurrentMenu(MenuType currentMenu) {
        WelcomeMenu.currentMenu = currentMenu;
    }

    @Override
    public void start(Stage stage) throws Exception {
        WelcomeMenu.stage = stage;
        URL fxmlAddress = getClass().getResource("/yugioh/fxml/WelcomeMenu.fxml");
        Parent pane = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        scene.setCursor(Cursor.HAND);
        //stage.getIcons().add(new Image("/Sample/JPG/Pacman.jpg"));
        stage.setTitle("Yugioh");
//        stage.setX(700);
//        stage.setY(300);
        stage.show();
    }

    public void execute() throws Exception {
        start(stage);
    }

    protected String processCommand(String command) {
        return "";
    }

}
