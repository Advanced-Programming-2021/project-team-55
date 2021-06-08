package yugioh.view.Menus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import yugioh.controller.DataBaseController;

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
//        launch(args);
        new WelcomeMenu().start(new Stage());
//        while (true) {
//            switch (currentMenu) {
//                case LOGIN: {
//                    loginMenu.execute();
//                    break;
//                }
//                case MAIN: {
//                    mainMenu.execute();
//                    break;
//                }
//                case PROFILE: {
//                    profileMenu.execute();
//                    break;
//                }
//                case SCOREBOARD: {
//                    scoreBoardMenu.execute();
//                    break;
//                }
//                case SHOP: {
//                    shopMenu.execute();
//                    break;
//                }
//                case DECK: {
//                    deckMenu.execute();
//                    break;
//                }
//                case DUEL: {
//                    duelMenu.execute();
//                    break;
//                }
//            }
//        }
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
