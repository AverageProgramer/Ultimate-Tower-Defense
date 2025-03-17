package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Nullable;

public class Shotgunner extends Tower {

    @Property
    public static final int COST = 600;

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/ShotgunnerTower.gif");

    @Property
    private final int[] damages = {2};

    @Property
    private final int[] coolDowns = {1500};

    @Property
    private final int startHealth = 100;

    @Property
    private final double radius = 40;

    public Shotgunner() {
        super.image = this.image;
        super.damages = this.damages;
        super.coolDowns = this.coolDowns;
        super.setHealth(this.startHealth);
        super.setRadius(this.radius);
    }

    @Override
    protected void attack(@Nullable final Enemy enemy) {
        if (enemy != null && super.isAlive()) {
            try {
                AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Gunshot 4.wav");
                player.play();
            } catch (Exception ex) {
                System.out.println("Exception occurred");
            }

            enemy.damage(super.damages[super.getLevel()]);
        }
    }

    @Override
    public void upgrade() throws InterruptedException {

    }
}
