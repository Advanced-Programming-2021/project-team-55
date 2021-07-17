package yugioh.client.controller.gamephasescontrollers;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import yugioh.client.controller.menucontroller.DetermineStarterMenuController;
import yugioh.client.controller.menucontroller.GameMenuController;
import yugioh.client.model.CoinDice;
import yugioh.client.model.Player;
import yugioh.client.model.User;
import yugioh.client.model.board.CardStatus;
import yugioh.client.model.board.Cell;
import yugioh.client.model.board.Game;
import yugioh.client.model.board.GameBoard;
import yugioh.client.model.cards.Card;
import yugioh.client.model.cards.Deck;
import yugioh.client.model.exceptions.GameException;
import yugioh.client.view.GameRegexes;
import yugioh.client.view.NetAdapter;
import yugioh.client.view.ViewInterface;
import yugioh.client.view.gamephases.CardActionsMenu;
import yugioh.client.view.gamephases.Duel;
import yugioh.client.view.gamephases.GamePhase;
import yugioh.client.view.gamephases.GameResponses;
import yugioh.client.view.menus.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class GameController {

    public Player currentTurnPlayer;
    public Player currentTurnOpponentPlayer;
    public GamePhase currentPhase;
    public ArrayList<Cell> changedPositionCells;
    public ArrayList<GamePhase> phases;
    public ArrayList<Cell> attackerCellsThisTurn;
    public boolean shouldRitualSummonNow;
    public boolean shouldSpecialSummonNow;
    public int turnCount;
    protected Game game;
    Cell lastSummonedMonster;
    private int currentRound = 1;
    private boolean didPlayerSetOrSummonThisTurn;
    private boolean isGameEnded;
    private DrawPhaseController drawPhaseController;
    private StandByPhaseController standByPhaseController;
    private MainPhase1Controller mainPhase1Controller;
    private BattlePhaseController battlePhaseController;
    private MainPhase2Controller mainPhase2Controller;
    private EndPhaseController endPhaseController;
    private DetermineStarterMenu determineStarterMenu;
    private GameMenuController gameMenuController;

    private static Stage logoStage;

    public GameController(Game game) {
        this.game = game;
        gameControllerInitialization();
    }

    public GameController() {
    }

    public GameMenuController getGameMenuController() {
        return gameMenuController;
    }

    public void setGameMenuController(GameMenuController gameMenuController) {
        this.gameMenuController = gameMenuController;
    }

    public Cell getLastSummonedMonster() {
        return lastSummonedMonster;
    }

    public void setLastSummonedMonster(Cell lastSummonedMonster) {
        this.lastSummonedMonster = lastSummonedMonster;
    }

    private void gameControllerInitialization() {
        currentPhase = GamePhase.DRAW;
        changedPositionCells = new ArrayList<>();
        attackerCellsThisTurn = new ArrayList<>();
        phases = new ArrayList<>();
        didPlayerSetOrSummonThisTurn = false;
        isGameEnded = false;
        shouldRitualSummonNow = false;
        shouldSpecialSummonNow = false;
        turnCount = 1;
        drawPhaseController = new DrawPhaseController(this);
        standByPhaseController = new StandByPhaseController(this);
        mainPhase1Controller = MainPhase1Controller.getInstance();
        battlePhaseController = new BattlePhaseController(this);
        mainPhase2Controller = MainPhase2Controller.getInstance();
        endPhaseController = EndPhaseController.getInstance(this);
        DetermineStarterMenuController.setGameController(this);
    }

    public int tossCoin() {
        return CoinDice.tossCoin();
    }

    public ArrayList<Cell> getChangedPositionCells() {
        return changedPositionCells;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public String showGraveyard() {
        String response = "";
        GameBoard playerGameBoard = currentTurnPlayer.getGameBoard();
        if (playerGameBoard.getGraveyard().size() == 0) {
            response = GameResponses.GRAVEYARD_EMPTY.response;
        } else {
            for (int i = 1; i <= playerGameBoard.getGraveyard().size(); i++) {
                response += i + ". " + playerGameBoard.getGraveyard().get(i - 1).getCellCard();
            }
        }
        return response;
    }

    public String showCard() throws GameException {
        NetAdapter.sendForwardRequestForGame("card show --selected");//todo complete function

        if (Cell.getSelectedCell() == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        } else if (!currentTurnOpponentPlayer.getGameBoard().isCellVisibleToOpponent(Cell.getSelectedCell())) {
            throw new GameException(GameResponses.CARD_IS_NOT_VISIBLE.response);
        } else {
            return Cell.getSelectedCell().getCellCard().toString();
        }
    }

    public void selectCard(String zone, int number, boolean opponent) throws GameException {
       // ViewInterface.showResult("select --" + zone+ number+ );//todo complete function
        GameBoard currentPlayerGameBoard = currentTurnPlayer.getGameBoard();
        GameBoard opponentPlayerGameBoard = currentTurnOpponentPlayer.getGameBoard();
        Cell selectedCell = null;
        number -= 1;
        switch (zone) {
            case "monster": {
                if (number > 4) {
                    throw new GameException(GameResponses.INVALID_SELECTION.response);
                } else {
                    if (opponent) {
                        selectedCell = opponentPlayerGameBoard.getMonsterCardZone()[number];
                    } else {
                        selectedCell = currentPlayerGameBoard.getMonsterCardZone()[number];
                    }
                }
                break;
            }
            case "spell": {
                if (number > 4) {
                    throw new GameException(GameResponses.INVALID_SELECTION.response);
                }
                if (opponent) {
                    selectedCell = opponentPlayerGameBoard.getSpellAndTrapCardZone()[number];
                } else {
                    selectedCell = currentPlayerGameBoard.getSpellAndTrapCardZone()[number];
                }
                break;
            }
            case "field": {
                if (opponent) {
                    selectedCell = opponentPlayerGameBoard.getFieldZone();
                } else {
                    selectedCell = currentPlayerGameBoard.getFieldZone();
                }
                break;
            }
            case "hand": {
                if (number >= currentPlayerGameBoard.getHandCards().size()) {
                    throw new GameException(GameResponses.INVALID_SELECTION.response);

                }
                selectedCell = currentPlayerGameBoard.getHandCards().get(number);
                break;
            }
        }
        if (selectedCell.getCellCard() == null) {
            throw new GameException(GameResponses.NO_CARDS_FOUND.response);
        } else {
            Cell.setSelectedCell(selectedCell);
        }
    }

    public void deselect() throws GameException {
        if (Cell.getSelectedCell() == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        }
        Cell.deselectCell();
    }

    public void changePhase() {


//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3.5), event -> {
        CardActionsMenu.close();
        //   if (isGameEnded()) {
//        endGameRound();
//            return;
//        }
        switch (currentPhase) {//todo debug phases not sync
            case DRAW: {
                currentPhase = GamePhase.STANDBY;
                break;
            }
            case STANDBY: {
                currentPhase = GamePhase.MAIN1;
                break;
            }
            case MAIN1: {
                NetAdapter.sendForwardRequestForGame("next phase");
                currentPhase = GamePhase.BATTLE;
                break;
            }
            case BATTLE: {
                NetAdapter.sendForwardRequestForGame("next phase");
                CardActionsMenu.removeSword();
                currentPhase = GamePhase.MAIN2;
                break;
            }
            case MAIN2: {
                NetAdapter.sendForwardRequestForGame("next phase");
                currentPhase = GamePhase.END;
                break;
            }
            case END: {
                currentPhase = GamePhase.DRAW;
                changeTurn(false, false);
                break;
            }
        }
        Duel.showPhase();
//        }));
//        timeline.play();

        Duel.executePhase();
    }

    public void changeTurn(boolean isTemporary, boolean backToPlayer) {
        if (isTemporary) {
            Platform.runLater(() -> {
                if (isTemporary && !backToPlayer) {
                    ViewInterface.showResult("now it will be " + currentTurnOpponentPlayer.getUser().getNickname() + "’s turn");
                    ViewInterface.showResult(mainPhase1Controller.showGameBoard(currentTurnOpponentPlayer, currentTurnPlayer));
                    Toast.makeText(WelcomeMenu.getStage(), "now it will be " + currentTurnOpponentPlayer.getUser().getNickname() + "’s turn");

                    gameMenuController.changeGameBoard();
                    gameMenuController.nextPhaseTriangle.setDisable(true);
                    Player player = currentTurnPlayer;
                    currentTurnPlayer = currentTurnOpponentPlayer;
                    currentTurnOpponentPlayer = player;
                    gameMenuController.updateGameStatusUIs();
                    CardActionsMenu.close();
                    return;
                }
                gameMenuController.changeGameBoard();
                gameMenuController.nextPhaseTriangle.setDisable(false);
                Player player = currentTurnPlayer;
                currentTurnPlayer = currentTurnOpponentPlayer;
                currentTurnOpponentPlayer = player;
                gameMenuController.updateGameStatusUIs();
                CardActionsMenu.close();
                didPlayerSetOrSummonThisTurn = false;
                changedPositionCells = new ArrayList<>();
                attackerCellsThisTurn = new ArrayList<>();
                turnCount++;

                mainPhase1Controller.showGameBoard(currentTurnPlayer,
                        currentTurnOpponentPlayer);
            });
        } else {
            gameMenuController.changeGameBoard();
            Player player = currentTurnPlayer;
            currentTurnPlayer = currentTurnOpponentPlayer;
            currentTurnOpponentPlayer = player;
            gameMenuController.updateGameStatusUIs();
            CardActionsMenu.close();
            didPlayerSetOrSummonThisTurn = false;
            changedPositionCells = new ArrayList<>();
            attackerCellsThisTurn = new ArrayList<>();
            turnCount++;

            mainPhase1Controller.showGameBoard(currentTurnPlayer,
                    currentTurnOpponentPlayer);
            //});

            disableActionsAndShowWaitingStage();
        }
    }

    public void disableActionsAndShowWaitingStage() {
        if (!currentTurnPlayer.getUser().equals(User.getLoggedInUser())) {
            GameMenuController.getGameMenuController().gamePane.setDisable(true);
            logoStage = new Stage();
            logoStage.initOwner(WelcomeMenu.stage);
            logoStage.initStyle(StageStyle.TRANSPARENT);
            URL url = getClass().getResource("/yugioh/fxml/Logo.fxml");
            Pane pane = null;
            try {
                pane = FXMLLoader.load(url);
            } catch (IOException ignored) {
            }
            Scene scene = WelcomeMenu.createScene(pane);
            scene.setFill(Color.TRANSPARENT);
            logoStage.setScene(scene);
            logoStage.setX(158);
            logoStage.setY(217);
            logoStage.show();
            for(Cell cell:currentTurnPlayer.getGameBoard().getAllCellsInBoard()){
                if(!cell.isEmpty()&&(cell.cardStatus== CardStatus.HIDDEN||cell.cardStatus==CardStatus.DEFENSIVE_HIDDEN)){
                    cell.getCellRectangle().setFill(cell.getCellCard().getCardBackImagePattern());
                }
            }
            for(Cell cell:currentTurnOpponentPlayer.getGameBoard().getAllCellsInBoard()){
                if(!cell.isEmpty()&&(cell.cardStatus==CardStatus.OCCUPIED||cell.cardStatus==CardStatus.DEFENSIVE_OCCUPIED||
                        cell.cardStatus==CardStatus.OFFENSIVE_OCCUPIED||currentTurnOpponentPlayer.getGameBoard().isCellInHandZone(cell))){
                    cell.getCellRectangle().setFill(cell.getCellCard().getCardImagePattern());
                }
            }
        } else {
            closeWaitingStage();
        }
    }

    private void closeWaitingStage() {
        GameMenuController.getGameMenuController().gamePane.setDisable(false);
        try {
            logoStage.close();
            logoStage = null;
        } catch (Exception ignored) {
        }
    }

    public void activateTrapEffect(ArrayList<String> trapsCanBeActivated) {
        Stage activateStage = new Stage();
        activateStage.initOwner(WelcomeMenu.stage);
        activateStage.initStyle(StageStyle.UNDECORATED);
        activateStage.initModality(Modality.NONE);
        URL url = getClass().getResource("/yugioh/fxml/ActivateEffectMenu.fxml");
        try {
            Pane pane = FXMLLoader.load(url);
            Scene scene = WelcomeMenu.createScene(pane);
            activateStage.setScene(scene);
            Button yesButton = (Button) ((HBox) ((VBox) pane.getChildren().get(0)).getChildren().get(1)).getChildren().get(0);
            Button noButton = (Button) ((HBox) ((VBox) pane.getChildren().get(0)).getChildren().get(1)).getChildren().get(1);
            yesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    activateStage.close();
                    gameMenuController.choiceHasBeenMade = true;
                    gameMenuController.shouldActivateEffectsNow = true;
                    gameMenuController.canBeActivatedCards = trapsCanBeActivated;
                }
            });
            noButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    gameMenuController.choiceHasBeenMade = true;
                    activateStage.close();
                }
            });
            activateStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
         /*   while (true) {
                ViewInterface.showResult("do you want to activate your trap or spell? yes/no");
                String response = ViewInterface.getInput();
                if (response.equals("no")) {
                    break;
                } else if (response.equals("yes")) {
                    ViewInterface.showResult("select the trap you want to be activated");
                    while (true) {
                        String input = ViewInterface.getInput();
                        if (input.equals("cancel")) {
                            break;
                        } else if (input.matches(GameRegexes.SELECT.regex)) {
                            String responseSelect = Duel.processSelect(input);
                            if (responseSelect.contains("selected")) responseSelect = "trap " + responseSelect;
                            ViewInterface.showResult(responseSelect);
                            continue;
                        } else if (input.matches("activate effect")) {
                            if (Cell.getSelectedCell() == null) {
                                ViewInterface.showResult(GameResponses.NO_CARDS_SELECTED.response);
                                continue;
                            } else {
                                Cell selectedCell = Cell.getSelectedCell();
                                for (String spellAndTrapName : trapsCanBeActivated) {
                                    if (selectedCell.getCellCard().getName().equals(spellAndTrapName)) {
                                        SpellAndTrap.activateSpellEffects(this, spellAndTrapName);
                                        return;
                                    }
                                }
                                ViewInterface.showResult("Error: you can’t activate this card");
                                continue;
                            }
                        } else {
                            ViewInterface.showResult("Error: it’s not your turn to play this kind of moves");
                        }
                    }
                } else {
                    ViewInterface.showResult("Error: try again!");
                    continue;
                }

            }
*/
    }

    public boolean doPlayerSetOrSummonedThisTurn() {
        return didPlayerSetOrSummonThisTurn;
    }

    public void setDidPlayerSetOrSummonThisTurn(boolean didPlayerSetOrSummonThisTurn) {
        this.didPlayerSetOrSummonThisTurn = didPlayerSetOrSummonThisTurn;
    }

    public void surrender() {
        game.addWinner(currentTurnOpponentPlayer);
        game.addLoser(currentTurnPlayer);
        isGameEnded = true;
    }

    public void endDuel() {
        currentRound = game.getRounds();
        if (game.getRounds() == 3) {
            game.setPlayerScore(currentTurnPlayer, 2000);
        }
        game.addWinner(currentTurnPlayer);
        game.addLoser(currentTurnOpponentPlayer);
        isGameEnded = true;
    }

    public boolean isGameEnded() {
        if (currentTurnPlayer.getLP() <= 0) {
            game.addWinner(currentTurnOpponentPlayer);
            game.addLoser(currentTurnPlayer);
            return true;
        } else if (currentTurnOpponentPlayer.getLP() <= 0) {
            game.addWinner(currentTurnPlayer);
            game.addLoser(currentTurnOpponentPlayer);
            return true;
        } else return isGameEnded;
    }

    public void endGameRound() {
        closeWaitingStage();
        Player winner = game.getWinners().get(game.getWinners().size() - 1);
        Player loser = game.getLosers().get(game.getLosers().size() - 1);
        String response = calculateScoresAndMoney(winner, loser);
        currentTurnPlayer.resetGameBoard();
        currentTurnOpponentPlayer.resetGameBoard();
        undoMakeAICheatCommand();
        if (game.getRounds() == currentRound) {
           // ViewInterface.showResult(response);
            isGameEnded = true;
            try {
                NetAdapter.justSendRequest("stop my thread");
                NetAdapter.justSendRequest("remove abundant game info");
                new EndOfGameMenu().execute(response, true);
                new DuelMenu().execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            gameControllerInitialization();
            currentRound++;
           // ViewInterface.showResult(response);
            //changeCards(currentTurnPlayer);
            //changeCards(currentTurnOpponentPlayer);
            try {
                new EndOfGameMenu().execute(response, false);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void undoMakeAICheatCommand() {
        if (!currentTurnPlayer.getUser().getNickname().equals("ai") && currentTurnPlayer.isAI())
            currentTurnPlayer.setAI(false);
        if (!currentTurnOpponentPlayer.getUser().getNickname().equals("ai") && currentTurnOpponentPlayer.isAI())
            currentTurnOpponentPlayer.setAI(false);
    }

    private String calculateScoresAndMoney(Player winner, Player loser) {
        User winnerUser = winner.getUser();
        User loserUser = loser.getUser();
        String response = winnerUser.getUsername() + " won the game and the score is: ";
        game.increasePlayerScore(winner, 1000);
        game.setPlayersLP(winner, winner.getLP());
        game.setPlayersLP(loser, loser.getLP());
        response += game.getPlayerScore(winner) + "-" + game.getPlayerScore(loser);
        if (game.getRounds() == currentRound) {
            winnerUser.changeScore(game.getPlayerScore(winner));
            if (currentRound == 1) {
                winnerUser.changeMoney(1000 + winner.getLP());
                loserUser.changeMoney(100);
            } else {
                winnerUser.changeMoney(3000 + 3 * game.getPlayersMaxLP(winner));
                loserUser.changeMoney(300);
            }
            response += "\n" + game.getWinner().getUser().getUsername() + " won the whole match with score: ";
            response += game.getPlayerScore(winner) + "-" + game.getPlayerScore(loser);
        }
        return response;
    }

    public GamePhase getCurrentPhase() {
        return currentPhase;
    }

    public Game getGame() {
        return game;
    }

    public Player getCurrentTurnPlayer() {
        return currentTurnPlayer;
    }

    public void setCurrentTurnPlayer(Player currentTurnPlayer) {
        this.currentTurnPlayer = currentTurnPlayer;
    }

    public Player getCurrentTurnOpponentPlayer() {
        return currentTurnOpponentPlayer;
    }

    public void setCurrentTurnOpponentPlayer(Player currentTurnOpponentPlayer) {
        this.currentTurnOpponentPlayer = currentTurnOpponentPlayer;
    }

    public DrawPhaseController getDrawPhaseController() {
        return drawPhaseController;
    }

    public EndPhaseController getEndPhaseController() {
        return endPhaseController;
    }

    public MainPhase1Controller getMainPhase1Controller() {
        return mainPhase1Controller;
    }

    public MainPhase2Controller getMainPhase2Controller() {
        return mainPhase2Controller;
    }

    public StandByPhaseController getStandByPhaseController() {
        return standByPhaseController;
    }

    public BattlePhaseController getBattlePhaseController() {
        return battlePhaseController;
    }

    public boolean checkCommandIsNotInCurrentPhase(String command) {
        if (command.matches(GameRegexes.SUMMON.regex) || command.matches(GameRegexes.SET.regex) ||
                command.matches(GameRegexes.SET_POSITION.regex) || command.matches(GameRegexes.FLIP_SUMMON.regex) ||
                command.matches(GameRegexes.ACTIVATE_EFFECT.regex)) {
            return currentPhase != GamePhase.MAIN1 && currentPhase != GamePhase.MAIN2;
        } else if (command.matches(GameRegexes.ATTACK.regex) || command.matches(GameRegexes.ATTACK_DIRECT.regex)) {
            return currentPhase != GamePhase.BATTLE;
        }
        return false;
    }

    public boolean didCardAttackThisTurn(Cell cell) {
        return attackerCellsThisTurn.contains(cell);
    }

    public boolean canPlayerDirectAttack() {
        return currentTurnOpponentPlayer.getGameBoard().isMonsterCardZoneEmpty();
    }

    public String getSideDeckCards(Player player) {
        StringBuilder response = new StringBuilder(player.getUser().getNickname() + "'s side deck cards:\n");
        for (Card card : player.getPlayDeck().getSideDeck()) {
            response.append(card.getName()).append("\n");
        }
        return response + "\n";
    }

    private void changeCards(Player player) {
        Deck deck = player.getPlayDeck();
        String deckInfo = player.getUser().getNickname() + "’s Deck: " + deck.getName() + "\n";
        ArrayList<Card> monsters = Card.getMonstersSorted(deck.getMainDeck());
        ArrayList<Card> spellAndTraps = Card.getMagicsSorted(deck.getMainDeck());
        ArrayList<Card> mainDeck = new ArrayList<>();
        int mainDeckCounter = 1;
        deckInfo += "yugioh.client.yugioh.server.Main deck:\nMonsters:";
        for (Card card : monsters) {
            deckInfo += "\n" + mainDeckCounter + "-" + card.getName();
            mainDeckCounter++;
            mainDeck.add(card);
        }
        deckInfo += "\nSpell and Traps:";
        for (Card card : spellAndTraps) {
            deckInfo += "\n" + mainDeckCounter + "-" + card.getName();
            mainDeckCounter++;
            mainDeck.add(card);
        }
        ArrayList<Card> sideMonsters = Card.getMonstersSorted(deck.getSideDeck());
        ArrayList<Card> sideSpellAndTraps = Card.getMagicsSorted(deck.getSideDeck());
        ArrayList<Card> sideDeck = new ArrayList();
        int sideDeckCounter = 1;
        deckInfo += "\nSide deck:\nMonsters:";
        for (Card card : sideMonsters) {
            deckInfo += "\n" + sideDeckCounter + "-" + card.getName();
            sideDeckCounter++;
            sideDeck.add(card);
        }
        deckInfo += "\nSpell and Traps:";
        for (Card card : sideSpellAndTraps) {
            deckInfo += "\n" + sideDeckCounter + "-" + card.getName();
            sideDeckCounter++;
            sideDeck.add(card);
        }
        ViewInterface.showResult(deckInfo);
        sideDeckCounter -= 1;
        mainDeckCounter -= 1;
        while (true) {
            ViewInterface.showResult("select a card from main deck to remove or enter continue:");
            String input = ViewInterface.getInput();
            if (input.equals("continue")) {
                break;
            } else if (!input.matches("\\d+") || Integer.parseInt(input) > mainDeckCounter || Integer.parseInt(input) <= 0) {
                ViewInterface.showResult("Error: invalid selection");
                continue;
            } else {
                while (true) {
                    ViewInterface.showResult("now select a card from side deck to replace or enter back");
                    String input2 = ViewInterface.getInput();
                    if (input2.equals("back")) {
                        break;
                    } else if (!input2.matches("\\d+") || Integer.parseInt(input2) > sideDeckCounter || Integer.parseInt(input2) <= 0) {
                        ViewInterface.showResult("Error: invalid selection");
                        continue;
                    } else {
                        mainDeckCounter -= 1;
                        deck.removeCardFromMainDeck(mainDeck.get(Integer.parseInt(input) - 1).getName());
                        deck.addCardToSideDeck(mainDeck.get(Integer.parseInt(input) - 1));
                        deck.removeCardFromSideDeck(sideDeck.get(Integer.parseInt(input2) - 1).getName());
                        deck.addCardToMainDeck(sideDeck.get(Integer.parseInt(input2) - 1));
                        ViewInterface.showResult(mainDeck.get(Integer.parseInt(input) - 1).getName() + " replaced with " + sideDeck.get(Integer.parseInt(input2) - 1).getName());
                        player.setPlayDeck(deck);
                        break;
                    }
                }
            }

        }
    }

    public ArrayList<Cell> getAttackerCellsThisTurn() {
        return attackerCellsThisTurn;
    }

}

