package yugioh.client.controller.gamephasescontrollers;

public class MainPhase2Controller implements MainPhasesController {

    private static MainPhase2Controller mainPhase2Controller;

    private MainPhase2Controller() {
    }

    public static MainPhase2Controller getInstance() {
        if (mainPhase2Controller == null) mainPhase2Controller = new MainPhase2Controller();
        return mainPhase2Controller;
    }

}
