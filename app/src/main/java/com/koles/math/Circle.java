package com.koles.math;

public class Circle {
    public final Vector2 center = new Vector2();
    public float radius;

    public Circle(float x, float y, float radius){
        this.radius = radius;
        this.center.set(x, y);
    }

    public boolean overlapCircle(Circle c1, Circle c2){
        float distance = c1.center.distSquared(c2.center);
        float radiusSum = c1.radius + c2.radius;
        return distance <= radiusSum * radiusSum;
    }


}
