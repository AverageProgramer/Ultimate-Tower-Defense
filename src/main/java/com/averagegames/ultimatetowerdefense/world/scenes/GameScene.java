package com.averagegames.ultimatetowerdefense.world.scenes;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.survival.Fast;
import com.averagegames.ultimatetowerdefense.characters.enemies.survival.Normal;
import com.averagegames.ultimatetowerdefense.characters.enemies.survival.Slow;
import com.averagegames.ultimatetowerdefense.characters.enemies.util.Wave;
import com.averagegames.ultimatetowerdefense.world.maps.elements.Path;
import com.averagegames.ultimatetowerdefense.world.maps.elements.Position;
import com.averagegames.ultimatetowerdefense.world.maps.elements.Spawner;
import com.averagegames.ultimatetowerdefense.tools.Builder;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

@Internal
public final class GameScene extends Scene implements Builder {
    public GameScene(@NotNull final Parent root) {
        super(root);
    }

    @Override
    public void pre_build(@SuppressWarnings("exports") @NotNull final Stage stage) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File f = new File("src/main/resources/com/averagegames/ultimatetowerdefense/audio/(Official) Tower Defense Simulator OST - Grave Buster.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.loop(Clip.LOOP_CONTINUOUSLY);

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
    public void build(@SuppressWarnings("exports") @NotNull final Stage stage) {
        Spawner spawner = getTestSpawner();

        spawner.spawn(new Wave(new Enemy[] {
                new Normal(),
                new Normal(),
                new Normal(),
                new Normal(),
                new Normal(),

                new Fast(),
                new Fast(),
                new Fast(),
                new Fast(),
                new Fast(),

                new Slow(),
                new Slow(),
                new Slow(),
                new Slow(),
                new Slow()
        }), (Group) super.getRoot());

        SCENE_MANAGER.put(0, this);

        stage.setScene(this);
    }

    @Override
    public void post_build(@SuppressWarnings("exports") @NotNull final Stage stage) {
        stage.show();
    }
}
