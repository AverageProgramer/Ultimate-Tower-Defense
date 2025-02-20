package com.averagegames.ultimatetowerdefense;

import com.averagegames.ultimatetowerdefense.world.scenes.GameScene;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import static com.averagegames.ultimatetowerdefense.tools.Builder.loadBuild;

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
