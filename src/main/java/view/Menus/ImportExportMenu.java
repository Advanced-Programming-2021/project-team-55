package view.Menus;

import controller.DataBaseController;
import model.exceptions.MenuException;
import view.Regexes;
import view.Responses;
import view.ViewInterface;

import java.util.regex.Matcher;

public class ImportExportMenu extends Menu {
    private static final DataBaseController dataBaseController = DataBaseController.getInstance();

    @Override
    protected void execute() {
        String response = processCommand(ViewInterface.getInput());
        ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        if (command.matches(Regexes.IMPORT_CARD.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.IMPORT_CARD.regex);
            response = dataBaseController.importCard(matcher.group(1));
        } else if (command.matches(Regexes.EXPORT_CARD.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.EXPORT_CARD.regex);
            response = dataBaseController.exportCard(matcher.group(1));
        } else if (command.matches(Regexes.ENTER_MENU.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.ENTER_MENU.regex);
            try {
                dataBaseController.enterMenu(matcher.group(1));
            } catch (MenuException e) {
                response = e.toString();
            }
        } else if (command.matches(Regexes.EXIT_MENU.regex)) {
            dataBaseController.exitMenu();
        } else if (command.matches(Regexes.SHOW_MENU.regex)) {
            response = getCurrentMenu();
        } else {
            response = Responses.INVALID_COMMAND.response;
        }
        return response;
    }
}
