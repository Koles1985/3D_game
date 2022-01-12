package com.koles.angin;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.koles.graphic.Graphics;
import com.koles.graphic.KolesGraphics;
import com.koles.input.Input;
import com.koles.input.KolesInput;
import com.koles.io.FileIO;
import com.koles.io.KolesFileIO;
import com.koles.sound.Audio;
import com.koles.sound.KolesAudio;

public abstract class KolesGame extends Activity implements Game{
    KolesFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        boolean isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        int frameBufferWidth = isLandscape ? 480 : 320;
        int frameBufferHeight = isLandscape ? 320 : 480;

        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight,
                Bitmap.Config.ARGB_4444);

        float scaleX = (float)frameBufferWidth / displayMetrics.widthPixels;
        float scaleY = (float)frameBufferHeight / displayMetrics.heightPixels;

        renderView = new KolesFastRenderView(this, frameBuffer);
        graphics = new KolesGraphics(getAssets(), frameBuffer);
        fileIO = new KolesFileIO(getAssets());
        audio = new KolesAudio(this);
        input = new KolesInput(this, renderView, scaleX, scaleY);
        screen = getStartScreen();
        setContentView(renderView);
    }

    @Override
    public void onResume() {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        renderView.pause();
        screen.pause();

        if(isFinishing()){
            screen.dispose();
        }
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if(screen == null){
            throw new IllegalArgumentException("Screen must not be null");
        }

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    @Override
    public Screen getCurrentScreen() {
        return screen;
    }
    
}
