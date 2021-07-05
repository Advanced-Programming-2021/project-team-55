package yugioh.view.gamephases;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.controller.gamephasescontrollers.MainPhasesController;
import yugioh.model.board.Cell;
import yugioh.model.board.GameBoard;
import yugioh.model.exceptions.GameException;
import yugioh.view.menus.WelcomeMenu;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.util.ArrayList;

public class CardActionsMenu implements MainPhasesController {
    private static Stage actionsStage=new Stage();

    private static double xImage;
    private static double yImage;
    private static Button actionButton=new Button();
    private static Rectangle imageRectangle;
    private static ArrayList<String> monsterActions =new ArrayList<>();
    private static ArrayList<String>spellAndTrapActions=new ArrayList<>();
    private static ArrayList<String>thisActions=new ArrayList<>();
    private static Pane gamePane;
    private static int place=6;
    private static GameController gameController;

    private static double lastMousePositionX = 0;
    private static double lastMousePositionY = 0;
    static {
        actionsStage.initOwner(WelcomeMenu.stage);
        actionsStage.initModality(Modality.NONE);
        actionsStage.initStyle(StageStyle.UNDECORATED);
        {
            monsterActions.add("set");
            monsterActions.add("summon");


            spellAndTrapActions.add("set");
            spellAndTrapActions.add("activate");
        }
    }

    public static void execute(Rectangle rectangle, GameController controller) throws Exception {
        imageRectangle=rectangle;
        gameController=controller;
        start();
    }

    public static void start() throws Exception {
        if(gameController.currentTurnPlayer.getGameBoard().isCellInHandZone(Cell.getSelectedCell())) {
            if (gameController.currentPhase.equals(GamePhase.MAIN1) || gameController.currentPhase.equals(GamePhase.MAIN2)) {
                openMainPhaseActions();
            }
        }
        else{
            //todo : add actions of cards in monster zone and spell zone
        }

    }

    public static void close(){
        if(actionsStage!=null)
        actionsStage.close();
    }

    public static void setCoordinates(double x, double y) {
        xImage=x;
        yImage=y;
    }
    public static void change(){
        int counter=0;
        for(String action: thisActions){
            if(action.equals(actionButton.getText())){
                if(counter== thisActions.size()-1){
                    counter=-1;
                }
                actionButton.setText(thisActions.get(counter+1));
                return;
            }
            counter++;
        }
    }
    public static void handleSet(){
           try {
                new CardActionsMenu().setCard(gameController);
           } catch (GameException e) {
               e.printStackTrace();
              //todo show an error box
           }
            actionsStage.close();
        }

    public static void setGamePane(Pane pane){
        gamePane=pane;
    }

    public static double getLastMousePositionX() {
        return lastMousePositionX;
    }

    public static void setLastMousePositionX(double lastMousePositionX) {
        CardActionsMenu.lastMousePositionX = lastMousePositionX;
    }

    public static double getLastMousePositionY() {
        return lastMousePositionY;
    }

    public static void setLastMousePositionY(double lastMousePositionY) {
        CardActionsMenu.lastMousePositionY = lastMousePositionY;
    }
    public static void openMainPhaseActions(){
        if(Cell.getSelectedCell().getCellCard().isMonster()){
            thisActions=monsterActions;
        }
        else{
            thisActions=spellAndTrapActions;
        }
        actionButton.setMinWidth(70);
        Pane pane=new Pane();
        pane.getChildren().add(actionButton);
        pane.setMaxWidth(70);
        pane.setMaxHeight(30);
        actionButton.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1.equals("set")){
                    if(gameController.doPlayerSetOrSummonedThisTurn()){
                        actionButton.setDisable(true);
                    }
                    else{
                        actionButton.setDisable(false);
                    }
                }
            }
        });
        actionButton.setText(thisActions.get(0));
        actionButton.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(actionButton.getText().equals("set")){
                    handleSet();
                }
            }
        });
        Scene scene=WelcomeMenu.createScene(pane);
        actionsStage.setX(xImage);
        actionsStage.setY(yImage);
        actionsStage.setScene(scene);
        actionsStage.show();
    }
}
