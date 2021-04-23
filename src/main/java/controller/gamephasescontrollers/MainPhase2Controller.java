package controller.gamephasescontrollers;

public class MainPhase2Controller {

    private static MainPhase2Controller mainPhase2Controller;

    private MainPhase2Controller() {

    }

    public static MainPhase2Controller getInstance() {
        if (mainPhase2Controller == null) {
            return new MainPhase2Controller();
        }
        return mainPhase2Controller;
    }

}
