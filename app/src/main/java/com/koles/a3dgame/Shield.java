package com.koles.a3dgame;

import com.koles.angin.GameObject3D;

public class Shield extends GameObject3D {
    static float SHIELD_RADIUS = 0.5f;

    public Shield(float x, float y, float z){
        super(x, y, z, SHIELD_RADIUS);

    }
}
