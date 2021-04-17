package view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewInterface {
    private static final Scanner input = new Scanner(System.in);

    public static String getInput() {
        return sortFields(input.nextLine());
    }

    public static void showResult(String result) {
        System.out.println(result);
    }

    public static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) return null;
        return matcher;
    }

    private static String interpreteAbreviations(String input) {
        return "has to be handled";
    }//todo fill

    private static String sortFields(String input) {
        if (input.indexOf("--") != -1) {
            input += " ";
            String init = input.substring(0, input.indexOf("--"));
            Pattern pattern = Pattern.compile("(--[^-]+)");
            Matcher matcher = pattern.matcher(input);
            ArrayList<String> fields = new ArrayList<>();
            while (matcher.find()) {
                fields.add(matcher.group());
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
