package yugioh.client.controller.gamephasescontrollers;

public class MainPhase1Controller implements MainPhasesController {

    private static MainPhase1Controller mainPhase1Controller;

    private MainPhase1Controller() {
    }

    public static MainPhase1Controller getInstance() {
        if (mainPhase1Controller == null) mainPhase1Controller = new MainPhase1Controller();
        return mainPhase1Controller;
    }

}
