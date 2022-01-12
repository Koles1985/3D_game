package com.koles.input;

import java.util.List;

public interface Input {
    boolean isKeyPressed(int keyCode);
    boolean isTouchDown(int pointer);
    int getTouchX(int pointer);
    int getTouchY(int pointer);
    float getAccelX();
    float getAccelY();
    float getAccelZ();
    List<KeyEvent> getKeyEvents();
    List<TouchEvent> getTouchEvents();
}
