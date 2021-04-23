package controller.gamephasescontrollers;

public interface methods {
    default void changePhase(GameController gameController,GamePhase currentPhase){
        switch (currentPhase){
            case DRAW:{
               gameController.setCurrentPhase(GamePhase.STANDBY);
                break;
            }
            case STANDBY:{
                gameController.setCurrentPhase(GamePhase.MAIN1);
                break;
            }
            case MAIN1:{
                gameController.setCurrentPhase(GamePhase.BATTLE);
                break;
            }
            case BATTLE:{
                gameController.setCurrentPhase(GamePhase.MAIN2);
                break;
            }
            case MAIN2:{
                gameController.setCurrentPhase(GamePhase.END);
                break;
            }
            case END:{
                gameController.setCurrentPhase(GamePhase.DRAW);
                //changeTurn();
                break;
            }

        }
    }
}
