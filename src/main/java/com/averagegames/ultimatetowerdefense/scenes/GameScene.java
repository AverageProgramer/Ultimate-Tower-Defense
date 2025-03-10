package com.averagegames.ultimatetowerdefense.scenes;

import com.averagegames.ultimatetowerdefense.characters.towers.legendary.Energizer;
import com.averagegames.ultimatetowerdefense.characters.towers.standard.*;
import com.averagegames.ultimatetowerdefense.maps.Base;
import com.averagegames.ultimatetowerdefense.maps.Path;
import com.averagegames.ultimatetowerdefense.maps.Position;
import com.averagegames.ultimatetowerdefense.maps.Spawner;
import com.averagegames.ultimatetowerdefense.player.Player;
import com.averagegames.ultimatetowerdefense.player.modes.Easy;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static com.averagegames.ultimatetowerdefense.characters.enemies.Enemy.LIST_OF_ACTIVE_ENEMIES;
import static com.averagegames.ultimatetowerdefense.characters.towers.Tower.LIST_OF_ACTIVE_TOWERS;
import static com.averagegames.ultimatetowerdefense.player.Player.LIST_OF_ACTIVE_FARMS;

// TODO: Complete overhaul of GameScene class. Add wave timer and bonus.

public final class GameScene extends Scene implements SceneBuilder {

    private int tower = -1;

    private int wave = 1;

    public static final Text cashText = new Text(STR."$\{Player.cash}");

    public static final Text baseText = new Text(STR."\{Base.health} HP");

    private boolean skip;

    private boolean spawnerFinished;

    public static final Path PATH = new Path(new Position[] {
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
    });

    public static final Spawner SPAWNER = getTestSpawner();

    public GameScene(@NotNull final Parent root) {
        super(root);
    }

    @Override
    public void pre_build(@NotNull final Stage stage) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/music/(Official) Tower Defense Simulator OST - Nuclear Fallen King.wav");
        // player.loop(AudioPlayer.INDEFINITELY);

        stage.setMaximized(true);
    }

    private static @NotNull Spawner getTestSpawner() {
        Spawner spawner = new Spawner(new Position(0, 100));

        spawner.setEnemyPathing(PATH);

        spawner.setSpawnDelay(1500);

        return spawner;
    }

    private void timerWait() {
        spawnerFinished = true;

        while (!LIST_OF_ACTIVE_ENEMIES.isEmpty() && !skip && Base.health > 0);

        spawnerFinished = false;

        if (Base.health <= 0) {
            return;
        }

        ++this.wave;

        this.skip = false;

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

        for (Farm farm : LIST_OF_ACTIVE_FARMS) {
            Player.cash += farm.getBonus();

            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Farm Income 1.wav");
            try {
                player.play();
            } catch (Exception ex) {
                // Ignore
            }
        }
        Platform.runLater(() -> cashText.setText(STR."$\{Player.cash}"));
    }

    @Override
    public void build(@NotNull final Stage stage) {
        Group root = (Group) super.getRoot();

        cashText.setX(50);
        cashText.setY(725);
        cashText.setFill(Paint.valueOf("#3dbe23"));
        cashText.setFont(Font.font(50));
        root.getChildren().add(cashText);

        baseText.setX(1300);
        baseText.setY(725);
        baseText.setFill(Paint.valueOf("#b60e0e"));
        baseText.setFont(Font.font(50));
        root.getChildren().add(baseText);

        new Thread(() -> {
            SPAWNER.spawn(Easy.WAVE_1, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_2, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_3, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_4, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_5, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_6, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_7, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_8, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_9, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_10, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_11, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_12, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_13, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_14, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_15, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_16, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_17, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_18, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_19, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_20, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_21, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_22, root);
            timerWait();
        }).start();

        this.setOnMouseClicked(event -> {
            if (this.tower == 0 && Player.cash >= Scout.COST) {
                Scout scout = new Scout();

                scout.setParent(root);
                scout.place(new Position(event.getX(), event.getY()));

                scout.startAttacking();

                Player.cash -= Scout.COST;

                Platform.runLater(() -> cashText.setText(STR."$\{Player.cash}"));
            } else if (this.tower == 1 && Player.cash >= Marksman.COST) {
                Marksman marksman = new Marksman();

                marksman.setParent(root);
                marksman.place(new Position(event.getX(), event.getY()));

                marksman.startAttacking();

                Player.cash -= Marksman.COST;

                Platform.runLater(() -> cashText.setText(STR."$\{Player.cash}"));
            } else if (this.tower == 2 && Player.cash >= Gunner.COST) {
                Gunner gunner = new Gunner();

                gunner.setParent(root);
                gunner.place(new Position(event.getX(), event.getY()));

                gunner.startAttacking();

                Player.cash -= Gunner.COST;

                Platform.runLater(() -> cashText.setText(STR."$\{Player.cash}"));
            } else if (this.tower == 3 && Player.cash >= Energizer.COST) {
                AtomicInteger amount = new AtomicInteger();

                LIST_OF_ACTIVE_TOWERS.forEach(tower1 -> {
                    if (tower1 instanceof Energizer) {
                        amount.incrementAndGet();
                    }
                });

                if (amount.get() < Energizer.LIMIT) {
                    Energizer energizer = new Energizer();

                    energizer.setParent(root);
                    energizer.place(new Position(event.getX(), event.getY()));

                    energizer.startAttacking();

                    Player.cash -= Energizer.COST;

                    Platform.runLater(() -> cashText.setText(STR."$\{Player.cash}"));
                } else {
                    AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Error 1.wav");
                    try {
                        player.play();
                    } catch (Exception ex) {
                        // Ignore
                    }
                }
            } else if (this.tower == 4 && Player.cash >= Farm.COST) {
                AtomicInteger amount = new AtomicInteger();

                LIST_OF_ACTIVE_TOWERS.forEach(tower1 -> {
                    if (tower1 instanceof Farm) {
                        amount.incrementAndGet();
                    }
                });

                if (amount.get() < Farm.LIMIT) {
                    Farm farm = new Farm();

                    farm.setParent(root);
                    farm.place(new Position(event.getX(), event.getY()));

                    farm.startAttacking();

                    Player.cash -= Farm.COST;

                    Platform.runLater(() -> cashText.setText(STR."$\{Player.cash}"));
                } else {
                    AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Error 1.wav");
                    try {
                        player.play();
                    } catch (Exception ex) {
                        // Ignore
                    }
                }
            } else if (this.tower == -1) {
                // Nothing
            } else {
                AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Error 1.wav");
                try {
                    player.play();
                } catch (Exception ex) {
                    // Ignore
                }
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
            } else if (event.getCode().equals(KeyCode.S) && this.spawnerFinished) {
                Player.cash += (this.wave * 5) + 100;

                Platform.runLater(() -> cashText.setText(STR."$\{Player.cash}"));

                this.skip = true;
            }
        });

        stage.setScene(this);
    }

    @Override
    public void post_build(@NotNull final Stage stage) {
        stage.show();
    }
}
