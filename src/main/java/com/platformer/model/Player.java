package com.platformer.model;

import com.platformer.model.animations.PlayerAnimation;
import com.platformer.utils.Utils;
import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * Created by Oleh on 04.12.2014.
 */
public class Player extends DynamicElement {

    private Image[] leftMoveSlides;

    private Image[] rightMoveSlides;

    private Image stendL;
    private Image stendR;

    private Direction direction = Direction.DEFFAULT;

    private Animation leftMove;
    private Animation rightMove;
    private final double RATE = 0.003;
    private final float MAX_SPEED = 20;

    public Player(World world) {
        super(world);

        initSprites();
        initPlayer();



    }

    public void updatePosition() {

        Body body = getBody();
        Vec2 vel = body.getLinearVelocity();

        System.out.println("BEGIN: Player at position: x = " + body.getPosition().x + ", y = " + body.getPosition().y +
                ". Direction is " + direction.name() + ". X velocity is " + vel.x);

        float xpos = Utils.toPixelPosX(body.getPosition().x);
        float ypos = Utils.toPixelPosY(body.getPosition().y);
        getNode().setLayoutX(xpos);
        getNode().setLayoutY(ypos);


;
        switch (direction) {
            case LEFT:
                //vel.x = sp2Min(vel.x - 5f, -30f);
                if(!maxSpeed(vel.x))
                    body.applyLinearImpulse(new Vec2(-10, 0), body.getWorldCenter());
                break;
            case RIGHT:
                //vel.x = sp2Max(vel.x + 5f, 30f);
                if(!maxSpeed(vel.x))
                    body.applyLinearImpulse(new Vec2(10, 0), body.getWorldCenter());
                break;
            case STOP:
                vel.x = vel.x * 0.95f;
                //body.setAngularVelocity(0);
                if(Math.abs(vel.x) < 0.1) {
                    body.setLinearVelocity(new Vec2(0,0));
                    body.setAngularVelocity(0);
                    direction = Direction.DEFFAULT;
                }
                break;
            default:
                break;

        }

        System.out.println("END: Player at position: x = " + body.getPosition().x + ", y = " + body.getPosition().y +
             ". Direction is " + direction.name() + ". X velocity is " + vel.x);
    }

    public void moveLeft() {
        if(leftMove == null) {
            leftMove = new PlayerAnimation((ImageView)getNode(), leftMoveSlides, new Duration(100));
            leftMove.setRate(RATE);
        }

        direction = Direction.LEFT;
        leftMove.setCycleCount(Animation.INDEFINITE);
        leftMove.play();
    }

    public void moveRight() {
        if(rightMove == null) {
            rightMove = new PlayerAnimation((ImageView)getNode(), rightMoveSlides, new Duration(100));
            rightMove.setRate(RATE);
        }

        direction = Direction.RIGHT;
        rightMove.setCycleCount(Animation.INDEFINITE);
        rightMove.play();
    }

    public void stop() {
        if(leftMove != null)
            leftMove.stop();
        if(rightMove != null)
            rightMove.stop();

        switch (direction) {
            case LEFT:
                ((ImageView)getNode()).setImage(stendL);
                direction = Direction.STOP;
                break;
            case RIGHT:
                ((ImageView)getNode()).setImage(stendR);
                direction = Direction.STOP;
                break;
        }

//        if(!direction.equals(Direction.STOP))
//            direction = Direction.DEFFAULT;


    }


    private void initPlayer() {

        CircleShape ps = new CircleShape();
        ps.m_radius = 10f;

        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.friction = 0;


        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position = new Vec2(65, 65);


        this.setBody(getWorld().createBody(bd));
        this.getBody().createFixture(fd);

        this.setNode(new ImageView(stendR));
        this.getNode().setLayoutX(Utils.toPixelPosX(bd.position.x));
        this.getNode().setLayoutY(Utils.toPixelPosY(bd.position.y));

    }

    private void initSprites() {

        try {

            stendR = new Image(getClass().getResource("/playerImgs/standR.png").openStream());

            stendL = new Image(getClass().getResource("/playerImgs/standL.png").openStream());

            leftMoveSlides = new Image[] {
                    new Image(getClass().getResource("/playerImgs/moveL1.png").openStream()),
                    new Image(getClass().getResource("/playerImgs/moveL2.png").openStream()),
                    new Image(getClass().getResource("/playerImgs/moveL3.png").openStream())
            };

            rightMoveSlides = new Image[] {
                    new Image(getClass().getResource("/playerImgs/moveR1.png").openStream()),
                    new Image(getClass().getResource("/playerImgs/moveR2.png").openStream()),
                    new Image(getClass().getResource("/playerImgs/moveR3.png").openStream())
            };

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private float sp2Max(float vel, float max) {

        if(vel >= max)
            return max;
        else
            return vel;

    }

    private float sp2Min(float vel, float max) {

        if(vel <= max)
            return max;
        else
            return vel;

    }

    private boolean maxSpeed(float velocity) {
        return Math.abs(velocity)>=MAX_SPEED;
    }



    private enum Direction {
        LEFT, RIGHT, STOP, DEFFAULT;
    }


}
