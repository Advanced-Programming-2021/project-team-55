package yugioh.view.Menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import yugioh.controller.menucontroller.LoginMenuController;
import yugioh.model.exceptions.MenuException;
import yugioh.view.Regexes;
import yugioh.view.Responses;
import yugioh.view.ViewInterface;

import java.net.URL;
import java.util.regex.Matcher;

public class LoginMenu extends WelcomeMenu {
    private static final LoginMenuController loginMenuController = LoginMenuController.getInstance();

    @Override
    public void execute() throws Exception {
        //String response = processCommand(ViewInterface.getInput());
        // ViewInterface.showResult(response);
//       try {
//           start(new Stage());
//       }catch (Exception ignored){}
        start(stage);
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        if (command.matches(Regexes.LOGIN_USER.regex)) {
            try {
                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.LOGIN_USER.regex);
                loginMenuController.loginUser(matcher.group(2), matcher.group(1));
                response = Responses.LOGIN_SUCCESSFULLY.response;
            } catch (MenuException e) {
                response = e.toString();
            }
//        } else if (command.matches(Regexes.CREATE_USER.regex)) {
//            try {
//                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.CREATE_USER.regex);
//                loginMenuController.createUser(matcher.group(3), matcher.group(2), matcher.group(1));
//                response = Responses.CREATE_SUCCESSFULLY.response;
//            } catch (MenuException e) {
//                response = e.toString();
//            }
        } else if (command.matches(Regexes.ENTER_MENU.regex)) {
//            try {
//                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.ENTER_MENU.regex);
//                loginMenuController.enterMenu(matcher.group(1));
//            } catch (MenuException e) {
//                response = e.toString();
//            }
        } else if (command.matches(Regexes.EXIT_MENU.regex)) {
            //   loginMenuController.exitMenu();
        } else if (command.matches(Regexes.SHOW_MENU.regex)) {
            response = getCurrentMenu();
        } else {
            response = Responses.INVALID_COMMAND.response;
        }
        return response;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/LoginMenu.fxml");
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
