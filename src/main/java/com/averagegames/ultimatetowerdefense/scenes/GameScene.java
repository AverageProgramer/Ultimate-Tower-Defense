package com.averagegames.ultimatetowerdefense.scenes;

import com.averagegames.ultimatetowerdefense.characters.towers.legendary.Energizer;
import com.averagegames.ultimatetowerdefense.characters.towers.standard.Gunner;
import com.averagegames.ultimatetowerdefense.characters.towers.standard.Marksman;
import com.averagegames.ultimatetowerdefense.characters.towers.standard.Pyromancer;
import com.averagegames.ultimatetowerdefense.characters.towers.standard.Scout;
import com.averagegames.ultimatetowerdefense.maps.Path;
import com.averagegames.ultimatetowerdefense.maps.Position;
import com.averagegames.ultimatetowerdefense.maps.Spawner;
import com.averagegames.ultimatetowerdefense.player.modes.Challenging;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

import static com.averagegames.ultimatetowerdefense.characters.enemies.Enemy.LIST_OF_ACTIVE_ENEMIES;

/**
 * Everything in the {@link GameScene} class as of right now is temporary and subject to change.
 */
public final class GameScene extends Scene implements SceneBuilder {

    private int tower = -1;

    public GameScene(@NotNull final Parent root) {
        super(root);
    }

    @Override
    public void pre_build(@NotNull final Stage stage) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/music/(Official) Tower Defense Simulator OST - Nuclear Fallen King.wav");
        player.loop(AudioPlayer.INDEFINITELY);

        stage.setMaximized(true);
    }

    private static @NotNull Spawner getTestSpawner() {
        Spawner spawner = new Spawner(new Position(0, 100));

        spawner.setEnemyPathing(new Path(new Position[] {
                new Position(400, 100),
                new Position(550, 300),
                new Position(550, 500),
                new Position(900, 500),
                new Position(900, 300),
                new Position(700, 200),
                new Position(700, 100),
                new Position(1050, 100),
                new Position(1200, 300),
                new Position(1350, 300)
        }));

        spawner.setSpawnDelay(1500);

        return spawner;
    }

    private void timerWait() {
        while (!LIST_OF_ACTIVE_ENEMIES.isEmpty());

        for (int i = 0; i < 4; i++) {
            try {
                if (i == 0) {
                    Thread.sleep(1000);
                }

                if (i < 3) {
                    AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Timer Sound Effect 1.wav");
                    player.play();
                }

                Thread.sleep(1000);
            } catch (Exception ex) {
                // Ignore
            }
        }
    }

    @Override
    public void build(@NotNull final Stage stage) {
        Group root = (Group) super.getRoot();

        Spawner spawner = getTestSpawner();

        new Thread(() -> {
            spawner.spawn(Challenging.WAVE_1, root);
            timerWait();

            spawner.spawn(Challenging.WAVE_2, root);
            timerWait();

            spawner.spawn(Challenging.WAVE_3, root);
            timerWait();

            spawner.spawn(Challenging.WAVE_4, root);
            timerWait();

            spawner.spawn(Challenging.WAVE_5, root);
            timerWait();

            spawner.spawn(Challenging.WAVE_6, root);
            timerWait();

            spawner.spawn(Challenging.WAVE_7, root);
            timerWait();

            spawner.spawn(Challenging.WAVE_8, root);
            timerWait();

            spawner.spawn(Challenging.WAVE_9, root);
            timerWait();

            spawner.spawn(Challenging.WAVE_10, root);
            timerWait();

            spawner.spawn(Challenging.WAVE_11, root);
            timerWait();

            spawner.spawn(Challenging.WAVE_12, root);
            timerWait();

            spawner.spawn(Challenging.WAVE_13, root);
            timerWait();

            spawner.spawn(Challenging.WAVE_14, root);
            timerWait();

            spawner.spawn(Challenging.WAVE_15, root);
            timerWait();

            spawner.spawn(Challenging.WAVE_16, root);
            timerWait();

            spawner.spawn(Challenging.WAVE_17, root);
            timerWait();

            spawner.spawn(Challenging.WAVE_18, root);
        }).start();

        this.setOnMouseClicked(event -> {
            if (this.tower == 0) {
                Scout scout = new Scout();

                scout.setParent(root);
                scout.place(new Position(event.getX(), event.getY()));

                scout.startAttacking();
            } else if (this.tower == 1) {
                Marksman marksman = new Marksman();

                marksman.setParent(root);
                marksman.place(new Position(event.getX(), event.getY()));

                marksman.startAttacking();
            } else if (this.tower == 2) {
                Gunner gunner = new Gunner();

                gunner.setParent(root);
                gunner.place(new Position(event.getX(), event.getY()));

                gunner.startAttacking();
            } else if (this.tower == 3) {
                Pyromancer pyromancer = new Pyromancer();

                pyromancer.setParent(root);
                pyromancer.place(new Position(event.getX(), event.getY()));

                pyromancer.startAttacking();
            } else if (this.tower == 4) {
                Energizer energizer = new Energizer();

                energizer.setParent(root);
                energizer.place(new Position(event.getX(), event.getY()));

                energizer.startAttacking();
            }
        });

        this.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.DIGIT1)) {
                this.tower = 0;
            } else if (event.getCode().equals(KeyCode.DIGIT2)) {
                this.tower = 1;
            } else if (event.getCode().equals(KeyCode.DIGIT3)) {
                this.tower = 2;
            } else if (event.getCode().equals(KeyCode.DIGIT4)) {
                this.tower = 3;
            } else if (event.getCode().equals(KeyCode.DIGIT5)) {
                this.tower = 4;
            } else if (event.getCode().equals(KeyCode.DIGIT0)) {
                this.tower = -1;
            }
        });

        stage.setScene(this);
    }

    @Override
    public void post_build(@NotNull final Stage stage) {
        stage.show();
    }
}
