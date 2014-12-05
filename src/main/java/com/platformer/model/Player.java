package com.platformer.model;

import com.platformer.model.animations.PlayerAnimation;
import com.platformer.utils.Utils;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
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
    private Image[] jumpL, jumpR;

    private Image stendL;
    private Image stendR;

    private Direction direction = Direction.DEFFAULT;
    private Direction jumpDirection = Direction.RIGHT;

    private Animation leftMove;
    private Animation rightMove;
    private Animation leftJump;
    private Animation rightJump;
    private final double RATE = 0.003;
    private final float MAX_SPEED = 20;

    private boolean isInJump = false;
    private boolean goAndJump = false;

    public Player(World world) {
        super(world);

        initSprites();
        initPlayer();



    }

    public void updatePosition() {

        Body body = getBody();
        Vec2 vel = body.getLinearVelocity();

        float xpos = Utils.toPixelPosX(body.getPosition().x);
        float ypos = Utils.toPixelPosY(body.getPosition().y);
        getNode().setLayoutX(xpos);
        getNode().setLayoutY(ypos);


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

        if(goAndJump) {
            if(!isInJump) {
                body.applyLinearImpulse(new Vec2(0, 30), body.getWorldCenter());
                isInJump = true;
            }

            goAndJump = false;
        }

        if(Math.abs(vel.y) <= 0.1) {
            if(body.getPosition().y < 9) {
                if(isInJump) {
                    isInJump = false;
                    jumpStop();
                }
            }
        }

        System.out.println("UPDATE END: Player at position: x = " + body.getPosition().x + ", y = " + body.getPosition().y +
             ". Direction is " + direction.name() + ". X velocity is " + vel.x + ", Y velocity is " + vel.y + ". Is in jump = " + isInJump+
             ". Jum direction is " + jumpDirection.name());
    }

    public void moveLeft() {
        if(leftMove == null) {
            leftMove = new PlayerAnimation((ImageView)getNode(), leftMoveSlides, new Duration(100));
            leftMove.setRate(RATE);
        }

        direction = Direction.LEFT;
        jumpDirection = direction;
        leftMove.setCycleCount(Animation.INDEFINITE);
        if(!isInJump)
            leftMove.play();
    }

    public void moveRight() {
        if(rightMove == null) {
            rightMove = new PlayerAnimation((ImageView)getNode(), rightMoveSlides, new Duration(100));
            rightMove.setRate(RATE);
        }

        direction = Direction.RIGHT;
        jumpDirection = direction;
        rightMove.setCycleCount(Animation.INDEFINITE);
        if(!isInJump)
            rightMove.play();
    }

    public void jump() {

        if(jumpDirection.equals(Direction.LEFT)) {
            if(leftJump == null) {
                leftJump = new PlayerAnimation((ImageView)getNode(), jumpL, new Duration(100));
                leftJump.setRate(RATE);
            }

            leftJump.setCycleCount(Animation.INDEFINITE);
            leftJump.play();

        }

        if(jumpDirection.equals(Direction.RIGHT)) {
            if(rightJump == null) {
                rightJump = new PlayerAnimation((ImageView)getNode(), jumpR, new Duration(100));
                rightJump.setRate(RATE);
            }

            rightJump.setCycleCount(Animation.INDEFINITE);
            rightJump.play();
        }

        goAndJump = true;

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

    private void jumpStop() {
        if(rightJump != null)
            rightJump.stop();
        if(leftJump != null)
            leftJump.stop();

        switch (jumpDirection) {
            case LEFT:
                ((ImageView)getNode()).setImage(stendL);
                break;
            case RIGHT:
                ((ImageView)getNode()).setImage(stendR);
                break;
        }
    }


    private void initPlayer() {

        PolygonShape ps = new PolygonShape();
        //CircleShape ps = new CircleShape();
        ps.setAsBox(Utils.toJBoxWidth(16*3), Utils.toJBoxHeight(16*3));

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

            stendR = new Image(getClass().getResource("/playerImgs/standR.png").openStream(), 16*3, 16*3, false, false);

            stendL = new Image(getClass().getResource("/playerImgs/standL.png").openStream(), 16*3, 16*3, false, false);

            leftMoveSlides = new Image[] {
                    new Image(getClass().getResource("/playerImgs/moveL1.png").openStream(), 16*3, 16*3, false, false),
                    new Image(getClass().getResource("/playerImgs/moveL2.png").openStream(), 16*3, 16*3, false, false),
                    new Image(getClass().getResource("/playerImgs/moveL3.png").openStream(), 16*3, 16*3, false, false)
            };

            rightMoveSlides = new Image[] {
                    new Image(getClass().getResource("/playerImgs/moveR1.png").openStream(), 16*3, 16*3, false, false),
                    new Image(getClass().getResource("/playerImgs/moveR2.png").openStream(), 16*3, 16*3, false, false),
                    new Image(getClass().getResource("/playerImgs/moveR3.png").openStream(), 16*3, 16*3, false, false)
            };

            jumpL = new Image[] {
                    new Image(getClass().getResource("/playerImgs/jumpL.png").openStream(), 16*3, 16*3, false, false),
            };

            jumpR = new Image[] {
                    new Image(getClass().getResource("/playerImgs/jumpR.png").openStream(), 16*3, 16*3, false, false),
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
