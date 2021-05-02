package view.gamephases;

import controller.gamephasescontrollers.MainPhase1Controller;
import exceptions.GameException;
import view.GameRegexes;
import view.ViewInterface;

import java.util.regex.Matcher;

public class MainPhase1 extends Duel {
    private static MainPhase1Controller mainPhase1Controller;


    @Override
    protected void execute() {
        mainPhase1Controller= gameController.getMainPhase1Controller();
        ViewInterface.showResult(mainPhase1Controller.showGameBoard(gameController.currentTurnPlayer,
                gameController.currentTurnOpponentPlayer));
        String response = processCommand(ViewInterface.getInput());
        ViewInterface.showResult(response);

    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        if (command.matches(GameRegexes.NEXT_PHASE.regex)) {
            gameController.changePhase();
            showPhase(gameController);
        } else if (command.matches(GameRegexes.SELECT.regex)) {
            response = processSelect(command);
        }
        else if(command.matches(GameRegexes.DESELECT.regex)) {
            try {
                gameController.deselect();
                response=GameResponses.CARD_DESELECTED.response;
            }
            catch (GameException e){
                response=e.toString();
            }
        }
        else if(command.matches(GameRegexes.SUMMON.regex)){
            try {
                mainPhase1Controller.summon();
                response=GameResponses.SUMMONED_SUCCESSFULLY.response;
            }
            catch (GameException e){
                response=e.toString();
            }
        }
        else if(command.matches(GameRegexes.SET.regex)){
            try {
                mainPhase1Controller.set();
                response=GameResponses.SET_SUCCESSFULLY.response;
            }
            catch (GameException e){
                response=e.toString();
            }
        }
        else if(command.matches(GameRegexes.SET_POSITION.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,GameRegexes.SET_POSITION.regex);
            try{
                mainPhase1Controller.setPosition(matcher.group(1));
                response=GameResponses.SET_POSITION_SUCCESSFULLY.response;
            }
            catch (GameException e){
                response=e.toString();
            }
        }
        else if(command.matches(GameRegexes.FLIP_SUMMON.regex)){
            try {
                mainPhase1Controller.flipSummon();
                response=GameResponses.FLIP_SUMMONED_SUCCESSFULLY.response;
            }
            catch (GameException e){
                response=e.toString();
            }
        }
        //todo these attack methods should be moved to battle phase
        else if(command.matches(GameRegexes.ATTACK.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,GameRegexes.ATTACK.regex);
            try{
                response=mainPhase1Controller.attack(Integer.parseInt(matcher.group(1)));
            }
            catch (GameException e){
                response=e.toString();
            }
        }
        else if(command.matches(GameRegexes.ATTACK_DIRECT.regex)){
            try {
                response=mainPhase1Controller.directAttack();
            }
            catch (GameException e){
                response=e.toString();
            }
        }
        else if(command.matches(GameRegexes.ACTIVATE_EFFECT.regex)){
            try {
                mainPhase1Controller.activateSpell();
                response=GameResponses.SPELL_ACTIVATED.response;
            }
            catch (GameException e){
                response=e.toString();
            }
        }
        //todo check whether we have handled all methods in this phase or not

        else{
            response=GameResponses.INVALID_COMMAND.response;
        }

        return response;
    }


}