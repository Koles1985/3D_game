package com.koles.sound;

public interface Audio {
    Music newMusic(String fileName);

    Sound newSound(String fileName);
}
