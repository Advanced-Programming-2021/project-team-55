package yugioh.view.gamephases;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import yugioh.model.board.Cell;
import yugioh.view.menus.WelcomeMenu;

public class CardActionsMenu extends WelcomeMenu {
    private static Stage actionsStage;
    private double xImage;
    private double yImage;
    @Override
    public void execute() throws Exception {
        start(stage);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Button button=new Button();
        button.setText("set");
        Pane pane=new Pane();
        pane.getChildren().add(button);
        pane.setMaxWidth(20);
        pane.setMaxHeight(20);
        Scene scene=new Scene(pane);
        actionsStage=new Stage();
        actionsStage.initModality(Modality.NONE);
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
}
