package view.gamephases;

import controller.gamephasescontrollers.MainPhase1Controller;
import exceptions.GameException;
import view.Responses;
import view.ViewInterface;

import java.util.regex.Matcher;

public class MainPhase1 extends Duel {
    private static MainPhase1Controller mainPhase1Controller;


    @Override
    protected void execute() {
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
        //...

        return response;
    }

    private String processSelect(String command) {
        String response = "";
        if (command.matches(GameRegexes.SELECT_MONSTER.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, GameRegexes.SELECT_MONSTER.regex);
            try {
                mainPhase1Controller.selectCard("Monster", Integer.parseInt(matcher.group(1)),matcher.group(3)!=null);
                response = GameResponses.CARD_SELECTED.response;
            } catch (GameException e) {
                response = e.toString();
            }
        }
        else if(command.matches(GameRegexes.SELECT_SPELL.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,GameRegexes.SELECT_SPELL.regex);
            try {
                mainPhase1Controller.selectCard("Spell",Integer.parseInt(matcher.group(1)),false);
                response=GameResponses.CARD_SELECTED.response;
            }
            catch (GameException e){
                response=e.toString();
            }
        }
        else if(command.matches(GameRegexes.SELECT_OPPONENT_SPELL.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,GameRegexes.SELECT_OPPONENT_SPELL.regex);
            try {
                mainPhase1Controller.selectCard("Spell",Integer.parseInt(matcher.group(1)),true);
                response=GameResponses.CARD_SELECTED.response;
            }
            catch (GameException e){
                response=e.toString();
            }
        }
        else if(command.matches(GameRegexes.SELECT_FIELDZONE.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,GameRegexes.SELECT_FIELDZONE.regex);
            try {
                mainPhase1Controller.selectCard("Field",0,matcher.group(1)!=null);
                response=GameResponses.CARD_SELECTED.response;
            }
            catch (GameException e){
                response=e.toString();
            }
        }
        else if(command.matches(GameRegexes.SELECT_HAND.regex)){
            Matcher matcher=ViewInterface.getCommandMatcher(command,GameRegexes.SELECT_HAND.regex);
            try {
                mainPhase1Controller.selectCard("Hand",Integer.parseInt(matcher.group(1)),false);
                response=GameResponses.CARD_SELECTED.response;
            }
            catch (GameException e){
                response=e.toString();
            }
        }
        else{
            response=GameResponses.INVALID_COMMAND.response;
        }
        return response;
    }
}