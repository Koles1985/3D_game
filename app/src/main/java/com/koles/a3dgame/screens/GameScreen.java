package com.koles.a3dgame.screens;

import com.koles.a3dgame.Settings;
import com.koles.a3dgame.Ship;
import com.koles.a3dgame.World;
import com.koles.a3dgame.WorldRenderer;
import com.koles.angin.FPSCounter;
import com.koles.angin.GLGame;
import com.koles.angin.GLScreen;
import com.koles.content.Assets;
import com.koles.graphic.Camera2D;
import com.koles.graphic.SpriteBatcher;
import com.koles.input.TouchEvent;
import com.koles.math.OverlapTester;
import com.koles.math.Rectangle;
import com.koles.math.Vector2;
import com.koles.math.Vector3;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class GameScreen extends GLScreen {
    static final int GAME_RUNNING = 0;
    static final int GAME_PAUSED = 1;
    static final int GAME_OVER = 2;

    int state;
    Camera2D guiCam;
    Vector2 touchPoint;
    SpriteBatcher batch;
    World world;
    World.WorldListener listener;
    WorldRenderer renderer;
    Rectangle pauseBounds;
    Rectangle resumeBounds;
    Rectangle quitBounds;
    Rectangle leftBounds;
    Rectangle rightBounds;
    Rectangle shotBounds;
    int lastScore;
    int lastLives;
    int lastWaves;
    String scoreString;
    FPSCounter fpsCounter;

    public GameScreen(GLGame glGame){
        super(glGame);

        state = GAME_RUNNING;
        guiCam = new Camera2D(glGraphics, 1280, 720);
        touchPoint = new Vector2();
        batch = new SpriteBatcher(glGraphics, 300);
        world = new World();
        listener = new World.WorldListener() {
            @Override
            public void explosion() {
                Assets.playSound(Assets.explosionSound);
            }

            @Override
            public void shot() {
                Assets.playSound(Assets.shotSound);
            }
        };
        world.setWorldListener(listener);
        renderer = new WorldRenderer(glGraphics);
        pauseBounds = new Rectangle(1130, 570, 150, 150);
        resumeBounds = new Rectangle(450, 260, 380, 100);
        quitBounds = new Rectangle(450, 360, 380, 100);
        shotBounds = new Rectangle(1130, 0, 150, 150);
        leftBounds = new Rectangle(0, 0, 150,150);
        rightBounds = new Rectangle(980, 0, 150, 150);
        lastScore = 0;
        lastLives = world.ship.lives;
        lastWaves = world.waves;
        scoreString = "Lives: " + lastLives + " waves: " + lastWaves +
                " score: " + lastScore;
        fpsCounter = new FPSCounter();
    }


    public void update(float deltaTime){
       switch(state){
           case GAME_PAUSED:
               updatePaused();
               break;
           case GAME_RUNNING:
               updateRunning(deltaTime);
               break;
           case GAME_OVER:
               updateGameOver();
               break;
       }
    }

    private void updatePaused(){
        List<TouchEvent> events = game.getInput().getTouchEvents();
        int len = events.size();
        for(int i = 0; i < len; i++){
            TouchEvent event = events.get(i);
            if(event.getType() != TouchEvent.TOUCH_UP)
                continue;

            guiCam.touchToWorld(touchPoint.set(event.getX(), event.getY()));
            if(OverlapTester.pointInRectangle(resumeBounds, touchPoint)){
                Assets.playSound(Assets.clickSound);
                state = GAME_RUNNING;
            }

            if(OverlapTester.pointInRectangle(quitBounds, touchPoint)){
                Assets.playSound(Assets.clickSound);
                game.setScreen(new MainMenuScreen(glGame));
            }
        }
    }

    private void updateRunning(float deltaTime){
        List<TouchEvent> events = glGame.getInput().getTouchEvents();
        int len = events.size();
        for(int i = 0; i < len; i++){
            TouchEvent event = events.get(i);
            if(event.getType() != TouchEvent.TOUCH_UP)
                continue;

            guiCam.touchToWorld(touchPoint.set(event.getX(), event.getY()));
            if(OverlapTester.pointInRectangle(pauseBounds, touchPoint)){
                Assets.playSound(Assets.clickSound);
                state = GAME_PAUSED;
            }

            if(OverlapTester.pointInRectangle(shotBounds, touchPoint)){
                Assets.playSound(Assets.shotSound);
                world.shoot();
            }
        }
        world.update(deltaTime, calculateInputAcceleration());
        if(world.ship.lives != lastLives || world.score != lastScore ||
        world.waves != lastWaves){
            lastLives = world.ship.lives;
            lastScore = world.score;
            lastWaves = world.waves;
            scoreString = "Lives: " + lastLives + " waves: " + lastWaves +
                    " score:" + lastScore;
        }
        if(world.isGameOver()){
            state = GAME_OVER;
        }
    }

    private float calculateInputAcceleration(){
        float accelX = 0;
        if(Settings.touchEnabled){
            for(int i = 0; i < 2; i++){
                if(glGame.getInput().isTouchDown(i)) {
                    guiCam.touchToWorld(touchPoint.set(touchPoint.set(glGame.getInput().getTouchX(i),
                            glGame.getInput().getTouchY(i))));
                    if (OverlapTester.pointInRectangle(leftBounds, touchPoint)) {
                        accelX = -Ship.SHIP_VELOCITY / 5;
                    }
                    if (OverlapTester.pointInRectangle(rightBounds, touchPoint)) {
                        accelX = Ship.SHIP_VELOCITY / 5;
                    }
                }
            }
        }else{
            accelX = glGame.getInput().getAccelY();
        }
        return accelX;
    }

    private void updateGameOver(){
        List<TouchEvent> events = glGame.getInput().getTouchEvents();
        int len = events.size();
        for(int i = 0; i < len; i++){
            TouchEvent event = events.get(i);
            if(event.getType() != TouchEvent.TOUCH_UP)
                continue;

            Assets.playSound(Assets.clickSound);
            glGame.setScreen(new MainMenuScreen(glGame));
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGl10();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        guiCam.setViewportAndMatrices();
        gl.glEnable(GL10.GL_TEXTURE_2D);
        batch.beginBatch(Assets.background);
        batch.drawSprite(640, 360, 1280, 720, Assets.backgroundRegion);
        batch.endBatch();
        gl.glDisable(GL10.GL_TEXTURE_2D);

        renderer.render(world, deltaTime);

        switch (state){
            case GAME_RUNNING:
                presentRunning();
                break;
            case GAME_PAUSED:
                presentPaused();
                break;
            case GAME_OVER:
                presentGameOver();

        }
        fpsCounter.logFrame();
    }

    private void presentPaused(){
        GL10 gl = glGraphics.getGl10();
        guiCam.setViewportAndMatrices();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL10.GL_TEXTURE_2D);

        batch.beginBatch(Assets.items);
        Assets.font.drawText(batch, scoreString, 10, 600);
        batch.drawSprite(450, 260, 380, 200, Assets.pauseRegion);
        batch.endBatch();

        gl.glDisable(GL10.GL_TEXTURE_2D);
        gl.glDisable(GL10.GL_BLEND);
    }

    private void presentRunning(){
        GL10 gl = glGraphics.getGl10();
        guiCam.setViewportAndMatrices();
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batch.beginBatch(Assets.items);
        batch.drawSprite(1130, 570, 150, 150, Assets.pauseButtonRegion);
        Assets.font.drawText(batch, scoreString, 10, 700);
        batch.drawSprite(1130, 0, 150, 150, Assets.fireRegion);
        batch.endBatch();

        if(Settings.touchEnabled){
            batch.beginBatch(Assets.leftTexture);
            batch.drawSprite(0, 0, 150,150, Assets.leftRegion);
            batch.drawSprite(980, 0, -150, 150, Assets.leftRegion);
            batch.endBatch();
        }

        gl.glDisable(GL10.GL_BLEND);
        gl.glDisable(GL10.GL_TEXTURE_2D);

    }


    private void presentGameOver(){
        GL10 gl = glGraphics.getGl10();
        guiCam.setViewportAndMatrices();
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batch.beginBatch(Assets.items);
        batch.drawSprite(370, 310, 540, 100, Assets.gameOverRegion);
        Assets.font.drawText(batch, scoreString, 10, 600);
        batch.endBatch();

        gl.glDisable(GL10.GL_BLEND);
        gl.glDisable(GL10.GL_TEXTURE_2D);
    }
    @Override
    public void pause() {
        state = GAME_PAUSED;
    }

    @Override
    public void dispose() {

    }
}
