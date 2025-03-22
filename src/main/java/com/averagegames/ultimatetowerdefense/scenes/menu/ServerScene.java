package com.averagegames.ultimatetowerdefense.scenes.menu;

import com.averagegames.ultimatetowerdefense.scenes.Builder;
import com.averagegames.ultimatetowerdefense.util.development.Constant;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import static com.averagegames.ultimatetowerdefense.util.development.LogManager.LOGGER;

@SuppressWarnings("all")
public final class ServerScene extends Scene implements Builder {

    @Constant
    private static final double BUTTON_PREF_WIDTH;

    @Constant
    private static final int BUTTON_PREF_HEIGHT;

    public static final Rectangle2D SCREEN;

    public static final Button CREATE_BUTTON;

    public static final Button JOIN_BUTTON;

    public static final Button RETURN_BUTTON;

    private final Group parent;

    static {
        BUTTON_PREF_WIDTH = 500;
        BUTTON_PREF_HEIGHT = 75;

        SCREEN = Screen.getPrimary().getVisualBounds();

        CREATE_BUTTON = new Button("Create Server");
        JOIN_BUTTON = new Button("Join Server");

        RETURN_BUTTON = new Button("Go Back");
    }

    public ServerScene(@NotNull final Group root) {
        super(root);

        this.parent = root;
    }

    @Override
    public void build(@NotNull final Stage stage) {

        CREATE_BUTTON.setPrefSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);

        CREATE_BUTTON.setTranslateX((SCREEN.getWidth() / 2) - (CREATE_BUTTON.getPrefWidth() / 2));
        CREATE_BUTTON.setTranslateY((SCREEN.getHeight() / 2) - (CREATE_BUTTON.getPrefHeight() / 2) - 50);

        CREATE_BUTTON.setOnAction(event -> Platform.runLater(() -> {
            CreateScene createScene = new CreateScene(new Group());

            MANAGER.setNextScene(createScene);

            try {
                Builder.loadBuild(createScene, stage);
            } catch (Exception ex) {
                LOGGER.severe(STR."Exception \{ex} thrown when loading scene \{createScene}");
            }
        }));

        this.parent.getChildren().add(CREATE_BUTTON);

        JOIN_BUTTON.setPrefSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);

        JOIN_BUTTON.setTranslateX((SCREEN.getWidth() / 2) - (JOIN_BUTTON.getPrefWidth() / 2));
        JOIN_BUTTON.setTranslateY((SCREEN.getHeight() / 2) - (JOIN_BUTTON.getPrefHeight() / 2) + 50);

        JOIN_BUTTON.setOnAction(event -> Platform.runLater(() -> {
            JoinScene joinScene = new JoinScene(new Group());

            MANAGER.setNextScene(joinScene);

            try {
                Builder.loadBuild(joinScene, stage);
            } catch (Exception ex) {
                LOGGER.severe(STR."Exception \{ex} thrown when loading scene \{joinScene}");
            }
        }));

        this.parent.getChildren().add(JOIN_BUTTON);

        RETURN_BUTTON.setPrefSize(BUTTON_PREF_WIDTH / 5, 25);

        RETURN_BUTTON.setTranslateX(50);
        RETURN_BUTTON.setTranslateY(20);

        RETURN_BUTTON.setOnAction(event -> Platform.runLater(() -> {
            OpeningScene openingScene = new OpeningScene(new Group());

            MANAGER.setNextScene(openingScene);

            try {
                Builder.loadBuild(openingScene, stage);
            } catch (Exception ex) {
                LOGGER.severe(STR."Exception \{ex} thrown when loading scene \{openingScene}");
            }
        }));

        this.parent.getChildren().add(RETURN_BUTTON);
    }

    @Override
    public void post_build(@NotNull final Stage stage) {
        stage.setScene(this);

        stage.show();
    }
}
