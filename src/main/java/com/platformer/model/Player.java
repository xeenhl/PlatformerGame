package com.platformer.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * Created by Oleh on 04.12.2014.
 */
public class Player extends DynamicElement {

    private final Image stendLeft = new Image("resources/playerImgs/standLeft.png");

    public Player(World world) {
        super(world);

    }

    private void initPlayer() {

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(1.6f, 1.3f);

        FixtureDef fd = new FixtureDef();
        fd.shape = ps;

        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;

        getWorld().createBody(bd).createFixture(fd);

        this.setNode(new ImageView(stendLeft));
    }


}
