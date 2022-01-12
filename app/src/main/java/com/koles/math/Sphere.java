package com.koles.math;

public class Sphere {

    public Vector3 center = new Vector3();
    public float radius;

    public Sphere(float x, float y, float z, float radius){
        this.center.set(x, y, z);
        this.radius = radius;
    }

    public Sphere(Vector3 center, float radius){
        this.center.set(center);
        this.radius = radius;
    }
}
