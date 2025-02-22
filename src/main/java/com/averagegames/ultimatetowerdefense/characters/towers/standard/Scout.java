package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

public final class Scout extends Tower {

    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/ScoutTower.gif");

    private final int damage = 1;

    private final int cooldown = 2000;

    public Scout() {
        super.image = this.image;
        super.damage = damage;
        super.coolDown = cooldown;
    }

    @Override
    public void upgrade() {
        // TODO: Add upgrades
    }

    @Override
    protected void attack(@NotNull final Enemy enemy) throws InterruptedException {
        enemy.damage(this.damage);
    }
}
