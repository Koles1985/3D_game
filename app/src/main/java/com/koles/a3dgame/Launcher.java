package com.koles.a3dgame;

import com.koles.a3dgame.screens.MainMenuScreen;
import com.koles.angin.GLGame;
import com.koles.angin.Screen;
import com.koles.content.Assets;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Launcher extends GLGame {
    boolean firstTimeCreated = true;

    @Override
    public Screen getStartScreen() {
        return new MainMenuScreen(this);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
        if(firstTimeCreated){
            Settings.load(getFileIO());
            Assets.load(this);
            firstTimeCreated = false;
        }else{
            Assets.reload();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(Settings.soundEnabled)
            Assets.music.pause();
    }
}