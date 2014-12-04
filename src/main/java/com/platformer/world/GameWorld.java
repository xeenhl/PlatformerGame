package com.platformer.world;

import com.platformer.exceptions.WrongElementTypeException;
import com.platformer.model.DynamicElement;
import com.platformer.model.Element;
import com.platformer.model.Player;
import com.platformer.model.StaticElement;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleh on 04.12.2014.
 */
public class GameWorld {

    private final List<StaticElement> staticElements = new ArrayList<>();
    private final List<DynamicElement> dynamicElements = new ArrayList<>();
    private final World world = new World(new Vec2(0f, -10.0f));
    private final Player player;

    //World step parameters
    private final float TIME_STEP = 1/60.0f;
    private final int VELOCYTI_ITERATIONS = 8;
    private final int POSITION_ITERATIONS = 3;

    public GameWorld(Player p) {
        player = p;
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
        ground.setAsBox(1, 100);

        BodyDef bd = new BodyDef();
        bd.type = BodyType.STATIC;
        bd.position = new Vec2(0.0f,-10f);

    }
}
