package yugioh.view.transitions;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ExplodeAnimation extends Transition {

    private final ImageView imageView;

    public ExplodeAnimation(ImageView imageView) {
        this.imageView = imageView;
        setCycleDuration(Duration.millis(500));
        this.play();
    }

    @Override
    protected void interpolate(double v) {
        int frame = (int) Math.floor(v * 7);
        if (frame == 7) imageView.setImage(null);
        else imageView.setImage(new Image("/yugioh/PNG/explosion/" + frame + ".png"));
    }
}
