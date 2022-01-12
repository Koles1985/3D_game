package com.koles.a3dgame.screens;

import com.koles.angin.GLGame;
import com.koles.angin.GLScreen;
import com.koles.content.Assets;
import com.koles.graphic.Camera2D;
import com.koles.graphic.SpriteBatcher;
import com.koles.input.TouchEvent;
import com.koles.math.OverlapTester;
import com.koles.math.Rectangle;
import com.koles.math.Vector2;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class MainMenuScreen extends GLScreen {
    Camera2D guiCam;
    SpriteBatcher batcher;
    Vector2 touchPos;
    Rectangle playBounds;
    Rectangle settingsBounds;

    public MainMenuScreen(GLGame glGame){
        super(glGame);

        guiCam = new Camera2D(glGraphics, 1280, 720);
        batcher = new SpriteBatcher(glGraphics, 10);
        touchPos = new Vector2();
        playBounds = new Rectangle();
        settingsBounds = new Rectangle();
    }

    @Override
    public void resume() {

    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> eventsList = glGame.getInput().getTouchEvents();
        int len = eventsList.size();
        for(int i = 0; i < len; i++){
            TouchEvent event = eventsList.get(i);
            if(event.getType() != TouchEvent.TOUCH_UP){
                continue;
            }
            guiCam.touchToWorld(touchPos.set(event.getX(), event.getY()));
            if(OverlapTester.pointInRectangle(playBounds, touchPos)){
                Assets.playSound(Assets.clickSound);
                glGame.setScreen(new GameScreen(glGame));
            }
            if(OverlapTester.pointInRectangle(settingsBounds, touchPos)){
                Assets.playSound(Assets.clickSound);
                glGame.setScreen(new SettingsScreen(glGame));
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGl10();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMatrices();
        gl.glEnable(GL10.GL_TEXTURE_2D);

        batcher.beginBatch(Assets.background);
        batcher.drawSprite(640, 360, 1280, 720, Assets.backgroundRegion);
        batcher.endBatch();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);


        batcher.beginBatch(Assets.items);
        batcher.drawSprite(, , , , Assets.logoRegion);
        batcher.drawSprite(, , , , Assets.menuRegion);
        batcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);
        gl.glDisable(GL10.GL_TEXTURE_2D);


    }

    @Override
    public void pause() {

    }

    @Override
    public void dispose() {

    }
}
