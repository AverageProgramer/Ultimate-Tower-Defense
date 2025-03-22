package com.averagegames.ultimatetowerdefense.scenes.menu;

import com.averagegames.ultimatetowerdefense.maps.dev.TestMap;
import com.averagegames.ultimatetowerdefense.scenes.Builder;
import com.averagegames.ultimatetowerdefense.scenes.game.GameScene;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Constant;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import static com.averagegames.ultimatetowerdefense.util.development.LogManager.LOGGER;

@SuppressWarnings("all")
public final class OpeningScene extends Scene implements Builder {

    @Constant
    private static final double BUTTON_PREF_WIDTH;

    @Constant
    private static final int BUTTON_PREF_HEIGHT;

    @Constant
    private final static int TITLE_FONT_SIZE = 100;

    public static final Rectangle2D SCREEN;

    public static final AudioPlayer GLOBAL_PLAYER;

    public static final Text TITLE_TEXT;

    public static final Button SINGLEPLAYER_BUTTON;

    public static final Button MULTIPLAYER_BUTTON;

    private final Group parent;

    static {
        BUTTON_PREF_WIDTH = 650;
        BUTTON_PREF_HEIGHT = 75;

        SCREEN = Screen.getPrimary().getVisualBounds();

        GLOBAL_PLAYER = new AudioPlayer();

        TITLE_TEXT = new Text("Ultimate Tower Defense");

        SINGLEPLAYER_BUTTON = new Button("Singleplayer");
        MULTIPLAYER_BUTTON = new Button("Multiplayer");
    }

    public OpeningScene(@NotNull final Group root) {
        super(root);

        this.parent = root;
    }

    @Override
    public void build(@NotNull final Stage stage) throws Exception {
        TITLE_TEXT.setFont(Font.font("Courier New", TITLE_FONT_SIZE));
        TITLE_TEXT.setTextAlignment(TextAlignment.CENTER);

        TITLE_TEXT.setX((SCREEN.getWidth() / 2) - (TITLE_TEXT.getLayoutBounds().getWidth() / 2));
        TITLE_TEXT.setY(((SCREEN.getHeight() / 2) - (BUTTON_PREF_HEIGHT / 2) - 50) / 3);

        this.parent.getChildren().add(TITLE_TEXT);

        SINGLEPLAYER_BUTTON.setPrefSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);

        SINGLEPLAYER_BUTTON.setTranslateX((SCREEN.getWidth() / 2) - (SINGLEPLAYER_BUTTON.getPrefWidth() / 2));
        SINGLEPLAYER_BUTTON.setTranslateY((SCREEN.getHeight() / 2) - (SINGLEPLAYER_BUTTON.getPrefHeight() / 2) - 50);

        SINGLEPLAYER_BUTTON.setOnAction(event -> Platform.runLater(() -> {
            GameScene gameScene = new GameScene(new Group(), new TestMap());

            MANAGER.setNextScene(gameScene);

            try {
                Builder.loadBuild(gameScene, stage);
            } catch (Exception ex) {
                LOGGER.severe(STR."Exception \{ex} thrown when loading scene \{gameScene}");
            }
        }));

        this.parent.getChildren().add(SINGLEPLAYER_BUTTON);

        MULTIPLAYER_BUTTON.setPrefSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);

        MULTIPLAYER_BUTTON.setTranslateX((SCREEN.getWidth() / 2) - (MULTIPLAYER_BUTTON.getPrefWidth() / 2));
        MULTIPLAYER_BUTTON.setTranslateY((SCREEN.getHeight() / 2) - (MULTIPLAYER_BUTTON.getPrefHeight() / 2) + 50);

        MULTIPLAYER_BUTTON.setOnAction(event -> Platform.runLater(() -> {
            ServerScene lobbyScene = new ServerScene(new Group());

            MANAGER.setNextScene(lobbyScene);

            try {
                Builder.loadBuild(lobbyScene, stage);
            } catch (Exception ex) {
                LOGGER.severe(STR."Exception \{ex} thrown when loading scene \{lobbyScene}");
            }
        }));

        this.parent.getChildren().add(MULTIPLAYER_BUTTON);

        if (MANAGER.getNextScene() != this) {
            GLOBAL_PLAYER.setPathname("src/main/resources/com/averagegames/ultimatetowerdefense/audio/music/Daybreak OST - SCP Roleplay.wav");
            GLOBAL_PLAYER.loop(AudioPlayer.INDEFINITELY);
        }

        Platform.runLater(() -> stage.sceneProperty().addListener((observableValue, scene, t1) -> {
            if (t1 instanceof GameScene) {
                GLOBAL_PLAYER.stop();
            }
        }));
    }

    @Override
    public void post_build(@NotNull final Stage stage) throws Exception {
        stage.setScene(this);
        stage.show();
    }
}
