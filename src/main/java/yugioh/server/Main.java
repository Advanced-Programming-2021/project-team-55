package yugioh.server;

import yugioh.server.model.cards.monsters.BattleOX;
import yugioh.server.view.Menus.Menu;
import yugioh.server.view.NetAdapter;

public class Main {
    public static void main(String[] args) {
        new NetAdapter(3333);
        Menu.run();
    }
}
