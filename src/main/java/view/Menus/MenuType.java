package view.Menus;

public enum MenuType {
    LOGIN("Login Menu"),
    MAIN("Main Menu"),
    DUEL("Duel Menu"),
    DECK("Deck Menu"),
    SCOREBOARD("ScoreBoard Menu"),
    SHOP("Shop Menu"),
    PROFILE("Profile Menu"),
    IMPORTEXPORT("Import/Export Menu");

    public final String menuName;

    MenuType(String label) {
        this.menuName = label;
    }
}
