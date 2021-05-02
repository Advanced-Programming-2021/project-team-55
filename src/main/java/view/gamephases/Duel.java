package view.gamephases;

import controller.gamephasescontrollers.GameController;
import exceptions.GameException;
import view.GameRegexes;
import view.ViewInterface;

import java.util.regex.Matcher;

abstract public class Duel {

    private static DrawPhase drawPhase = new DrawPhase();
    private static MainPhase1 mainPhase1 = new MainPhase1();
    private static BattlePhase battlePhase = new BattlePhase();
    private static StandByPhase standByPhase = new StandByPhase();
    private static MainPhase2 mainPhase2 = new MainPhase2();
    private static EndPhase endPhase = new EndPhase();
    public static GameController gameController;

    public static void runGame(GameController gameController) {
        Duel.gameController=gameController;
        showPhase(gameController);
        while (true) {
            switch (gameController.getCurrentPhase()) {
                case DRAW: {
                    drawPhase.execute();
                    break;
                }
                case STANDBY: {
                    standByPhase.execute();
                    break;
                }
                case MAIN1: {
                    mainPhase1.execute();
                    break;
                }
                case MAIN2: {
                    mainPhase2.execute();
                    break;
                }
                case BATTLE: {
                    battlePhase.execute();
                    break;
                }
                case END: {
                    endPhase.execute();
                    break;
                }
            }
        }
    }

    abstract protected void execute();
    abstract protected String  processCommand(String command);
    protected static void showPhase(GameController gameController){
        ViewInterface.showResult("\nphase: "+gameController.getCurrentPhase().name);
    }
    protected String processSelect(String command) {
        String response = "";
        if (command.matches(GameRegexes.SELECT_MONSTER.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, GameRegexes.SELECT_MONSTER.regex);
            try {
                gameController.selectCard("Monster", Integer.parseInt(matcher.group(1)),
                        matcher.group(2)!=null );
                response = GameResponses.CARD_SELECTED.response;
            } catch (GameException e) {
                response = e.toString();
            }
        }
        else if(command.matches(GameRegexes.SELECT_SPELL.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,GameRegexes.SELECT_SPELL.regex);
            try {
                gameController.selectCard("Spell",Integer.parseInt(matcher.group(1)),false);
                response=GameResponses.CARD_SELECTED.response;
            }
            catch (GameException e){
                response=e.toString();
            }
        }
        else if(command.matches(GameRegexes.SELECT_OPPONENT_SPELL.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,GameRegexes.SELECT_OPPONENT_SPELL.regex);
            try {
                gameController.selectCard("Spell",Integer.parseInt(matcher.group(1)),
                        true);
                response=GameResponses.CARD_SELECTED.response;
            }
            catch (GameException e){
                response=e.toString();
            }
        }
        else if(command.matches(GameRegexes.SELECT_FIELDZONE.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,GameRegexes.SELECT_FIELDZONE.regex);
            try {
                gameController.selectCard("Field",0,
                        matcher.group(1)!=null);
                response=GameResponses.CARD_SELECTED.response;
            }
            catch (GameException e){
                response=e.toString();
            }
        }
        else if(command.matches(GameRegexes.SELECT_HAND.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,GameRegexes.SELECT_HAND.regex);
            try {
                gameController.selectCard("Hand",Integer.parseInt(matcher.group(1)),
                        false);
                response=GameResponses.CARD_SELECTED.response;
            }
            catch (GameException e){
                response=e.toString();
            }
        }
        else{
            response=GameResponses.INVALID_SELECTION.response;
        }
        return response;
    }
}
