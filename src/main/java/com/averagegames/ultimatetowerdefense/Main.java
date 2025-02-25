package com.averagegames.ultimatetowerdefense;

import static com.averagegames.ultimatetowerdefense.Manager.LOGGER;
import static com.averagegames.ultimatetowerdefense.scenes.SceneBuilder.loadBuild;

import com.averagegames.ultimatetowerdefense.scenes.GameScene;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;

import org.jetbrains.annotations.NotNull;

public class Main extends Application {
    @Override
    public void start(@SuppressWarnings("exports") @NotNull final Stage stage) throws Exception {
        loadBuild(new GameScene(new Group()), stage);
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
