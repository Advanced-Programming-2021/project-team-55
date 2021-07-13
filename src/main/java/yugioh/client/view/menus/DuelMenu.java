package yugioh.client.view.menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import yugioh.client.controller.AIPlayerController;
import yugioh.client.controller.menucontroller.DuelMenuController;
import yugioh.client.view.Regexes;
import yugioh.client.view.Responses;
import yugioh.client.view.ViewInterface;
import yugioh.client.view.gamephases.Duel;

import java.net.URL;
import java.util.regex.Matcher;

public class DuelMenu extends WelcomeMenu {

    private static final DuelMenuController duelMenuController = DuelMenuController.getInstance();

    @Override
    public void execute() throws Exception {
        AIPlayerController.setIsGameEnded(true);
        start(stage);
    }

    @Override
    public String processCommand(String command) {
        String response = "";
        if (command.matches(Regexes.DUEL_PLAYER.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.DUEL_PLAYER.regex);
            try {
                Duel.runGame(duelMenuController.newPVPDuel(matcher.group(2), Integer.parseInt(matcher.group(1))));
            } catch (Exception e) {
                response = e.toString();
            }
        } else if (command.matches(Regexes.DUEL_AI.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.DUEL_AI.regex);
            try {
                Duel.runGame(duelMenuController.newAIDuel(Integer.parseInt(matcher.group(1))));
            } catch (Exception e) {
                response = e.toString();
            }
//        } else if (command.matches(Regexes.ENTER_MENU.regex)) {
//            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.ENTER_MENU.regex);
//            try {
//                duelMenuController.enterMenu(matcher.group(1));
//            } catch (MenuException e) {
//                response = e.toString();
//            }
//        } else if (command.matches(Regexes.EXIT_MENU.regex)) {
//            duelMenuController.exitMenu();
        } else if (command.matches(Regexes.SHOW_MENU.regex)) {
            response = getCurrentMenu();
        } else {
            response = Responses.INVALID_COMMAND.response;
        }
        return response;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/DuelMenu.fxml");
        Parent parent = FXMLLoader.load(url);
        Scene scene = WelcomeMenu.createScene(parent);
        stage.setScene(scene);
        stage.show();
    }
}
