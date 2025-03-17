package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Nullable;

public final class Marksman extends Tower {

    public static final int COST = 300;

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/MarksmanTower.gif");

    @Property
    private final int[] upgradeCosts = {150, 500, 1500, 2250, 4000};

    @Property
    private final int[] damages = {4, 6, 12, 20, 35, 40};

    @Property
    private final int[] coolDowns = {5000, 4500, 4500, 3500, 3250, 3000};

    @Property
    private final double[] radii = {175, 175, 185, 190, 210, 220};

    @Property
    private final int startHealth = 100;

    public Marksman() {
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

        try {
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Gunshot 2.wav");
            player.play();
        } catch (Exception ex) {
            System.out.println("Exception occurred");
        }

        enemy.damage(this.damages[super.getLevel()]);
    }

    @Override
    public void upgrade() throws InterruptedException {
        super.setLevel(super.getLevel() + 1);

        if (super.getLevel() >= 4) {
            super.setHiddenDetection(true);
        }

        super.setRadius(this.radii[super.getLevel()]);
    }
}
