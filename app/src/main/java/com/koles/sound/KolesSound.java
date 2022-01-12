package com.koles.sound;

import android.media.SoundPool;

public class KolesSound implements Sound{
    int soundId;
    SoundPool soundPool;

    public KolesSound(SoundPool soundPool, int soundId){
        this.soundId = soundId;
        this.soundPool = soundPool;
    }

    @Override
    public void play(float volume) {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    @Override
    public void dispose() {
        soundPool.unload(soundId);
    }
}
