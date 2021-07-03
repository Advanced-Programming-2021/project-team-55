package yugioh.view.gamephases;

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
    private static ImageView image;
    private static ArrayList<String>actions=new ArrayList<>();
    private static Pane gamePane;
    private static int place=6;
    private static GameController gameController;
    static {
        actionsStage.initOwner(WelcomeMenu.stage);
        actionsStage.initModality(Modality.NONE);
        actionsStage.initStyle(StageStyle.UNDECORATED);
        {
            actions.add("set");
            actions.add("summon");
        }
    }
    public static void execute(ImageView imageView, GameController controller) throws Exception {
        image=imageView;
        gameController=controller;
        start();
    }

    public static void start() throws Exception {
        actionButton.setText("set");
        actionButton.setMinWidth(70);
        Pane pane=new Pane();
        pane.getChildren().add(actionButton);
        pane.setMaxWidth(70);
        pane.setMaxHeight(30);
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
        for(String action:actions){
            if(action.equals(actionButton.getText())){
                if(counter==actions.size()-1){
                    counter=-1;
                }
                actionButton.setText(actions.get(counter+1));
                return;
            }
            counter++;
        }
    }
    public static void handleSet(){
        if(Cell.getSelectedCell().getCellCard().isMonster()){
           try {
               ImagePattern imagePattern=new ImagePattern(new Image(new File(Cell.getSelectedCell().getCellCard().getImage()).toURI().toString()));
               ((Rectangle)gamePane.getChildren().get(place++)).setFill(imagePattern);
               new CardActionsMenu().setCard(gameController,image);
               ((HBox)gamePane.getChildren().get(1)).getChildren().remove(image);
           } catch (GameException e) {
               e.printStackTrace();
              //todo show an error box
           }
            actionsStage.close();
        }
    }
    public static void setGamePane(Pane pane){
        gamePane=pane;
        GameBoard.setGamePane(gamePane);
    }
}
