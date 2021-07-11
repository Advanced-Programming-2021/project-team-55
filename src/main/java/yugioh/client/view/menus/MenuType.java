package yugioh.client.view.menus;

public enum MenuType {
    LOGIN("Login Menu"),
    MAIN("yugioh.server.Main Menu"),
    DUEL("Duel Menu"),
    DECK("Deck Menu"),
    SCOREBOARD("ScoreBoard Menu"),
    SHOP("Shop Menu"),
    PROFILE("Profile Menu"),
    IMPORT_EXPORT("Import/Export Menu");

    public final String menuName;

    MenuType(String label) {
        this.menuName = label;
    }
}
