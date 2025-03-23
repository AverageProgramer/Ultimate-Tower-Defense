package com.averagegames.ultimatetowerdefense;

import static com.averagegames.ultimatetowerdefense.scenes.Builder.loadBuild;
import static com.averagegames.ultimatetowerdefense.scenes.menu.OpeningScene.SCREEN;
import static com.averagegames.ultimatetowerdefense.util.development.LogManager.LOGGER;
import static com.averagegames.ultimatetowerdefense.util.development.LogManager.enableLogging;

import com.averagegames.ultimatetowerdefense.scenes.menu.OpeningScene;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Main extends Application {

    @Override
    public void init() throws Exception {
        enableLogging(false);

        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            Thread.setDefaultUncaughtExceptionHandler(null);
        }

        LOGGER.info(STR."Application \{this} initalized.");
    }

    @Override
    public void start(@NotNull final Stage stage) throws Exception {
        stage.setTitle("Ultimate Tower Defense");

        stage.setMaximized(true);
        stage.setResizable(false);

        stage.setWidth(SCREEN.getWidth());
        stage.setHeight(SCREEN.getHeight());

        OpeningScene openingScene = new OpeningScene(new Group());
        loadBuild(openingScene, stage);

        LOGGER.info(STR."Scene \{openingScene} has been loaded successfully onto stage \{stage}.");

        LOGGER.info(STR."Application \{this} started.");
    }

    @Override
    public void stop() {
        LOGGER.info(STR."Application \{this} stopped.");

        System.exit(0);
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
