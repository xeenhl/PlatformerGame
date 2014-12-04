package com.platformer.model.animations;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Created by admin on 04.12.14.
 */
public class PlayerAnimation extends Transition{

    private final ImageView view;
    private final Image[] images;
    private final int  maxI;
    private int i = 0;


    public PlayerAnimation(ImageView view, Image[] images, Duration duration) {
        this.view = view;
        this.images = images;

        maxI = images.length - 1;

        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double v) {

        if(i > maxI)
            i = 0;

        //System.out.println("Playing animation for pic number: " + i + ", with maxI = " + maxI);
        view.setImage(images[i]);
        i++;
    }
}
