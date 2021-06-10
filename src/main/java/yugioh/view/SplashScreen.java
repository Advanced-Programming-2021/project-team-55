package yugioh.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import yugioh.view.Menus.WelcomeMenu;

import java.io.File;
import java.util.Objects;

public class SplashScreen extends Application {

    private Pane splashLayout;

    public static void run(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        File file = new File("src/resources/yugioh/PNG/splashScreen/splash.png");
        ImageView splash = new ImageView(new Image(file.toURI().toString()));
        splashLayout = new VBox();
        splashLayout.getChildren().addAll(splash);
        splashLayout.setEffect(new DropShadow());
    }

    @Override
    public void start(final Stage initStage) {
        showSplash(initStage);
        startWelcomeMenu(initStage);
    }

    private void startWelcomeMenu(Stage initStage) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            try {
                initStage.close();
                WelcomeMenu.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        timeline.play();
    }

    private void showSplash(Stage initStage) {
        Scene splashScene = new Scene(splashLayout);
        splashScene.setFill(null);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.setScene(splashScene);
        initStage.getIcons().add(new Image("/yugioh/PNG/icon/icon.png"));
        initStage.show();
    }
}
