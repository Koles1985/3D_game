package com.koles.content;

import android.content.res.AssetFileDescriptor;

import com.koles.angin.GLGame;
import com.koles.graphic.Animation;
import com.koles.graphic.Font;
import com.koles.graphic.ObjLoader;
import com.koles.graphic.Texture;
import com.koles.graphic.TextureRegion;
import com.koles.graphic.Vertices3;
import com.koles.sound.KolesMusic;
import com.koles.sound.Music;
import com.koles.sound.Sound;

public class Assets {
    public static Texture background;
    public static TextureRegion backgroundRegion;
    public static Texture mainMenu;
    public static TextureRegion mainMenuRegion;
    public static Texture items;
    public static TextureRegion logoRegion;
    public static TextureRegion menuRegion;
    public static TextureRegion gameOverRegion;
    public static TextureRegion pauseRegion;
    public static TextureRegion settingsRegion;
    public static TextureRegion touchRegion;
    public static TextureRegion accelRegion;
    public static TextureRegion touchEnabledRegion;
    public static TextureRegion accelEnabledRegion;
    public static TextureRegion soundRegion;
    public static TextureRegion soundEnabledRegion;
    public static TextureRegion leftRegion;
    public static TextureRegion rightRegion;
    public static TextureRegion fireRegion;
    public static TextureRegion pauseButtonRegion;
    public static Font font;

    public static TextureRegion explosionTexture;
    public static Animation explosionAnim;
    public static Vertices3 shipModel;
    public static Texture shipTexture;
    public static Vertices3 invaderModel;
    public static Texture invaderTexture;
    public static Vertices3 shotModel;
    public static Vertices3 shielModel;

    public static Music music;
    public static Sound clickSound;
    public static Sound explosionSound;
    public static Sound shotSound;

    public static void load(GLGame glGame){
        background = new Texture(glGame, "texture/earth.png", true);
        backgroundRegion new TextureRegion(background, 0, 0, 1280, 720);
        mainMenu = new Texture(glGame, "texture/main menu screen.png", true);
        mainMenuRegion = new TextureRegion(mainMenu, 0, 0, 1280, 720);
        items = new Texture(glGame, "texture/region.png");
        logoRegion = new TextureRegion(items, 0, 620, 600, 100);
        menuRegion = new TextureRegion(items, );
        gameOverRegion = new TextureRegion(items,);
        pauseRegion = new TextureRegion(items,);
        settingsRegion = new TextureRegion(items,);
        touchRegion = new TextureRegion(items,);
        accelRegion = new TextureRegion(items,);
        touchEnabledRegion = new TextureRegion(items,);
        accelEnabledRegion = new TextureRegion(items,);
        soundRegion = new TextureRegion(items,);
        soundEnabledRegion = new TextureRegion(items,);
        leftRegion = new TextureRegion(items,);
        rightRegion = new TextureRegion(items,);
        fireRegion = new TextureRegion(items,);
        pauseButtonRegion = new TextureRegion(items,);
        font = new Font(items, );

        explosionTexture = new TextureRegion(items,);
        explosionAnim = new Animation(0.1f, explosionTexture);
        shipModel = ObjLoader.load(glGame, "data/ship.obj");
        shipTexture = new Texture(glGame, "data/ship.png", true);
        invaderModel = ObjLoader.load(glGame, "data/invader.obj");
        invaderTexture = new Texture(glGame, "data/invader.png", true);
        shotModel = ObjLoader.load(glGame, "data/shot.obj");
        shielModel = ObjLoader.load(glGame, "data/block.obj");

        music = glGame.getAudio().newMusic("music/song.mp3");
        clickSound = glGame.getAudio().newSound("music/alein short.wav");
        explosionSound = glGame.getAudio().newSound("music/ship destroy.wav");
        shotSound = glGame.getAudio().newSound("music/ship short.wav");




    }
}
