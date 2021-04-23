package controller.gamephasescontrollers;

public enum GamePhase {

    DRAW("draw phase"),
    STANDBY("standby phase"),
    MAIN1("main phase 1"),
    BATTLE("battle phase"),
    MAIN2("main phase 2"),
    END("end phase");
    public String name;
    private GamePhase(String name){
        this.name = name;
    }
}
