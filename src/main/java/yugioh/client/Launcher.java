package yugioh.client;

import java.util.Scanner;

public class Launcher {
    public static String host;
    public static int port;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter server host:");
        host = scanner.nextLine();
        System.out.println("enter server port:");
        port = scanner.nextInt();
        scanner.nextLine();
        Main.main(args);
    }
}
