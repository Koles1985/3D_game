package com.koles.content;

import android.content.res.AssetFileDescriptor;

import com.koles.a3dgame.Settings;
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
    public static Texture leftTexture;
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

    //public static TextureRegion explosionTexture;
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
        backgroundRegion = new TextureRegion(background, 0, 0, 1280, 720);
        mainMenu = new Texture(glGame, "texture/main menu screen.png", true);
        mainMenuRegion = new TextureRegion(mainMenu, 0, 0, 1280, 720);
        leftTexture = new Texture(glGame, "texture/left.png", true);
        items = new Texture(glGame, "texture/region.png");
        logoRegion = new TextureRegion(items, 0, 620, 600, 100);
        menuRegion = new TextureRegion(items,380, 480, 480, 200 );
        gameOverRegion = new TextureRegion(items,0, 830, 540, 100);
        pauseRegion = new TextureRegion(items,0, 480, 380, 200);
        settingsRegion = new TextureRegion(items,380, 580, 860,680);
        touchRegion = new TextureRegion(items,0,680, 150, 150);
        accelRegion = new TextureRegion(items,300, 680, 150, 150);
        touchEnabledRegion = new TextureRegion(items,150, 680, 150, 150);
        accelEnabledRegion = new TextureRegion(items,450, 680, 150, 150);
        soundRegion = new TextureRegion(items,1130, 0, 150, 150);
        soundEnabledRegion = new TextureRegion(items,980, 0, 150, 150);
        leftRegion = new TextureRegion(leftTexture,0,0, 32, 32);
        rightRegion = new TextureRegion(leftTexture, 0, 0, 32, 32);
        fireRegion = new TextureRegion(items,600, 680, 150, 150);
        pauseButtonRegion = new TextureRegion(items,750, 68, 150, 150);
        font = new Font(items, 0, 0, 16, 58, 60);

        //explosionTexture = new TextureRegion(items,960, 318, 512, 512);
        TextureRegion[] keyFrames = new TextureRegion[16];
        int frame = 0;
        for(int y = 318; y < 830; y += 128){
            for(int x = 960; x < 1472; x += 128){
                keyFrames[frame++] = new TextureRegion(items, x, y, 128, 128);
            }
        }
        explosionAnim = new Animation(0.1f, keyFrames);
        shipTexture = new Texture(glGame, "data/ship.png", true);
        shipModel = ObjLoader.load(glGame, "data/ship.obj");
        invaderTexture = new Texture(glGame, "data/invader.png", true);
        invaderModel = ObjLoader.load(glGame, "data/invader.obj");
        shotModel = ObjLoader.load(glGame, "data/shot.obj");
        shielModel = ObjLoader.load(glGame, "data/block.obj");

        music = glGame.getAudio().newMusic("music/song.mp3");
        music.setLooping(true);
        music.setVolume(0.5f);
        if(Settings.soundEnabled)
            music.play();

        clickSound = glGame.getAudio().newSound("music/alein short.wav");
        explosionSound = glGame.getAudio().newSound("music/ship destroy.wav");
        shotSound = glGame.getAudio().newSound("music/ship short.wav");
    }

    public static void reload(){
        mainMenu.reload();
        background.reload();
        items.reload();
        leftTexture.reload();
        shipTexture.reload();
        invaderTexture.reload();
        if(Settings.soundEnabled)
            music.play();
    }

    public static void playSound(Sound sound){
        if(Settings.soundEnabled)
            sound.play(1);
    }
}
