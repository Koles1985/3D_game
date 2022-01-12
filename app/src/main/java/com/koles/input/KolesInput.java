package com.koles.input;

import android.content.Context;
import android.view.View;

import java.util.List;

public class KolesInput implements Input{
    AccelerometrHandler accelerometer;
    KeyboardHandler keyHandler;
    MultiTouchHandler touchHandler;

    public KolesInput(Context context, View view, float scaleX, float scaleY){
        accelerometer = new AccelerometrHandler(context);
        keyHandler = new KeyboardHandler(view);
        touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
    }

    @Override
    public boolean isKeyPressed(int keyCode) {
        return keyHandler.isKeyPressed(keyCode);
    }

    @Override
    public boolean isTouchDown(int pointer) {
        return touchHandler.isTouchDown(pointer);
    }

    @Override
    public int getTouchX(int pointer) {
        return touchHandler.getTouchX(pointer);
    }

    @Override
    public int getTouchY(int pointer) {
        return touchHandler.getTouchY(pointer);
    }

    @Override
    public float getAccelX() {
        return accelerometer.getAccelX();
    }

    @Override
    public float getAccelY() {
        return accelerometer.getAccelY();
    }

    @Override
    public float getAccelZ() {
        return accelerometer.getAccelZ();
    }

    @Override
    public List<KeyEvent> getKeyEvents() {
        return keyHandler.getKeyEvents();
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        return touchHandler.getTouchEvents();
    }
}
