package yugioh.view.menus;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import yugioh.controller.menucontroller.ScoreBoardMenuController;
import yugioh.model.User;
import yugioh.view.Regexes;
import yugioh.view.Responses;

import java.net.URL;
import java.util.ArrayList;
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
//            showScoreBoard(scoreBoardMenuController.getScoreBoard());
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

    private ArrayList<Label> showScoreBoard(LinkedHashMap<Integer, HashMap<Integer, String>> scoreBoard) {
        ArrayList<Label> scoreBoardInfo = new ArrayList<>();
        for (int i = 0; i < Math.min(scoreBoard.size(), 20); i++) {
            HashMap<Integer, String> userInfo = scoreBoard.get(i);
            for (int rank : userInfo.keySet()) {
                Label info = new Label(rank + userInfo.get(rank));
                info.setFont(Font.font(15.0));
                info.setAlignment(Pos.TOP_CENTER);
                if (userInfo.get(rank).equals(User.loggedInUser.toString())) {
                    info.setTextFill(Color.CYAN);
                } else {
                    info.setTextFill(Color.CHARTREUSE);
                }
                scoreBoardInfo.add(info);
            }
        }
        return scoreBoardInfo;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/ScoreBoardMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = WelcomeMenu.createScene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
