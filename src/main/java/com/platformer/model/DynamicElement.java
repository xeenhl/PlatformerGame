package com.platformer.model;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

/**
 * Created by Oleh on 04.12.2014.
 */
public class DynamicElement extends Element {
    public DynamicElement(World world) {
        super(world);
        this.getBody().m_type = BodyType.DYNAMIC;
    }
}
