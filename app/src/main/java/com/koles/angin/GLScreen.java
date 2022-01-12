package com.koles.angin;

import com.koles.graphic.GLGraphics;

public abstract class GLScreen extends Screen {
    protected final GLGraphics glGraphics;
    protected final GLGame glGame;
     public GLScreen(Game game){
         super(game);
         glGame = (GLGame)game;
         glGraphics = ((GLGame)game).getGLGraphics();
     }

     public GLGraphics getGLGraphics(){
         return this.glGraphics;
     }

     public  GLGame getGlGame(){
         return  this.glGame;
     }
}
