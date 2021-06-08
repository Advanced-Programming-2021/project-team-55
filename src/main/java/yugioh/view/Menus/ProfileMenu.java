package yugioh.view.Menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import yugioh.controller.menucontroller.ProfileMenuController;
import yugioh.model.User;
import yugioh.model.exceptions.MenuException;
import yugioh.view.Regexes;
import yugioh.view.Responses;
import yugioh.view.ViewInterface;

import java.net.URL;
import java.util.regex.Matcher;

public class ProfileMenu extends WelcomeMenu {
    private static final ProfileMenuController profileMenuController = ProfileMenuController.getInstance();


    @Override
    public void execute() throws Exception {
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
        URL url = getClass().getResource("/yugioh/fxml/ProfileMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        TextField  usernameField = (TextField) ((HBox) ((VBox) pane.getChildren().get(1)).getChildren().get(1)).getChildren().get(1);
        // Label usernameLabel = ((HBox)((VBox)pane.getChildren().get(0)).getChildren().get(2)).getChildren().get(1)
          usernameField.setText(User.loggedInUser.getUsername());
        TextField nicknameField = (TextField) ((HBox) ((VBox) pane.getChildren().get(1)).getChildren().get(2)).getChildren().get(1);
        nicknameField.setText(User.loggedInUser.getNickname());
        //ImageView profileImage = (ImageView) pane.getChildren().get(0);
        //profileImage.setImage(new Image(User.loggedInUser.getProfileImage()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
