package com.koles.input;

public class TouchEvent {
    public static final int TOUCH_UP = 0;
    public static final int TOUCH_DOWN = 1;
    public static final int TOUCH_MOVE = 2;

    private int type;
    private int x;
    private int y;
    private int pointer;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }
}
