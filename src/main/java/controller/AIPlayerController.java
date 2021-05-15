package controller;

import com.opencsv.CSVWriter;
import controller.gamephasescontrollers.GameController;
import model.CoinDice;

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

    public static String getLastResponse() {
        return lastResponse;
    }

    public static void setLastResponse(String lastResponse) {
        secondLastResponse = AIPlayerController.lastResponse;
        AIPlayerController.lastResponse = lastResponse;
    }

    public static void recordGameLogs(GameController gameController) {
        try {
            String csv = "src\\resources\\gameLog\\data.csv";
            CSVWriter writer = new CSVWriter(new FileWriter(csv, true));

            String[] record = new String[4];
            record[0] = gameController.getMainPhase1Controller().showGameBoard(gameController.getCurrentTurnPlayer(), gameController.getCurrentTurnOpponentPlayer());
            record[1] = secondLastResponse;
            record[2] = lastAISelectionCommand;
            record[3] = lastAICommand;

            writer.writeNext(record);

            writer.close();
        } catch (Exception ignored) {
        }
    }

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
