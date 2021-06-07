package yugioh.view.Menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import yugioh.controller.menucontroller.ScoreBoardMenuController;
import yugioh.view.Regexes;
import yugioh.view.Responses;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ScoreBoardMenu extends WelcomeMenu {
    private static final ScoreBoardMenuController scoreBoardMenuController = ScoreBoardMenuController.getInstance();


    @Override
    public void execute() throws Exception {
        start(stage);
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        if (command.matches(Regexes.SHOW_SCOREBOARD.regex)) {
            showScoreBoard(scoreBoardMenuController.getScoreBoard());
//        } else if (command.matches(Regexes.ENTER_MENU.regex)) {
//            try {
//                Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.ENTER_MENU.regex);
//                scoreBoardMenuController.enterMenu(matcher.group(1));
//            } catch (MenuException e) {
//                response = e.toString();
//            }
//        } else if (command.matches(Regexes.EXIT_MENU.regex)) {
//            scoreBoardMenuController.exitMenu();
        } else if (command.matches(Regexes.SHOW_MENU.regex)) {
            response = getCurrentMenu();
        } else {
            response = Responses.INVALID_COMMAND.response;
        }
        return response;
    }

    private void showScoreBoard(LinkedHashMap<Integer, HashMap<Integer, String>> scoreBoard) {
        for (int i = 0; i < scoreBoard.size(); i++) {
            HashMap<Integer, String> userInfo = scoreBoard.get(i);
            for (int rank : userInfo.keySet()) {
                System.out.println(rank + userInfo.get(rank));
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/ScoreBoardMenu.fxml");
        Parent parent = FXMLLoader.load(url);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }
}
