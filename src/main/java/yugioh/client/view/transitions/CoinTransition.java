package yugioh.client.view.transitions;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;

public class CoinTransition extends Transition {
    private final ImageView coinImage;

    public CoinTransition(ImageView coinImage) {
        this.coinImage = coinImage;
        setCycleCount(-1);
        setCycleDuration(Duration.millis(1200));
    }

    @Override
    protected void interpolate(double v) {
        int frame = (int) Math.floor(v * 9) + 1;
        setBackground("src/resources/yugioh/PNG/coin/" + frame + ".png");
    }

    private void setBackground(String url) {
        coinImage.setImage(new Image(new File(url).toURI().toString()));
    }
}
