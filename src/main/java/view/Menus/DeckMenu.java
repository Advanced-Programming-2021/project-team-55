package view.Menus;

import controller.menucontroller.DeckMenuController;
import exceptions.MenuException;
import view.Regexes;
import view.Responses;
import view.ViewInterface;

import java.util.regex.Matcher;

public class DeckMenu extends Menu {
    private static final DeckMenuController deckMenuController=DeckMenuController.getInstance();

    @Override
    protected void execute() {
        String response=processCommand(ViewInterface.getInput());
        ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command) {
        String response="";
        if(command.matches(Regexes.CREATE_DECK.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,Regexes.CREATE_DECK.regex);
            try {
                deckMenuController.createDeck(matcher.group(1));
                response= Responses.DECK_CREATED_SUCCESSFULLY.response;
            }
            catch (MenuException e){
                response=e.toString();
            }
        }
        else if(command.matches(Regexes.DELETE_DECK.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,Regexes.DELETE_DECK.regex);
            try {
                deckMenuController.deleteDeck(matcher.group(1));
                response=Responses.DECK_DELETED_SUCCESSFULLY.response;
            }
            catch (MenuException e){
                response=e.toString();
            }
        }
        else if(command.matches(Regexes.SET_DECK_ACTIVE.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,Regexes.SET_DECK_ACTIVE.regex);
            try {
                deckMenuController.activeDeck(matcher.group(1));
                response=Responses.DECK_ACTIVATED_SUCCESSFULLY.response;
            }
            catch (MenuException e){
                response=e.toString();
            }

        }
        else if(command.matches(Regexes.ADD_CARD_TO_DECK.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,Regexes.ADD_CARD_TO_DECK.regex);
            try {
                deckMenuController.addCardToDeck(matcher.group(1),matcher.group(2),matcher.groupCount()==3);
                response=Responses.CARD_ADDED_TO_DECK_SUCCESSFULLY.response;
            }
            catch (MenuException e){
                response=e.toString();
            }
        }
        return response;
    }
}
