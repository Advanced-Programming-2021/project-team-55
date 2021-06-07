package yugioh.view.Menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import yugioh.controller.DataBaseController;
import yugioh.view.Regexes;
import yugioh.view.Responses;
import yugioh.view.ViewInterface;

import java.net.URL;
import java.util.regex.Matcher;

public class ImportExportMenu extends WelcomeMenu {
    private static final DataBaseController dataBaseController = DataBaseController.getInstance();

    @Override
    public void execute() throws Exception {
        start(stage);
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        if (command.matches(Regexes.IMPORT_CARD.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.IMPORT_CARD.regex);
            dataBaseController.importDeck(matcher.group(1));
        } else if (command.matches(Regexes.EXPORT_CARD.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.EXPORT_CARD.regex);
            response = dataBaseController.exportCard(matcher.group(1));
//        } else if (command.matches(Regexes.ENTER_MENU.regex)) {
//            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.ENTER_MENU.regex);
//            try {
//                dataBaseController.enterMenu(matcher.group(1));
//            } catch (MenuException e) {
//                response = e.toString();
//            }
//        } else if (command.matches(Regexes.EXIT_MENU.regex)) {
//            dataBaseController.exitMenu();
        } else if (command.matches(Regexes.SHOW_MENU.regex)) {
            response = getCurrentMenu();
        } else {
            response = Responses.INVALID_COMMAND.response;
        }
        return response;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/ImportExportMenu.fxml");
        Parent parent = FXMLLoader.load(url);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }
}
