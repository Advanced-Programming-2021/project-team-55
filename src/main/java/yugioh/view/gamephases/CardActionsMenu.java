package yugioh.view.gamephases;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import yugioh.model.board.Cell;
import yugioh.view.menus.WelcomeMenu;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;

public class CardActionsMenu extends WelcomeMenu {
    private static Stage actionsStage;
    private double xImage;
    private double yImage;
    private Button actionButton=new Button();
    private ArrayList<String>actions=new ArrayList<>();

    {
        actions.add("set");
        actions.add("summon");
    }
    @Override
    public void execute() throws Exception {
        start(stage);
    }

    @Override
    public void start(Stage stage) throws Exception {
        actionButton.setText("set");
        actionButton.setMinWidth(70);
        Pane pane=new Pane();
        pane.getChildren().add(actionButton);
        pane.setMaxWidth(70);
        pane.setMaxHeight(30);
        Scene scene=new Scene(pane);
        actionsStage=new Stage();
        actionsStage.initOwner(stage);
        actionsStage.initModality(Modality.NONE);
        actionsStage.initStyle(StageStyle.UNDECORATED);
        actionsStage.setX(xImage);
        actionsStage.setY(yImage);
        actionsStage.setScene(scene);
        actionsStage.show();
    }
    public static void close(){
        actionsStage.close();
    }

    public void setCoordinates(double x, double y) {
        xImage=x;
        yImage=y;
    }
    public void change(){
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
}
