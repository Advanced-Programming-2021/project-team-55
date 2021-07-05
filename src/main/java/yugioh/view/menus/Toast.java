package yugioh.view.menus;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public final class Toast {

    public static void makeText(Stage ownerStage, String toastMsg) {
        int toastDelay = 2500;
        int fadeInDelay = 250;
        int fadeOutDelay = 500;
        Stage toastStage = new Stage();
        toastStage.initOwner(ownerStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Text text = new Text(toastMsg);
        text.getStyleClass().add("mainTitleText");

        text.setFill(Paint.valueOf("#ccdefc"));
        text.setFont(Font.font("Bauhaus 93", 40));
        text.setTextAlignment(TextAlignment.CENTER);

        StackPane root = new StackPane(text);
        root.setStyle("-fx-background-radius: 20; -fx-background-color: #04091a; -fx-padding: 50px;");
        root.setOpacity(0.1);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);
        toastStage.show();

//        try {//todo uncomment, commented for debug purposes
//            GameMenuController.getGameMenuController().nextPhaseTriangle.setDisable(true);
//        }catch (Exception ignored){
//        }

        Timeline fadeInTimeline = new Timeline();
        KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(fadeInDelay), new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 1));
        fadeInTimeline.getKeyFrames().add(fadeInKey1);
        fadeInTimeline.setOnFinished((ae) ->
                new Thread(() -> {
                    try {
                        Thread.sleep(toastDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Timeline fadeOutTimeline = new Timeline();
                    KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(fadeOutDelay), new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0));
                    fadeOutTimeline.getKeyFrames().add(fadeOutKey1);
                    fadeOutTimeline.setOnFinished((aeb) -> {
//                        try {//todo uncomment, commented for debug purposes
//                            GameMenuController.getGameMenuController().nextPhaseTriangle.setDisable(false);
//                        } catch (Exception ignored) {
//                        }
                        toastStage.close();
                    });
                    fadeOutTimeline.play();
                }, "toast").start());
        fadeInTimeline.play();
    }
}