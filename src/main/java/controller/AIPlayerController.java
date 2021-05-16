package controller;

import com.opencsv.CSVWriter;
import controller.gamephasescontrollers.GameController;
import model.CoinDice;
import model.board.CardStatus;
import model.board.Cell;
import model.cards.Monster;
import view.gamephases.Duel;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;


public class AIPlayerController {

    private static String lastResponse = "";
    private static String secondLastResponse = "";
    private static String lastAICommand = "";
    private static String lastAISelectionCommand = "";

    private static boolean isGameEnded = false;
    ArrayList<String> battleSelectionCommands = new ArrayList<>();
    ArrayList<String> battlePhaseCommands = new ArrayList<>();
    ArrayList<String> mainPhaseCommands = new ArrayList<>();
    ArrayList<String> mainPhaseSelectionCommands = new ArrayList<>();

    public AIPlayerController(orderKind selectionOrder, orderKind commandsOrder) {
        isGameEnded = false;
        for (int i = 1; i <= 5; i++) {
            mainPhaseSelectionCommands.add("select --hand " + i);
        }
        for (int i = 1; i <= 5; i++) {
            mainPhaseSelectionCommands.add("select --monster " + i);
        }
        for (int i = 1; i <= 5; i++) {
            mainPhaseSelectionCommands.add("select --spell " + i);
        }

        for (int i = 1; i <= 5; i++) {
            battleSelectionCommands.add("select --monster " + i);
        }

        mainPhaseCommands.add("activate effect");
        mainPhaseCommands.add("summon");
        mainPhaseCommands.add("set");
        mainPhaseCommands.add("set --position attack");
        mainPhaseCommands.add("set --position defense");
        mainPhaseCommands.add("flip-summon");

        battlePhaseCommands.add("attack direct");
        battlePhaseCommands.add("attack direct");
        battlePhaseCommands.add("attack direct");
        battlePhaseCommands.add("attack direct");
        battlePhaseCommands.add("attack direct");
        for (int i = 1; i <= 5; i++) {
            battlePhaseCommands.add("attack " + i);
        }

        if (selectionOrder == orderKind.RANDOM) Collections.shuffle(battleSelectionCommands);
        if (selectionOrder == orderKind.RANDOM) Collections.shuffle(mainPhaseSelectionCommands);
        if (commandsOrder == orderKind.RANDOM) Collections.shuffle(battlePhaseCommands);
        if (commandsOrder == orderKind.RANDOM) Collections.shuffle(mainPhaseCommands);

    }

    public static boolean isIsGameEnded() {
        return isGameEnded;
    }

    public static void setIsGameEnded(boolean isGameEnded) {
        AIPlayerController.isGameEnded = isGameEnded;
    }

    public static void setLastResponse(String lastResponse) {
        secondLastResponse = AIPlayerController.lastResponse;
        AIPlayerController.lastResponse = lastResponse;
    }

    public static void recordGameLogs(GameController gameController) {
//        try {
//            String csv = "src\\resources\\gameLog\\data.csv";
//            CSVWriter writer = new CSVWriter(new FileWriter(csv, true));
//
//            String[] record = new String[4];
//            record[0] = (gameController.getMainPhase1Controller().showGameBoard(gameController.getCurrentTurnPlayer(), gameController.getCurrentTurnOpponentPlayer()));
//            record[1] = (secondLastResponse);
//            record[2] = (lastAISelectionCommand);
//            record[3] = (lastAICommand);
////            record[0] = "" + saveStringAndReturnCode(gameController.getMainPhase1Controller().showGameBoard(gameController.getCurrentTurnPlayer(), gameController.getCurrentTurnOpponentPlayer()));
////            record[1] = "" + saveStringAndReturnCode(secondLastResponse);
////            record[2] = "" + saveStringAndReturnCode(lastAISelectionCommand);
////            record[3] = "" + saveStringAndReturnCode(lastAICommand);
//
//            writer.writeNext(record);
//
//            writer.close();
//        } catch (Exception ignored) {
//        }
    }
//
//    private static int saveStringAndReturnCode(String str){
//        int code = 0;
//        while (true){
//            try{
//                String content = DataBaseController.getInstance().readFileContent("src\\resources\\gameLog\\commands\\" + code + ".txt");
//                if (content == null) throw new Exception();
//                if (content.equals(str)) return code;
//            }catch (Exception e) {
//                try{
//                    DataBaseController.writeFile("src\\resources\\gameLog\\commands\\" + code + ".txt", str);
//                    return code;
//                }catch (Exception ignored){
//                }
//            }
//            code++;
//        }
//    }

    public String getSelectCommandForMainPhases() {
        String string;
        try {
            string = mainPhaseSelectionCommands.get(0);
            mainPhaseSelectionCommands.remove(0);
        } catch (Exception e) {
            string = "next phase";
        }
        lastAISelectionCommand = string;
        return string;
    }

