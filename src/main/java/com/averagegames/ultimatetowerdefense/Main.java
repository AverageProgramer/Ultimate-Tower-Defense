package com.averagegames.ultimatetowerdefense;

import static com.averagegames.ultimatetowerdefense.scenes.OpeningScene.SCREEN;
import static com.averagegames.ultimatetowerdefense.util.LogManager.LOGGER;

import com.averagegames.ultimatetowerdefense.scenes.tools.SceneBuilder;
import com.averagegames.ultimatetowerdefense.scenes.OpeningScene;
import com.averagegames.ultimatetowerdefense.util.LogManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;

import org.jetbrains.annotations.NotNull;

public class Main extends Application {

    @Override
    public void init() {
        LogManager.enableLogging(false);

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
        SceneBuilder.loadBuild(openingScene, stage);

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
