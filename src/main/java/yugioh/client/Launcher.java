package yugioh.client;

import yugioh.client.view.NetAdapter;

import java.util.Scanner;

public class Launcher {

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("enter server host:");
//        NetAdapter.setHost(scanner.nextLine());
//        System.out.println("enter server port:");
//        NetAdapter.setPort(scanner.nextInt());
//        scanner.nextLine();
        NetAdapter.initialize();
        Main.main(args);
    }
}
