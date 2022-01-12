package com.koles.angin;

import com.koles.graphic.GLGraphics;
import com.koles.graphic.Graphics;
import com.koles.input.Input;
import com.koles.io.FileIO;
import com.koles.sound.Audio;

public interface Game {
    Input getInput();
    FileIO getFileIO();
    Graphics getGraphics();
    GLGraphics getGLGraphics();
    Audio getAudio();
    void setScreen(Screen screen);
    Screen getCurrentScreen();
    Screen getStartScreen();
}
