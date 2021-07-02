package yugioh.view.gamephases;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import yugioh.view.menus.WelcomeMenu;

public class CardActionsMenu extends WelcomeMenu {
    private Stage actionsStage;
    @Override
    public void execute() throws Exception {
        super.execute();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Button button=new Button();
        button.setText("set");
        Pane pane=new Pane();
        pane.getChildren().add(button);
        pane.setMaxWidth(20);
        pane.setMaxHeight(30);
        Scene scene=new Scene(pane);
        if(actionsStage!=null){
            actionsStage.close();
        }
        actionsStage=new Stage();
        actionsStage.setScene(scene);
        actionsStage.show();
    }
}