    public String getSelectCommandForBattlePhase() {
        String string;
        try {
            string = battleSelectionCommands.get(0);
            battleSelectionCommands.remove(0);
        } catch (Exception e) {
            string = "next phase";
        }
        lastAISelectionCommand = string;
        return string;
    }

    public String getMainCommandForBattlePhase() {
        String string;
        try {
            string = battlePhaseCommands.get(0);
            battlePhaseCommands.remove(0);
        } catch (Exception e) {
            string = "next phase";
        }
        if (string.matches("attack \\d+")) {
            try {
                int num = Integer.parseInt(string.substring(7));
                Cell opponentMonster = Duel.getGameController().getCurrentTurnOpponentPlayer().getGameBoard().getMonsterByIndex(num);
                Cell aiMonster = Cell.getSelectedCell();
                if (opponentMonster.getCardStatus() == CardStatus.OFFENSIVE_OCCUPIED) {
                    if (((Monster) opponentMonster.getCellCard()).getAtk() > ((Monster) aiMonster.getCellCard()).getAtk()) {
                        this.getMainCommandForBattlePhase();
                    }
                }
                if (opponentMonster.getCardStatus() == CardStatus.DEFENSIVE_HIDDEN) {
                    if (((Monster) opponentMonster.getCellCard()).getDef() > ((Monster) aiMonster.getCellCard()).getAtk()) {
                        this.getMainCommandForBattlePhase();
                    }
                }
            } catch (Exception ignored) {
                this.getMainCommandForBattlePhase();
            }
        }
        lastAICommand = string;
        return string;
    }

    public String getMainCommandForMainPhases() {
        String string;
        try {
            string = mainPhaseCommands.get(0);
            mainPhaseCommands.remove(0);
        } catch (Exception e) {
            string = "next phase";
        }
        lastAICommand = string;
        return string;
    }

    public String getSpecialCommand() {
        if (lastResponse.contains("yes/no")) {
            String command = randomYesNo();
            lastAICommand = command;
            return command;
        }
        if (lastResponse.contains("number")) {
            String command = (CoinDice.rollDice() + CoinDice.rollDice() - 1) + "";
            lastAICommand = command;
            return command;
        }
        if (lastResponse.contains("you should special summon right now")) {
            String command = "summon";
            lastAICommand = command;
            return command;
        }
        if (lastResponse.contains("United We Stand") || lastResponse.contains("Sword of dark destruction")) {
            String command = "select --monster " + CoinDice.rollDice();
            lastAICommand = command;
            return command;
        }
        if (lastResponse.contains("invalid format! try again:")) {
            String command = "me " + CoinDice.rollDice();
            lastAICommand = command;
            return command;
        }
        if (lastResponse.contains("trap card selected")) {
            String command = "activate effect";
            lastAICommand = command;
            return command;
        }
        if (lastResponse.contains("select the trap you want to be activated")) {
            int randomNumber = CoinDice.rollDice();
            String command = "select --spell " + randomNumber;
            if (randomNumber == 6) command = "cancel";
            lastAICommand = command;
            return command;
        }
        if (lastResponse.contains("now choose at most 2 opponent spell or traps") ||
                lastResponse.contains("select second spell or trap") ||
                lastResponse.contains("select a spell or trap card from your opponent")) {
            int randomNumber = CoinDice.rollDice();
            String command = "select --spell " + randomNumber + " --opponent";
            if (randomNumber == 6) command = "cancel";
            lastAICommand = command;
            return command;
        }
        if (lastResponse.contains("you should select a card from hand!")) {
            String command = "select --hand " + CoinDice.rollDice();
            if (CoinDice.rollDice() == 6) command = "cancel";
            lastAICommand = command;
            return command;
        }
        if (lastResponse.contains("select a spell or trap on the field to destroy") ||
                lastResponse.contains("select a spell or trap card from your opponent gameBoard")) {
            String command = "select --opponent --spell " + CoinDice.rollDice();
            if (CoinDice.rollDice() == 6) command = "cancel";
            lastAICommand = command;
            return command;
        }
        if (lastResponse.equals("Error: try again") || lastResponse.equals("Error: no card found in the given position")) {
            if (CoinDice.tossCoin() == 1) {
                lastAICommand = "cancel";
                return lastAICommand;
            }
        }
        if (lastResponse.contains("choose a monster from your or your opponents graveyard")) {
            String command;
            if (CoinDice.tossCoin() == 1) command = "me " + CoinDice.rollDice();
            else command = "opponent " + CoinDice.rollDice();
            lastAICommand = command;
            return command;
        }

        if (CoinDice.rollDice() == 6) {
            lastAICommand = "cancel";
            return lastAICommand;
        }
        return getSelectCommandForMainPhases();
    }

    private String randomYesNo() {
        if (CoinDice.tossCoin() == 1) {
            return "yes";
        }
        return "no";
    }

    public enum orderKind {ORDINARY, RANDOM}

}
