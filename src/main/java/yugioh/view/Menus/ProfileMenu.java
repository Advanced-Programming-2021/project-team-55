package yugioh.view.Menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import yugioh.controller.menucontroller.ProfileMenuController;
import yugioh.model.exceptions.MenuException;
import yugioh.view.Regexes;
import yugioh.view.Responses;
import yugioh.view.ViewInterface;

import java.net.URL;
import java.util.regex.Matcher;

public class ProfileMenu extends WelcomeMenu {
    private static final ProfileMenuController profileMenuController = ProfileMenuController.getInstance();


    @Override
    public void execute() throws Exception{
        start(stage);
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        if (command.matches(Regexes.CHANGE_NICKNAME.regex)) {
            try {
                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.CHANGE_NICKNAME.regex);
                profileMenuController.changeNickname(matcher.group(1));
                response = Responses.NICKNAME_CHANGED_SUCCESSFULLY.response;
            } catch (MenuException e) {
                response = e.toString();
            }
        } else if (command.matches(Regexes.CHANGE_PASSWORD.regex)) {
            try {
                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.CHANGE_PASSWORD.regex);
                profileMenuController.changePassword(matcher.group(1), matcher.group(2));
                response = Responses.PASSWORD_CHANGED_SUCCESSFULLY.response;
            } catch (MenuException e) {
                response = e.toString();
            }
//        } else if (command.matches(Regexes.ENTER_MENU.regex)) {
//            try {
//                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.ENTER_MENU.regex);
//                profileMenuController.enterMenu(matcher.group(1));
//            } catch (MenuException e) {
//                response = e.toString();
//            }
//        } else if (command.matches(Regexes.EXIT_MENU.regex)) {
//            profileMenuController.exitMenu();
        } else if (command.matches(Regexes.SHOW_MENU.regex)) {
            response = getCurrentMenu();
        } else {
            response = Responses.INVALID_COMMAND.response;
        }
        return response;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url=getClass().getResource("/yugioh/fxml/ProfileMenu.fxml");
        Parent parent= FXMLLoader.load(url);
        Scene scene=new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }
}
