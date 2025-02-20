package com.averagegames.ultimatetowerdefense;

import com.averagegames.ultimatetowerdefense.game.scenes.TestScene;
import javafx.application.Application;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import static com.averagegames.ultimatetowerdefense.tools.util.Builder.loadBuild;
import static com.averagegames.ultimatetowerdefense.control.LogController.enableLogging;

public class Main extends Application {
    @Override
    public void start(@SuppressWarnings("exports") @NotNull final Stage stage) throws Exception {
        enableLogging(true);

        loadBuild(new TestScene(), stage);
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
