package yugioh.client.view.gamephases;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import yugioh.client.controller.CheatController;
import yugioh.client.controller.gamephasescontrollers.GameController;
import yugioh.client.controller.menucontroller.GameMenuController;
import yugioh.client.model.exceptions.GameException;
import yugioh.client.view.GameRegexes;
import yugioh.client.view.ViewInterface;
import yugioh.client.view.menus.DetermineStarterMenu;
import yugioh.client.view.menus.GameMenu;
import yugioh.client.view.menus.Toast;
import yugioh.client.view.menus.WelcomeMenu;

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
        Duel.getGameController().disableActionsAndShowWaitingStage();
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
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3.5), event -> {
                Toast.makeText(WelcomeMenu.getStage(), "phase: " + gameController.currentPhase.name);
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


//                String playerName = gameController.getGame().getLosers().get(gameController.getGame().getLosers().size() - 1).getUser().getNickname();
//                ViewInterface.showResult(playerName + " do you want to be the first player? yes/no");
//                String input = ViewInterface.getInput();
//                while (!input.equals("no") && !input.equals("yes")) {
//                    ViewInterface.showResult("Error: invalid choice!");
//                    input = ViewInterface.getInput();
//                }
//                switch (input) {
//                    case "yes": {
//                        gameController.setCurrentTurnPlayer(gameController.getGame().getLosers().get(gameController.getGame().getLosers().size() - 1));
//                        gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getWinners().get(gameController.getGame().getWinners().size() - 1));
//                        break;
//                    }
//                    case "no": {
//                        gameController.setCurrentTurnPlayer(gameController.getGame().getWinners().get(gameController.getGame().getWinners().size() - 1));
//                        gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getLosers().get(gameController.getGame().getLosers().size() - 1));
//                        break;
//                    }
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showSideDeckCards() {
        String response = "";
        response += gameController.getSideDeckCards(gameController.getCurrentTurnPlayer());
        response += gameController.getSideDeckCards(gameController.getCurrentTurnOpponentPlayer());
//        ViewInterface.showResult(response);
    }

    public static GameController getGameController() {
        return gameController;
    }

    public static void handleCommand(String command) {
        if (command.contains("next phase")) {
            GameMenuController.getGameMenuController().nextPhase();
            return;
        }
        if (command.contains("surrender")){
            GameMenuController.getGameMenuController().surrender();
            return;
        }
        //todo commented by parham :)

//        String result = drawPhase.processCommand(command);
//        if (result.equals("Error: invalid command"))
//            result = standByPhase.processCommand(command);
//        if (result.equals("Error: invalid command"))
//            result = mainPhase1.processCommand(command);
//        if (result.equals("Error: invalid command"))
//            result = battlePhase.processCommand(command);
//        if (result.equals("Error: invalid command"))
//            result = mainPhase2.processCommand(command);
//        if (result.equals("Error: invalid command"))
//            result = endPhase.processCommand(command);
//        if (result.equals("Error: invalid command"))
//            result = graveyard.processCommand(command);
        String result="";
        if(gameController.currentPhase==GamePhase.DRAW){
            result=drawPhase.processCommand(command);
        }
        else if(gameController.currentPhase==GamePhase.STANDBY){
            result=standByPhase.processCommand(command);
        }
        else if(gameController.currentPhase==GamePhase.MAIN1){
            result=mainPhase1.processCommand(command);
        }
        else if(gameController.currentPhase==GamePhase.MAIN2){
            result=mainPhase2.processCommand(command);
        }
        else if(gameController.currentPhase==GamePhase.BATTLE){
            result=battlePhase.processCommand(command);
        }
        else if(gameController.currentPhase==GamePhase.END){
            result=endPhase.processCommand(command);
        }
        System.out.println("command "+command+" handled on other side with result: "+result);

    }

    abstract protected void execute();

    abstract protected String processCommand(String command);

}
