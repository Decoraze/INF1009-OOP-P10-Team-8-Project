package com.p10.core.interfaces;

//these 3 methods were from iInput and were split to ensure SRP and ISP
public interface iAudio {
    void playSound(String soundName);

    void playMusic(String musicName);

    void stopMusic();
}