package com.platformer.model;

import javafx.scene.Node;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

/**
 * Created by Oleh on 03.12.2014.
 */
public abstract class Element {

    private Body body;
    private final World world;

    public Element(World world) {
        this.world = world;
    }

    public void setNode(Node node) {
        body.setUserData(node);
    }

    public Node getNode() {

        return (Node)body.getUserData();

    }

    public Body getBody() { return body; }

    public void setBody(Body body) { this.body = body; }

    public World getWorld() {
        return world;
    }
}
