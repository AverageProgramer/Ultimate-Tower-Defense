package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.tools.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.tools.development.Property;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

public final class Scout extends Tower {

    public static final int COST = 200;

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/ScoutTower.gif");

    @Property
    private final int damage = 1;

    @Property
    private final int coolDown = 2000;

    public Scout() {
        super.image = this.image;
        super.damage = this.damage;
        super.coolDown = this.coolDown;
    }

    @Override
    public void upgrade() throws InterruptedException {
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
        } catch (Exception ex) {
            System.out.println("Exception occurred");
        }

        enemy.damage(this.damage);
    }
}
