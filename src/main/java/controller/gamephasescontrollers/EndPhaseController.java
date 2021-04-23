package controller.gamephasescontrollers;

public class EndPhaseController {

    private static EndPhaseController endPhaseController;

    private EndPhaseController() {

    }

    public static EndPhaseController getInstance() {
        if (endPhaseController == null) {
            return new EndPhaseController();
        }
        return endPhaseController;
    }

    private String changePlayerTurn() {
        return null;
    }

}
