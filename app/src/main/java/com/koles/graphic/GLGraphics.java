package com.koles.graphic;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.opengles.GL10;

public class GLGraphics {
    private GLSurfaceView glView;
    private GL10 gl;

    public GLGraphics(GLSurfaceView glView){
        this.glView = glView;
    }

    public void setGl10(GL10 gl) {
        this.gl = gl;
    }

    public GL10 getGl10() {
        return gl;
    }

    public int getWidth(){
        return glView.getWidth();
    }

    public int getHeight(){
        return glView.getHeight();
    }
}
