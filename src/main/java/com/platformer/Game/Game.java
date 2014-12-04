package com.platformer.Game;

import com.platformer.model.Player;
import com.platformer.utils.Utils;
import com.platformer.world.GameWorld;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;



/**
 * Created by Oleh on 04.12.2014.
 */
public class Game extends Application{

    private final int HEIGHT = Utils.HEIGHT;
    private final int WIDTH = Utils.WIDTH;

    @Override
    public void start(Stage primaryStage) throws Exception {

        GameWorld world = new GameWorld();
        Player player = new Player(world.getWorld());


        Group objects = new Group();

        objects.getChildren().add(player.getNode());

        primaryStage.setResizable(false);
        primaryStage.setTitle("Game Demo");

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        Duration duration = Duration.seconds(1.0/60.0);

        EventHandler<ActionEvent> ev = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                world.step();
                player.updatePosition();

            }
        };

        EventHandler<KeyEvent> keyPress = keyEvent -> {
            switch (keyEvent.getCode()) {
                case LEFT:
                    player.moveLeft();
                    break;
                case RIGHT:
                    player.moveRight();
                    break;
                default:
                    System.out.println("Into default");
                    break;
            }
        };

        EventHandler<KeyEvent> keyRelease = keyEvent -> player.stop();


        KeyFrame keyFrame = new KeyFrame(duration, ev, null, null);
        timeline.getKeyFrames().addAll(keyFrame);
        timeline.play();


        Scene scene = new Scene(objects, WIDTH, HEIGHT);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPress);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyRelease);

        primaryStage.setScene(scene);

        primaryStage.show();

    }


}
