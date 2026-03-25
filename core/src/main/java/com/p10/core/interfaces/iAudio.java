package com.p10.core.interfaces;

public interface iAudio {
    void playSound(String soundName);

    void playMusic(String musicName);

    void stopMusic();

    void setMusicVolume(float volume);

}