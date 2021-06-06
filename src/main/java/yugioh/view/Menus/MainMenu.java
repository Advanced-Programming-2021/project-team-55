package yugioh.view.Menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import yugioh.controller.menucontroller.MainMenuController;
import yugioh.model.exceptions.MenuException;
import yugioh.view.Regexes;
import yugioh.view.Responses;
import yugioh.view.ViewInterface;

import java.net.URL;
import java.util.regex.Matcher;

public class MainMenu extends WelcomeMenu {

    private static final MainMenuController mainMenuController = MainMenuController.getInstance();

    public void execute()throws Exception {
        start(stage);
    }

    protected String processCommand(String command) {
        String response = "";
        if (command.matches(Regexes.LOGOUT_USER.regex)) {
           // mainMenuController.logout();
            response = Responses.LOGOUT_SUCCESSFULLY.response;
//        } else if (command.matches(Regexes.ENTER_MENU.regex)) {
//            try {
//                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.ENTER_MENU.regex);
//                mainMenuController.enterMenu(matcher.group(1));
//            } catch (MenuException e) {
//                response = e.toString();
//            }
//        } else if (command.matches(Regexes.EXIT_MENU.regex)) {
//            mainMenuController.exitMenu();
//        } else if (command.matches(Regexes.SHOW_MENU.regex)) {
            response = getCurrentMenu();
        } else {
            response = Responses.INVALID_COMMAND.response;
        }
        return response;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url= getClass().getResource("/yugioh/fxml/MainMenu.fxml");
        Parent parent=FXMLLoader.load(url);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }
}
