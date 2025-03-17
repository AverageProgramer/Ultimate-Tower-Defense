package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Nullable;

public final class Gunner extends Tower {

    public static final int COST = 500;

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/GunnerTower.gif");

    @Property
    private final int[] upgradeCosts = {100, 400, 1500, 2500, 4750};

    @Property
    private final int[] damages = {1, 1, 2, 3, 4, 5};

    @Property
    private final int[] coolDowns = {1250, 1000, 900, 900, 800, 700};

    @Property
    private final int startHealth = 100;

    @Property
    private final double[] radii = {125, 130, 130, 135, 135, 140};

    public Gunner() {
        super.image = this.image;
        super.upgradeCosts = this.upgradeCosts;
        super.damages = this.damages;
        super.coolDowns = this.coolDowns;
        super.setHealth(this.startHealth);
        super.setRadius(this.radii[0]);
    }

    @Override
    protected void attack(@Nullable final Enemy enemy) throws InterruptedException {
        // Determines whether the enemy is null.
        if (enemy == null || !super.isAlive()) {

            // Prevents the scout from attacking a null enemy.
            return;
        }

        for (int i = 0; i < 3; i++) {
            if (enemy.isAlive()) {
                try {
                    AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Gunshot 1.wav");
                    player.play();
                } catch (Exception ex) {
                    System.out.println("Exception occurred");
                }

                enemy.damage(super.damages[super.getLevel()]);
            } else {
                break;
            }

            Thread.sleep(200);
        }
    }

    @Override
    public void upgrade() throws InterruptedException {
        super.setLevel(super.getLevel() + 1);

        if (super.getLevel() >= 2) {
            super.setHiddenDetection(true);
        }

        super.setRadius(this.radii[super.getLevel()]);
    }
}
