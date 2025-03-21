package com.averagegames.ultimatetowerdefense;

import static com.averagegames.ultimatetowerdefense.scenes.Builder.loadBuild;
import static com.averagegames.ultimatetowerdefense.util.development.LogManager.LOGGER;

import com.averagegames.ultimatetowerdefense.maps.dev.TestMap;
import com.averagegames.ultimatetowerdefense.scenes.GameScene;

import com.averagegames.ultimatetowerdefense.scenes.OpeningScene;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;

import org.jetbrains.annotations.NotNull;

public class Main extends Application {

    @Override
    public void init() throws Exception {
        LOGGER.info(STR."Application \{this} initalized.");
    }

    @Override
    public void start(@NotNull final Stage stage) throws Exception {
        GameScene scene = new GameScene(new Group(), new TestMap());

        OpeningScene start = new OpeningScene(new Group());

        loadBuild(start, stage);

        LOGGER.info(STR."Scene \{scene} has been loaded successfully onto stage \{stage}.");

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
