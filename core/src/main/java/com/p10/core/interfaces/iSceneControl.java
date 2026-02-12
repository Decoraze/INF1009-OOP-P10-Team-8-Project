package com.p10.core.interfaces;

import com.p10.core.scene.Scene;

public interface iSceneControl {

    void switchScene(String sceneName);

    Scene getCurrentScene();

    String getCurrentSceneName();
}