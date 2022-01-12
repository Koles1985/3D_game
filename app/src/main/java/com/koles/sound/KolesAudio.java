package com.koles.sound;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;

public class KolesAudio implements Audio{
    private AssetManager assetManager;
    private SoundPool soundPool;
    private AudioAttributes attributes;

    public KolesAudio(Activity activivty){
        activivty.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        this.assetManager = activivty.getAssets();
        this.attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        this.soundPool = new SoundPool.Builder()
                .setMaxStreams(20)
                .setAudioAttributes(attributes)
                .build();
    }

    @Override
    public Music newMusic(String fileName) {
        try{
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(fileName);
            return new KolesMusic(assetFileDescriptor);
        }catch (IOException e){
            Log.d("KolesAudio.newMusic", e.getMessage());
            return null;
        }
    }

    @Override
    public Sound newSound(String fileName) {
        try{
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(fileName);
            int soundId = soundPool.load(assetFileDescriptor, 0);
            return new KolesSound(soundPool, soundId);
        }catch(IOException e){
            Log.d("KolesAudio.newSound", e.getMessage());
            return null;
        }

    }
}
