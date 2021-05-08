package view;

import controller.AIPlayerController;
import view.gamephases.Duel;
import view.gamephases.GameResponses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewInterface {
    private static final Scanner input = new Scanner(System.in);

    public static String getInput() {
        String command;
        try{
            if (Duel.getGameController().getCurrentTurnPlayer().isAI() && AIPlayerController.isIsAllowedToCommand())
                command = (new AIPlayerController(AIPlayerController.orderKind.RANDOM,
                    AIPlayerController.orderKind.RANDOM)).getSpecialCommand();
            else command = input.nextLine();
        }catch(Exception e){
            command = input.nextLine();
        }
        return sortFields(command);
    }

    public static void showResult(String result) {
        if (!result.equals("")) {
            if (result.startsWith("Error: ")) {
                AIPlayerController.setLastResponse(result);
                System.out.println(ConsoleColors.RED + result.replaceAll("Error: ", "") + ConsoleColors.RESET);
            } else {
//                try {
//                    if (Duel.getGameController().getCurrentTurnPlayer().isAI()){
                        AIPlayerController.setLastResponse(result);
////                        return;
//                    }
//                }catch (Exception ignored){}

                if (Responses.responseExists(result) || GameResponses.responseExists(result)) {
                    System.out.println(ConsoleColors.GREEN + result + ConsoleColors.RESET);
                } else {
                    System.out.println(result);
                }
            }
        }
    }

    public static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) return null;
        return matcher;
    }

    private static String sortFields(String input) {
        if (input.indexOf("--") != -1) {
            input += "  ";
            String init = input.substring(0, input.indexOf("--"));
            ArrayList<String> fields = new ArrayList<>();
            int start = input.indexOf("--");
            for (int i = input.indexOf("--"); i < input.length(); i++) {
                if (i < input.length() - 2 && input.charAt(i) == '-' && input.charAt(i + 1) == '-' || i == input.length() - 1) {
                    fields.add(input.substring(start, i));
                    start = i;
                }
            }

            Collections.sort(fields);
            String sortedFields = "";
            for (int i = 0; i < fields.size(); i++) {
                sortedFields += fields.get(i);
            }
            return (init + sortedFields).trim();
        } else return input;
    }

}
