package com.koles.content;

import com.koles.angin.GLGame;
import com.koles.graphic.Animation;
import com.koles.graphic.Font;
import com.koles.graphic.Texture;
import com.koles.graphic.TextureRegion;
import com.koles.graphic.Vertices3;
import com.koles.sound.Music;
import com.koles.sound.Sound;

public class Assets {
    public static Texture background;
    public static TextureRegion backgroundRegion;
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

    public static Texture explosionTexture;
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

    }
}
