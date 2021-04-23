package controller.gamephasescontrollers;

public class MainPhase1Controller {

    private static MainPhase1Controller mainPhase1Controller;

    private MainPhase1Controller() {

    }

    public static MainPhase1Controller getInstance() {
        if (mainPhase1Controller == null) {
            return new MainPhase1Controller();
        }
        return mainPhase1Controller;
    }

}
