package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Targeting;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;

import com.averagegames.ultimatetowerdefense.tools.assets.AudioPlayer;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public final class Scout extends Tower {

    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/ScoutTower.gif");

    private final int damage = 1;

    private final int cooldown = 2000;

    public static final int COST = 200;

    public Scout() {
        super.image = this.image;
        super.damage = this.damage;
        super.coolDown = this.cooldown;
    }

    @Override
    public void upgrade() {
        super.setLevel(super.getLevel() + 1);

        if (super.getLevel() >= 3) {
            super.setHiddenDetection(true);
        }
    }

    @Override
    protected void attack(@NotNull final Enemy enemy) throws InterruptedException {
        try {
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Gunshot 1.wav");
            player.play();
        } catch (Exception e) {
            System.out.println("Exception occurred");
        }

        enemy.damage(this.damage);
    }
}
