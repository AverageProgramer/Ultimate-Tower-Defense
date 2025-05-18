package com.averagegames.ultimatetowerdefense.scenes.tools;

import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public final class SceneManager {
    private Scene previousScene;
    private Scene currentScene;
    private Scene nextScene;
}
