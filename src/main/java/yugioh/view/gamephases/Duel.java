package yugioh.view.gamephases;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import yugioh.controller.CheatController;
import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.controller.menucontroller.GameMenuController;
import yugioh.model.exceptions.GameException;
import yugioh.view.GameRegexes;
import yugioh.view.ViewInterface;
import yugioh.view.menus.DetermineStarterMenu;
import yugioh.view.menus.GameMenu;
import yugioh.view.menus.Toast;
import yugioh.view.menus.WelcomeMenu;

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
            GameMenu gameMenu = new GameMenu();
            gameMenu.execute();
            gameController.currentTurnPlayer.getGameBoard().setGamePane(gameMenu.getPane(), false);
            gameController.currentTurnOpponentPlayer.getGameBoard().setGamePane(gameMenu.getPane(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        startGame(gameController);
    }

    private static void startGame(GameController gameController) {
        showSideDeckCards();
        gameController.phases.add(gameController.currentPhase);
        showPhase();
        gameController.currentTurnPlayer.getGameBoard().addCardsToHandDeck(5, true);
        gameController.currentTurnOpponentPlayer.getGameBoard().addCardsToHandDeck(5, false);
        Duel.getGameController().getMainPhase1Controller().showGameBoard(gameController.currentTurnPlayer,
                gameController.currentTurnOpponentPlayer);
//        new Thread(() -> {
//        while (!gameController.isGameEnded()) {
//            if (gameController.phases.get(gameController.phases.size() - 1) != gameController.currentPhase) {
//                gameController.phases.add(gameController.currentPhase);
//            }
//            gameController.phases.add(gameController.currentPhase);
//            switch (gameController.getCurrentPhase()) {
//                case DRAW: {
        drawPhase.execute();
//                    break;
//                }
//                case STANDBY: {
//                    standByPhase.execute();
//                    break;
//                }
//                case MAIN1: {
//        mainPhase1.execute();
//                    break;
//                }
//                case MAIN2: {
//                    mainPhase2.execute();
//                    break;
//                }
//                case BATTLE: {
//                    battlePhase.execute();
//                    break;
//                }
//                case END: {
//                    endPhase.execute();
//                    break;
//                }
//                case GRAVEYARD: {
//                    graveyard.execute();
//                    break;
//                }
//            }
//            // showPhase();
//            AIPlayerController.recordGameLogs(gameController);
//        }
//            }
//        gameController.endGameRound();
//        }).start();
    }

    public static void showPhase() {
        GameMenuController.getGameMenuController().focusOpacityOnPhase(gameController.currentPhase);
        if (gameController.currentPhase == GamePhase.DRAW) {
            GameMenuController.getGameMenuController().getEpLabel().setEffect(null);
            GameMenuController.getGameMenuController().getEpLabel().setOpacity(0.5);
            Toast.makeText(WelcomeMenu.getStage(), "its " + gameController.getCurrentTurnPlayer().getUser().getNickname() + "'s turn");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
                Toast.makeText(WelcomeMenu.getStage(), "phase: " + gameController.currentPhase.name);//todo make duration 3.5
            }));
            timeline.play();
            return;
        }
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3.5), event -> {
        Toast.makeText(WelcomeMenu.getStage(), "phase: " + gameController.currentPhase.name);
//        }));
//        timeline.play();
    }

    public static void executePhase() {
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
            case BATTLE: {
                battlePhase.execute();
                break;
            }
            case MAIN2: {
                mainPhase2.execute();
                break;
            }
            case END: {
                endPhase.execute();
                break;
            }
        }
    }

    public static String processSelect(String command) {
        String response;
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

    public static GameController getGameController() {
        return gameController;
    }

    abstract protected void execute();

    abstract protected String processCommand(String command);

}
