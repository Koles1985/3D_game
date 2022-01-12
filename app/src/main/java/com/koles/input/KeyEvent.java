package com.koles.input;

public class KeyEvent {
    public static int KEY_UP = 0;
    public static int KEY_DOWN = 1;

    private int keyCode;
    private int type;
    private char keyChar;

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public char getKeyChar() {
        return keyChar;
    }

    public void setKeyChar(char keyChar) {
        this.keyChar = keyChar;
    }
}
