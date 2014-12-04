package com.platformer.Game;

import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by Oleh on 04.12.2014.
 */
public class Game extends Application{

    private final int HEIGHT = 600;
    private final int WIDTH = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Group objects = new Group();

        primaryStage.setResizable(false);
        primaryStage.setTitle("Game Demo");

        Scene scene = new Scene(objects, WIDTH, HEIGHT);

        primaryStage.setScene(scene);

        KeyFrame kf = new KeyFrame(Duration.INDEFINITE);

    }


}
