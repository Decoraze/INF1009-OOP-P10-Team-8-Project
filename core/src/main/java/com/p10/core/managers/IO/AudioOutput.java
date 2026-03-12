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
    }
    
    // Moved these 2 lines of codes down from constructor to avoid Gdx.files.internal to point to null
    // with all managers initialized in the ApplicationCore constructor now
    public void audioFiles() {
        // Load BGM — try-catch so game runs even without audio files
        try { musicMap.put("bgm", Gdx.audio.newMusic(Gdx.files.internal("bgm.ogg"))); }
        catch (Exception e) { System.out.println("[Audio] bgm.ogg not found — skipping"); }
        // Game-specific sounds
        try { soundMap.put("shoot", Gdx.audio.newSound(Gdx.files.internal("shoot.wav"))); }
        catch (Exception e) { System.out.println("[Audio] shoot.wav not found — skipping"); }
        try { soundMap.put("kill", Gdx.audio.newSound(Gdx.files.internal("kill.wav"))); }
        catch (Exception e) { System.out.println("[Audio] kill.wav not found — skipping"); }
        try { soundMap.put("wave_start", Gdx.audio.newSound(Gdx.files.internal("wave_start.wav"))); }
        catch (Exception e) { System.out.println("[Audio] wave_start.wav not found — skipping"); }
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
