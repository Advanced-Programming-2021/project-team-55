package yugioh.client.view.menus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import yugioh.client.controller.DataBaseController;
import yugioh.client.controller.menucontroller.WelcomeMenuController;
import yugioh.client.model.User;
import yugioh.client.model.cards.Card;
import yugioh.client.view.LoggerMessage;
import yugioh.server.model.UserHolder;

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
//            DataBaseController.usersDataBaseInitialization();
            DataBaseController.cardsDataBaseInitialization();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread saveDataThread = new Thread(() -> {
            while (true) {
                   try {
                       DataBaseController.saveUserInfo(User.loggedInUser);
                   } catch (Exception e) {
                       LoggerMessage.log("unable to save user data");
                       e.printStackTrace();
                   }
                try {
//                    for (Card card : Card.getCards()) {
//                        try {
//                            DataBaseController.saveCardInfo(card);
//                        } catch (Exception e) {
//                            LoggerMessage.log("unable to save card data");
//                            e.printStackTrace();
//                        }
//                    }
                } catch (Exception e) {
                }
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }}, "save data");
        saveDataThread.setDaemon(true);
        saveDataThread.start();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        new WelcomeMenu().start(stage);
    }

    public static String getCurrentMenu() {
        return currentMenu.menuName;
    }

    public static void setCurrentMenu(MenuType currentMenu) {
        WelcomeMenu.currentMenu = currentMenu;
    }

    public static Stage getStage() {
        //stage.setFullScreen(true);
        return stage;
    }

    public static Scene createScene(Parent parent) {
        Scene scene = new Scene(parent);
        scene.setCursor(Cursor.HAND);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.M && event.isControlDown()) {
                WelcomeMenuController.muteSound();
            }
        });
        return scene;
    }

    @Override
    public void start(Stage stage) throws Exception {
        WelcomeMenu.stage = stage;
        URL fxmlAddress = getClass().getResource("/yugioh/fxml/WelcomeMenu.fxml");
        Parent pane = FXMLLoader.load(fxmlAddress);
        Scene scene = createScene(pane);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/yugioh/PNG/icon/icon.png"));
        stage.setTitle("Yu Gi Oh");
        stage.show();
    }

    public void execute() throws Exception {
        start(stage);
    }

    public String processCommand(String command) {
        return "";
    }

    public static DuelMenu getDuelMenu() {
        return duelMenu;
    }
}
