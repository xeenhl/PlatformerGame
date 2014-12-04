package com.platformer.world;

import com.platformer.exceptions.WrongElementTypeException;
import com.platformer.model.DynamicElement;
import com.platformer.model.Element;
import com.platformer.model.Player;
import com.platformer.model.StaticElement;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleh on 04.12.2014.
 */
public class GameWorld {

    private final List<StaticElement> staticElements = new ArrayList<>();
    private final List<DynamicElement> dynamicElements = new ArrayList<>();
    private final World world = new World(new Vec2(0f, -10.0f));
    private Player player;

    //World step parameters
    private final float TIME_STEP = 1/60.0f;
    private final int VELOCYTI_ITERATIONS = 6;
    private final int POSITION_ITERATIONS = 3;

    public GameWorld() {
        initWorld();
    }


    public World getWorld() {
        return world;
    }

    public void addElement(Element elm) throws WrongElementTypeException {
        if ( elm instanceof DynamicElement )
            dynamicElements.add((DynamicElement)elm);
        else if( elm instanceof StaticElement )
            staticElements.add((StaticElement)elm);
        else
            throw new WrongElementTypeException(elm);
    }

    public void removeElement(Element elm) throws WrongElementTypeException {
        if ( elm instanceof DynamicElement )
            dynamicElements.remove((DynamicElement) elm);
        else if( elm instanceof StaticElement )
            staticElements.remove((StaticElement) elm);
        else
            throw new WrongElementTypeException(elm);
    }

    public void addPlayer(Player p) {
        player = p;
    }

    public List getStaticelements() {
        return staticElements;
    }

    public List getDynamicElement() {
        return dynamicElements;
    }

    public Player getPlayer() {
        return player;
    }

    public void step() {
        world.step(TIME_STEP, VELOCYTI_ITERATIONS, POSITION_ITERATIONS);
    }

    private void initWorld() {
        addGround();
    }

    private void addGround() {
        PolygonShape ground = new PolygonShape();
        ground.setAsBox(100, 10);

        BodyDef bd = new BodyDef();
        bd.type = BodyType.STATIC;
        bd.position = new Vec2(0.0f, -10.0f);

        FixtureDef fd = new FixtureDef();
        fd.shape = ground;

        world.createBody(bd).createFixture(fd);

    }
}
