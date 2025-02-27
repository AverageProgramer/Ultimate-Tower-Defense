package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.tools.assets.AudioPlayer;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

public class Marksman extends Tower {
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/MarksmanTower.gif");

    private final int damage = 4;

    private final int cooldown = 5000;

    public static final int COST = 300;

    public Marksman() {
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
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Gunshot 2.wav");
            player.play();
        } catch (Exception e) {
            System.out.println("Exception occurred");
        }

        enemy.damage(this.damage);
    }
}
