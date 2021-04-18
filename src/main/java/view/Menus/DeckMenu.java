package view.Menus;

import controller.menucontroller.DeckMenuController;
import exceptions.MenuException;
import model.Deck;
import view.Regexes;
import view.Responses;
import view.ViewInterface;

import java.util.ArrayList;
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
        else if(command.matches(Regexes.REMOVE_CARD_FROM_CARD.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,Regexes.REMOVE_CARD_FROM_CARD.regex);
            try {
                deckMenuController.removeCardFromDeck(matcher.group(1),matcher.group(2),matcher.groupCount()==3);
                response=Responses.CARD_REMOVED_FROM_DECK_SUCCESSFULLY.response;
            }
            catch (MenuException e){
                response=e.toString();
            }
        }
        else if(command.matches(Regexes.SHOW_ALL_DECKS.regex)){
            showAllDecks(deckMenuController.getAllDecks());
        }
        return response;
    }

    private void showAllDecks(ArrayList<Deck> allDecks) {
        if(allDecks.size()>0) {
            System.out.printf("Decks:\nActive deck:\n" + allDecks.get(0) + "\nOther decks:\n");
            if (allDecks.size() > 1) {
                for (int i = 1; i < allDecks.size(); i++) {
                    System.out.println(allDecks.get(i));
                }
            }
        }
        else{
            System.out.printf("Decks:\nActive deck:\nOther decks:\n");
        }
    }
}
