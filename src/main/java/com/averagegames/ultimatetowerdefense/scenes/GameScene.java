package com.averagegames.ultimatetowerdefense.scenes;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Wave;
import com.averagegames.ultimatetowerdefense.characters.enemies.survival.titans.GiantTitan;
import com.averagegames.ultimatetowerdefense.characters.enemies.survival.titans.LootBoxTitan;
import com.averagegames.ultimatetowerdefense.characters.enemies.survival.titans.NormalTitan;
import com.averagegames.ultimatetowerdefense.characters.enemies.survival.zombies.*;
import com.averagegames.ultimatetowerdefense.characters.towers.standard.Gunner;
import com.averagegames.ultimatetowerdefense.characters.towers.standard.Marksman;
import com.averagegames.ultimatetowerdefense.characters.towers.standard.Scout;
import com.averagegames.ultimatetowerdefense.maps.Path;
import com.averagegames.ultimatetowerdefense.maps.Position;
import com.averagegames.ultimatetowerdefense.maps.Spawner;
import com.averagegames.ultimatetowerdefense.tools.assets.AudioPlayer;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public final class GameScene extends Scene implements SceneBuilder {

    private int tower = -1;

    public GameScene(@NotNull final Parent root) {
        super(root);
    }

    @Override
    public void pre_build(@NotNull final Stage stage) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/music/Daybreak OST - SCP Roleplay.wav");
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

    @Override
    public void build(@NotNull final Stage stage) {
        Group root = (Group) super.getRoot();

        Spawner spawner = getTestSpawner();

        spawner.spawn(new Wave(new Enemy[] {
                new NormalTitan(),

                new Slow(),
                new Slow(),
                new Slow(),
                new Slow(),
                new Slow(),

                new Normal(),
                new Normal(),
                new Normal(),
                new Normal(),
                new Normal(),

                new Quick(),
                new Quick(),
                new Quick(),
                new Quick(),
                new Quick(),

                new NormalTitan(),

                new Stealthy(),
                new Stealthy(),
                new Stealthy(),
                new Stealthy(),
                new Stealthy(),

                new LootBox(),
                new LootBox(),
                new LootBox(),
                new LootBox(),
                new LootBox(),

                new GiantTitan(),

                new Slow(),
                new Slow(),
                new Slow(),
                new Slow(),
                new Slow(),

                new Normal(),
                new Normal(),
                new Normal(),
                new Normal(),
                new Normal(),

                new Quick(),
                new Quick(),
                new Quick(),
                new Quick(),
                new Quick(),

                new GiantTitan(),

                new Stealthy(),
                new Stealthy(),
                new Stealthy(),
                new Stealthy(),
                new Stealthy(),

                new LootBox(),
                new LootBox(),
                new LootBox(),
                new LootBox(),
                new LootBox(),

                new LootBoxTitan(),
                new LootBoxTitan(),
                new LootBoxTitan(),
                new LootBoxTitan(),
                new LootBoxTitan(),

                new LootBox(),
                new LootBox(),
                new LootBox(),
                new LootBox(),
                new LootBox(),

                new Stealthy(),
                new Stealthy(),
                new Stealthy(),
                new Stealthy(),
                new Stealthy(),

                new Normal(),
                new Normal(),
                new Normal(),
                new Normal(),
                new Normal(),

                new Quick(),
                new Quick(),
                new Quick(),
                new Quick(),
                new Quick(),

                new Slow(),
                new Slow(),
                new Slow(),
                new Slow(),
                new Slow(),
        }), root);

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
            }
        });

        this.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.DIGIT1)) {
                this.tower = 0;
            } else if (event.getCode().equals(KeyCode.DIGIT2)) {
                this.tower = 1;
            } else if (event.getCode().equals(KeyCode.DIGIT3)) {
                this.tower = 2;
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
