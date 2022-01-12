package com.koles.angin;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.koles.graphic.GLGraphics;
import com.koles.graphic.Graphics;
import com.koles.input.Input;
import com.koles.input.KolesInput;
import com.koles.io.FileIO;
import com.koles.io.KolesFileIO;
import com.koles.sound.Audio;
import com.koles.sound.KolesAudio;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public abstract class GLGame extends Activity implements Game, Renderer {

    enum GLGameState{
        Initialized,
        Running,
        Paused,
        Finished,
        Idle
    }

    GLSurfaceView glView;
    GLGraphics glGraphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    GLGameState state = GLGameState.Initialized;
    Object stateChanged = new Object();
    long startTime = System.nanoTime();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        glView = new GLSurfaceView(this);
        glView.setRenderer(this);
        setContentView(glView);

        glGraphics = new GLGraphics(glView);
        fileIO = new KolesFileIO(getAssets());
        audio = new KolesAudio(this);
        input = new KolesInput(this, glView, 1, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        glView.onResume();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glGraphics.setGl10(gl);

        synchronized (stateChanged){
            if(state == GLGameState.Initialized){
                screen = getStartScreen();
            }
            state = GLGameState.Running;
            screen.resume();
            startTime = System.nanoTime();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLGameState state = null;
        synchronized(stateChanged){
            state = this.state;
        }

        if(state == GLGameState.Running){
            float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
            startTime = System.nanoTime();

            screen.update(deltaTime);
            screen.present(deltaTime);
        }

        if(state == GLGameState.Paused){
            screen.pause();
            synchronized (stateChanged){
                this.state = GLGameState.Idle;
                stateChanged.notifyAll();
            }
        }

        if(state == GLGameState.Finished){
            screen.pause();
            screen.dispose();
            synchronized(stateChanged){
                this.state = GLGameState.Idle;
                stateChanged.notifyAll();
            }
        }

    }

    @Override
    protected void onPause() {
        synchronized(stateChanged){
            if(isFinishing()){
                state = GLGameState.Finished;
            }else{
                state = GLGameState.Paused;
            }
            while(true){
                try{
                    stateChanged.wait();
                    break;
                }catch(InterruptedException e){

                }
            }
        }
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        glView.onPause();
        super.onPause();
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
    public GLGraphics getGLGraphics() {
        return glGraphics;
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

    @Override
    public Graphics getGraphics() {
        System.out.println("Need call GLGraphics");
        return null;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}
