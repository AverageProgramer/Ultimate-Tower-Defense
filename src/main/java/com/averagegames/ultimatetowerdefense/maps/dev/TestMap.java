package com.averagegames.ultimatetowerdefense.maps.dev;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.maps.*;
import com.averagegames.ultimatetowerdefense.scenes.GameScene;
import com.averagegames.ultimatetowerdefense.util.development.Constant;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.jetbrains.annotations.NotNull;

public final class TestMap implements Map {

    /**
     * The total length of the {@link TestMap}'s {@link Path}.
     */
    @Constant
    private static final int PATH_LENGTH = 1350;

    /**
     * The total height of the {@link TestMap}'s {@link Path}.
     */
    @Constant
    private static final int PATH_HEIGHT = 400;

    /**
     * The time delay in millisecond between {@link Enemy} {@code spawns}.
     */
    @Constant
    private static final int ENEMY_SPAWN_DELAY = 1500;

    /**
     * The {@link TestMap}'s uniquely implemented pre-load action.
     * @param scene a {@link Scene} to use.
     * @since Ultimate Tower Defense 1.0
     */
    @Override
    public void pre_load(@NotNull final Scene scene) {

        // Creates two variables representing the x and y distances between starting point of the path and the screen's edges.
        // This will help with keeping the visual depiction of the path centered regardless of screen size.

        double xSpace = (GameScene.SCREEN.getWidth() - PATH_LENGTH) / 2;
        double ySpace = (GameScene.SCREEN.getHeight() - PATH_HEIGHT) / 2;

        // Sets the enemy spawner's spawn time delay to the global constant representing the time delay.
        ENEMY_SPAWNER.setSpawnPosition(new Position(xSpace, ySpace));

        // Sets the enemy spawner's spawn time delay to the global constant representing the time delay.
        ENEMY_SPAWNER.setSpawnDelay(ENEMY_SPAWN_DELAY);

        // Sets the enemy spawner's enemy pathing to the global constant representing the pathing.
        ENEMY_SPAWNER.setEnemyPathing(new Path(new Position[] {

                // The 2nd position of the path.
                new Position(xSpace + 400, ySpace),

                // The 3rd position of the path.
                new Position(xSpace + 550, ySpace + 200),

                // The 4th position of the path.
                new Position(xSpace + 550, ySpace + 400),

                // The 5th position of the path.
                new Position(xSpace + 900, ySpace + 400),

                // The 6th position of the path.
                new Position(xSpace + 900, ySpace + 200),

                // The 7th position of the path.
                new Position(xSpace + 700, ySpace + 100),

                // The 8th position of the path.
                new Position(xSpace + 700, ySpace),

                // The 9th position of the path.
                new Position(xSpace + 1050, ySpace),

                // The 10th position of the path.
                new Position(xSpace + 1200, ySpace + 200),

                // The 11th and end position of the path.
                new Position(xSpace + 1350, ySpace + 200)
        }));

        // Sets the base's position to the end of the set path.
        PLAYER_BASE.setBasePosition(new Position(xSpace + 1350, ySpace + 200));
    }

    /**
     * The {@link TestMap}'s uniquely implemented loading actions.
     * @param scene the {@link Scene} to add the {@link Scene} to.
     * @since Ultimate Tower Defense 1.0
     */
    @Override
    public void load(@NotNull final Scene scene) {

        // The given scene's parent root.
        Group root = (Group) scene.getRoot();

        // A position that will represent the position before the current position in the loop.
        Position lastPos = ENEMY_SPAWNER.getSpawnPosition();

        // Asserts that the enemy spawner's enemy pathing is not null.
        assert ENEMY_SPAWNER.getEnemyPathing() != null;

        // A loop that will iterate through every position on the enemy path.
        for (Position position : ENEMY_SPAWNER.getEnemyPathing().positions()) {

            // Asserts that the last position is not null.
            assert lastPos != null;

            // Creates a new line that will be drawn from the last position to the current position.
            Line line = new Line(lastPos.x(), lastPos.y(), position.x(), position.y());

            // Sets the line's view order to be at the very back.
            line.setViewOrder(Integer.MAX_VALUE);

            // Allows the line to be added to the given scene's parent root without causing any exceptions.
            // Adds the line to the given scene's parent root.
            Platform.runLater(() -> root.getChildren().add(line));

            // Sets the last position to the current position.
            lastPos = position;
        }

        // Allows for various GUI elements to be added to the given scene's parent root without causing any exceptions.
        // Adds an indicator to the start and end of the enemy pathing to the given scene's parent root.
        Platform.runLater(() -> {

            // Asserts that the enemy spawner's spawn position is not null.
            assert ENEMY_SPAWNER.getSpawnPosition() != null;

            // Adds circles to the ends of the enemy's pathing.
            root.getChildren().addAll(new Circle(ENEMY_SPAWNER.getSpawnPosition().x(), ENEMY_SPAWNER.getSpawnPosition().y(), 5), new Circle(ENEMY_SPAWNER.getEnemyPathing().positions()[ENEMY_SPAWNER.getEnemyPathing().positions().length - 1].x(), ENEMY_SPAWNER.getEnemyPathing().positions()[ENEMY_SPAWNER.getEnemyPathing().positions().length - 1].y(), 5));
        });
    }

    /**
     * The {@link TestMap}'s uniquely implemented post-load action.
     * @param scene a {@link Scene} to use.
     * @throws Exception when an {@link Exception} occurs.
     */
    @Override
    public void post_load(@NotNull final Scene scene) throws Exception {

        // Calls the super post load method.
        Map.super.post_load(scene);
    }
}
