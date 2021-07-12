package yugioh.server.view.Menus;

import yugioh.server.controller.DataBaseController;
import yugioh.server.model.UserHolder;
import yugioh.server.model.exceptions.MenuException;
import yugioh.server.view.Regexes;
import yugioh.server.view.Responses;
import yugioh.server.view.ViewInterface;

import java.util.regex.Matcher;

public class ImportExportMenu /*extends Menu*/ {
    private static final DataBaseController dataBaseController = DataBaseController.getInstance();

//    @Override
//    protected void execute() {
//        String response = processCommand(ViewInterface.getInput());
//        ViewInterface.showResult(response);
//}

//    @Override
//    protected String processCommand(String command, UserHolder currentUser) {
//        String response = "";
//        if (command.matches(Regexes.IMPORT_CARD.regex)) {
//            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.IMPORT_CARD.regex);
//            dataBaseController.importDeck(matcher.group(1));
//        } else if (command.matches(Regexes.EXPORT_CARD.regex)) {
//            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.EXPORT_CARD.regex);
//            response = dataBaseController.exportCard(matcher.group(1));
//        } else if (command.matches(Regexes.EXIT_MENU.regex)) {
//            dataBaseController.exitMenu();
//        } else if (command.matches(Regexes.SHOW_MENU.regex)) {
//            response = getCurrentMenu();
//        } else {
//            response = Responses.INVALID_COMMAND.response;
//        }
//        return response;
//    }
}

