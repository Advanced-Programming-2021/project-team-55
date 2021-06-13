package yugioh.view.gamephases;

import yugioh.controller.AIPlayerController;
import yugioh.controller.CheatController;
import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.exceptions.GameException;
import yugioh.view.GameRegexes;
import yugioh.view.Menus.DetermineStarterMenu;
import yugioh.view.Menus.GameMenu;
import yugioh.view.ViewInterface;

import java.util.regex.Matcher;

abstract public class Duel {

    protected static final CheatController cheatController = CheatController.getInstance();
    private static final DrawPhase drawPhase = new DrawPhase();
    private static final MainPhase1 mainPhase1 = new MainPhase1();
    private static final BattlePhase battlePhase = new BattlePhase();
    private static final StandByPhase standByPhase = new StandByPhase();
    private static final MainPhase2 mainPhase2 = new MainPhase2();
    private static final EndPhase endPhase = new EndPhase();
    private static final Graveyard graveyard = new Graveyard();
    protected static GameController gameController;


    public static void runGame(GameController gameController) {
        Duel.gameController = gameController;
        assignTurn();
        try {
            new GameMenu().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        start(gameController);//todo
    }

    private static void start(GameController gameController) {
        showSideDeckCards();
        gameController.phases.add(gameController.currentPhase);
        showPhase();
        while (!gameController.isGameEnded()) {
            if (gameController.phases.get(gameController.phases.size() - 1) != gameController.currentPhase) {
                gameController.phases.add(gameController.currentPhase);
            }
            gameController.phases.add(gameController.currentPhase);
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
            // showPhase();
            AIPlayerController.recordGameLogs(gameController);
        }
        gameController.endGameRound();
    }

    public static void showPhase() {
        String response = "";
        if (gameController.currentPhase == GamePhase.DRAW) {
            response += "its " + gameController.getCurrentTurnPlayer().getUser().getNickname() + "'s turn\n";
        }
        response += "\nphase: " + gameController.currentPhase.name;
        ViewInterface.showResult(response);
    }

    public static String processSelect(String command) {
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
        try {
            if (gameController.getCurrentRound() == 1)
                new DetermineStarterMenu().execute();
            else {
                String playerName = gameController.getGame().getLosers().get(gameController.getGame().getLosers().size() - 1).getUser().getNickname();
                ViewInterface.showResult(playerName + " do you want to be the first player? yes/no");
                String input = ViewInterface.getInput();
                while (!input.equals("no") && !input.equals("yes")) {
                    ViewInterface.showResult("Error: invalid choice!");
                    input = ViewInterface.getInput();
                }
                switch (input) {
                    case "yes": {
                        gameController.setCurrentTurnPlayer(gameController.getGame().getLosers().get(gameController.getGame().getLosers().size() - 1));
                        gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getWinners().get(gameController.getGame().getWinners().size() - 1));
                        break;
                    }
                    case "no": {
                        gameController.setCurrentTurnPlayer(gameController.getGame().getWinners().get(gameController.getGame().getWinners().size() - 1));
                        gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getLosers().get(gameController.getGame().getLosers().size() - 1));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showSideDeckCards() {
        String response = "";
        response += gameController.getSideDeckCards(gameController.getCurrentTurnPlayer());
        response += gameController.getSideDeckCards(gameController.getCurrentTurnOpponentPlayer());
        ViewInterface.showResult(response);
    }

    public static MainPhase1 getMainPhase1() {
        return mainPhase1;
    }

    public static GameController getGameController() {
        return gameController;
    }

    abstract protected void execute();

    abstract protected String processCommand(String command);
}
