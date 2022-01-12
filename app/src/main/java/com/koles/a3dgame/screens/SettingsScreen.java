package com.koles.a3dgame.screens;

import com.koles.a3dgame.Settings;
import com.koles.angin.GLScreen;
import com.koles.angin.Game;
import com.koles.content.Assets;
import com.koles.graphic.Camera2D;
import com.koles.graphic.SpriteBatcher;
import com.koles.input.TouchEvent;
import com.koles.math.OverlapTester;
import com.koles.math.Rectangle;
import com.koles.math.Vector2;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class SettingsScreen extends GLScreen {
    Camera2D guiCam;
    SpriteBatcher batcher;
    Vector2 touchPoint;
    Rectangle touchBounds;
    Rectangle accelBounds;
    Rectangle backBounds;
    Rectangle soundBounds;

    public SettingsScreen(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics, 1280, 720);
        batcher = new SpriteBatcher(glGraphics, 10);
        touchBounds = new Rectangle();
        accelBounds = new Rectangle();
        soundBounds = new Rectangle();
        backBounds = new Rectangle();
    }

    @Override
    public void resume() {

    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> eventList = glGame.getInput().getTouchEvents();
        int len = eventList.size();

        for(int i = 0; i < len; i++){
            TouchEvent event = eventList.get(i);
            if(event.getType() != TouchEvent.TOUCH_UP)
                continue;

            guiCam.touchToWorld(touchPoint.set(event.getX(), event.getY()));
            if(OverlapTester.pointInRectangle(touchBounds, touchPoint)){
                Assets.playSound(Assets.clickSound);
                Settings.touchEnabled = true;
                Settings.save(glGame.getFileIO());
            }

            if(OverlapTester.pointInRectangle(accelBounds, touchPoint)){
                Assets.playSound(Assets.clickSound);
                Settings.touchEnabled = false;
                Settings.save(glGame.getFileIO());
            }
            if(OverlapTester.pointInRectangle(soundBounds, touchPoint)){
                Assets.playSound(Assets.clickSound);
                Settings.soundEnabled = !Settings.soundEnabled;
                if(Settings.soundEnabled){
                    Assets.music.play();
                }else{
                    Assets.music.pause();
                }
                Settings.save(glGame.getFileIO());
            }

            if(OverlapTester.pointInRectangle(backBounds, touchPoint)){
                Assets.playSound(Assets.clickSound);
                glGame.setScreen(new MainMenuScreen(glGame));
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
        batcher.drawSprite(640, 360, glGraphics.getWidth(), glGraphics.getHeight(),
                Assets.backgroundRegion);
        batcher.endBatch();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Assets.items);
        batcher.drawSprite(, , , ,
                Settings.touchEnabled ? Assets.touchRegion : Assets.touchEnabledRegion);
        batcher.drawSprite(, , , ,
                Settings.touchEnabled ? Assets.accelRegion : Assets.accelEnabledRegion);
        batcher.drawSprite(, , , ,
                Settings.soundEnabled ? Assets.soundRegion : Assets.soundEnabledRegion);

        batcher.drawSprite(, , , , Assets.leftRegion);
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
