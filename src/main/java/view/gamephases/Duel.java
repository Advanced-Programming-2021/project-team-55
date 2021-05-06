package view.gamephases;

import controller.CheatController;
import controller.gamephasescontrollers.GameController;
import exceptions.GameException;
import view.GameRegexes;
import view.ViewInterface;

import java.util.regex.Matcher;

abstract public class Duel {

    private static final DrawPhase drawPhase = new DrawPhase();
    private static final MainPhase1 mainPhase1 = new MainPhase1();
    private static final BattlePhase battlePhase = new BattlePhase();
    private static final StandByPhase standByPhase = new StandByPhase();
    private static final MainPhase2 mainPhase2 = new MainPhase2();
    private static final EndPhase endPhase = new EndPhase();
    private static final Graveyard graveyard = new Graveyard();

    protected static GameController gameController;
    protected static final CheatController cheatController = CheatController.getInstance();

    public static void runGame(GameController gameController) {
        Duel.gameController = gameController;
        assignTurn();
        showSideDeckCards();
        gameController.phases.add(gameController.currentPhase);
        showPhase();
        while (!gameController.isGameEnded()) {
            if (gameController.phases.get(gameController.phases.size() - 1) != gameController.currentPhase) {
                gameController.phases.add(gameController.currentPhase);
            }
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
                case GRAVEYARD: {
                    graveyard.execute();
                    break;
                }
            }
        }
        gameController.endGameRound();
    }

    abstract protected void execute();

    abstract protected String processCommand(String command);

    protected static void showPhase() {
        String response = "";
        if (gameController.currentPhase == GamePhase.DRAW) {
            response += "its " + gameController.getCurrentTurnPlayer().getUser().getNickname() + "'s turn\n";
        }
        response += "\nphase: " + gameController.getCurrentPhase().name;
        ViewInterface.showResult(response);
    }

    public String processSelect(String command) {
        String response = "";
        if (command.matches(GameRegexes.SELECT_MONSTER.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, GameRegexes.SELECT_MONSTER.regex);
            try {
                gameController.selectCard("monster", Integer.parseInt(matcher.group(1)),
                        matcher.group(2) != null);
                response = GameResponses.CARD_SELECTED.response;
            } catch (GameException e) {
                response = e.toString();
            }
        } else if (command.matches(GameRegexes.SELECT_SPELL.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, GameRegexes.SELECT_SPELL.regex);
            try {
                gameController.selectCard("spell", Integer.parseInt(matcher.group(1)), false);
                response = GameResponses.CARD_SELECTED.response;
            } catch (GameException e) {
                response = e.toString();
            }
        } else if (command.matches(GameRegexes.SELECT_OPPONENT_SPELL.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, GameRegexes.SELECT_OPPONENT_SPELL.regex);
            try {
                gameController.selectCard("spell", Integer.parseInt(matcher.group(1)),
                        true);
                response = GameResponses.CARD_SELECTED.response;
            } catch (GameException e) {
                response = e.toString();
            }
        } else if (command.matches(GameRegexes.SELECT_FIELDZONE.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, GameRegexes.SELECT_FIELDZONE.regex);
            try {
                gameController.selectCard("field", 0,
                        matcher.group(1) != null);
                response = GameResponses.CARD_SELECTED.response;
            } catch (GameException e) {
                response = e.toString();
            }
        } else if (command.matches(GameRegexes.SELECT_HAND.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, GameRegexes.SELECT_HAND.regex);
            try {
                gameController.selectCard("hand", Integer.parseInt(matcher.group(1)),
                        false);
                response = GameResponses.CARD_SELECTED.response;
            } catch (GameException e) {
                response = e.toString();
            }
        } else {
            response = GameResponses.INVALID_SELECTION.response;
        }
        return response;
    }

    private static void assignTurn() {
        String currentPlayerName = gameController.getGame().getFirstPlayer().getUser().getNickname();
        String opponentPlayerName = gameController.getGame().getSecondPlayer().getUser().getNickname();
        if (gameController.getCurrentRound() == 1) {
            String request = currentPlayerName + " choose a side: 1-head 2-tale";
            ViewInterface.showResult(request);
            String choice=ViewInterface.getInput();
            while(!choice.equals("1")&&!choice.equals("2")){
                ViewInterface.showResult("Error: invalid choice!");
                choice=ViewInterface.getInput();
            }
            if (Integer.parseInt(choice) == gameController.tossCoin()) {
                ViewInterface.showResult(currentPlayerName + " do you want to be the first player? yes/no");
                String input=ViewInterface.getInput();
                while(!input.equals("no")&&!input.equals("yes")){
                    ViewInterface.showResult("Error: invalid choice!");
                    input=ViewInterface.getInput();
                }
                switch (input){
                    case "yes":{
                        gameController.setCurrentTurnPlayer(gameController.getGame().getFirstPlayer());
                        gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getSecondPlayer());
                        break;
                    }
                    case "no":{
                        gameController.setCurrentTurnPlayer(gameController.getGame().getSecondPlayer());
                        gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getFirstPlayer());
                        break;
                    }
                }
            } else {
                ViewInterface.showResult(opponentPlayerName + " do you want to be the first player? yes/no");
                String input=ViewInterface.getInput();
                while(!input.equals("no")&&!input.equals("yes")){
                    ViewInterface.showResult("Error: invalid choice!");
                    input=ViewInterface.getInput();
                }
                switch (input){
                    case "yes":{
                        gameController.setCurrentTurnPlayer(gameController.getGame().getSecondPlayer());
                        gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getFirstPlayer());
                        break;
                    }
                    case "no":{
                        gameController.setCurrentTurnPlayer(gameController.getGame().getFirstPlayer());
                        gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getSecondPlayer());
                        break;
                    }
                }
            }

        } else {
            String playerName = gameController.getGame().getLosers().get(gameController.getGame().getLosers().size() - 1).getUser().getNickname();
            ViewInterface.showResult(playerName + " do you want to be the first player? yes/no");
            String input=ViewInterface.getInput();
            while(!input.equals("no")&&!input.equals("yes")){
                ViewInterface.showResult("Error: invalid choice!");
                input=ViewInterface.getInput();
            }
            switch (input){
                case "yes":{
                    gameController.setCurrentTurnPlayer(gameController.getGame().getLosers().get(gameController.getGame().getLosers().size() - 1));
                    gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getWinners().get(gameController.getGame().getWinners().size() - 1));
                    break;
                }
                case "no":{
                    gameController.setCurrentTurnPlayer(gameController.getGame().getWinners().get(gameController.getGame().getWinners().size() - 1));
                    gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getLosers().get(gameController.getGame().getLosers().size() - 1));
                    break;
                }
            }
        }

    }

    private static void showSideDeckCards(){
        String response="";
        response+=gameController.getSideDeckCards(gameController.getCurrentTurnPlayer());
        response+=gameController.getSideDeckCards(gameController.getCurrentTurnOpponentPlayer());
        ViewInterface.showResult(response);
    }

    public static MainPhase1 getMainPhase1() {
        return mainPhase1;
    }
}
