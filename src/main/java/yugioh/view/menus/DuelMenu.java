package yugioh.view.menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import yugioh.controller.AIPlayerController;
import yugioh.controller.menucontroller.DuelMenuController;
import yugioh.model.exceptions.MenuException;
import yugioh.view.Regexes;
import yugioh.view.Responses;
import yugioh.view.ViewInterface;
import yugioh.view.gamephases.Duel;

import java.net.URL;
import java.util.regex.Matcher;

public class DuelMenu extends WelcomeMenu {

    private static final DuelMenuController duelMenuController = DuelMenuController.getInstance();

    @Override
    public void execute() throws Exception {
        AIPlayerController.setIsGameEnded(true);   //todo: in chie? (parham)
        start(stage);
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        if (command.matches(Regexes.DUEL_PLAYER.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.DUEL_PLAYER.regex);
            try {
                Duel.runGame(duelMenuController.newPVPDuel(matcher.group(2), Integer.parseInt(matcher.group(1))));
            } catch (MenuException e) {
                response = e.toString();
            }
        } else if (command.matches(Regexes.DUEL_AI.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.DUEL_AI.regex);
            try {
                Duel.runGame(duelMenuController.newAIDuel(Integer.parseInt(matcher.group(1))));
            } catch (MenuException e) {
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
