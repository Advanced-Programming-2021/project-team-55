package view;

import java.util.Scanner;

public class ViewInterface {
    public static Scanner input=new Scanner(System.in);

    public static String getInput(){
        return input.nextLine();
    }
}
