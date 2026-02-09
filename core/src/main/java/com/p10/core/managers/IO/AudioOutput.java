package com.p10.core.managers.IO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import java.util.HashMap;
import java.util.Map;

public class AudioOutput {

    private final Map<String, Sound> soundMap;
    private final Map<String, Music> musicMap;
    private Music currentMusic;

    public AudioOutput() {
        soundMap = new HashMap<>();
        musicMap = new HashMap<>();
        musicMap.put("bgm", Gdx.audio.newMusic(Gdx.files.internal("bgm.ogg")));
        // credits http://opengameart.org
        soundMap.put("jump", Gdx.audio.newSound(Gdx.files.internal("jump.wav")));
        // sound for jumping
    }

    public void playSound(String name) {
        Sound sound = soundMap.get(name);
        if (sound != null) sound.play();
    }

    public void playMusic(String name) {
        stopMusic();
        Music music = musicMap.get(name);
        // to loop the music
        if (music != null) {
            currentMusic = music;
            currentMusic.setLooping(true);
            currentMusic.play();
        }
    }

    public void stopMusic() {
        if (currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.stop();
        }
    }


    public void dispose() {
        for (Sound s : soundMap.values()) s.dispose();
        for (Music m : musicMap.values()) m.dispose();
    }
}
