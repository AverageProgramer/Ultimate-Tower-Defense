package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.maps.tools.Position;
import com.averagegames.ultimatetowerdefense.util.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.Property;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Nullable;

public final class Gunner extends Tower {

    public static final int COST = 500;

    @Property
    private final Image[] images = {
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/gunner/GunnerTower0.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/gunner/GunnerTower1.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/gunner/GunnerTower2.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/gunner/GunnerTower3.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/gunner/GunnerTower4.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/gunner/GunnerTower5.gif"),
    };

    @Property
    private final int placementCost = 500;

    @Property
    private final int[] upgradeCosts = {100, 400, 1500, 2500, 4750};

    @Property
    private final int[] damages = {1, 1, 2, 3, 4, 5};

    @Property
    private final int[] coolDowns = {1250, 1000, 900, 900, 800, 700};

    @Property
    private final int[] damageCoolDowns = {200, 200, 200, 200, 175, 125};

    @Property
    private final int[] bursts = {3, 3, 3, 4, 5, 7};

    @Property
    private final int startHealth = 100;

    @Property
    private final double[] radii = {125, 130, 130, 140, 150, 160};

    private int shot;

    public Gunner() {
        super.images = this.images;
        super.placementCost = this.placementCost;
        super.upgradeCosts = this.upgradeCosts;
        super.coolDowns = this.coolDowns;
        super.setHealth(this.startHealth);
        super.setRadius(this.radii[0]);
        super.setSpaceLength(25);
    }

    @Override
    protected void attack(@Nullable final Enemy enemy) throws InterruptedException {

        // Determines whether the enemy is null.
        if (enemy == null || !super.isAlive()) {

            // Prevents the scout from attacking a null enemy.
            return;
        }

        if (super.attacks.getHandleTime() == super.coolDowns[super.getLevel()]) {
            super.attacks.setHandleTime(this.damageCoolDowns[super.getLevel()]);
        }

        if (this.shot < this.bursts[super.getLevel()]) {
            try {
                AudioPlayer player = new AudioPlayer();

                if (super.getLevel() < 5) {
                    player.setPathname("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Gunshot 1.wav");
                } else {
                    player.setPathname("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Gunshot 5.wav");
                }

                player.play();
            } catch (Exception ex) {
                System.out.println("Exception occurred");
            }

            enemy.damage(this.damages[super.getLevel()]);

            if (!enemy.isAlive()) {
                this.shot = 0;

                super.attacks.setHandleTime(this.coolDowns[super.getLevel()]);
            } else {
                this.shot++;
            }
        } else {
            this.shot = 0;

            super.attacks.setHandleTime(this.coolDowns[super.getLevel()]);
        }
    }

    @Override
    public void upgrade() throws InterruptedException {
        Position oldPos = super.getPosition();

        if (super.getLevel() < 5) {
            super.setLevel(super.getLevel() + 1);
        }

        if (super.getLevel() >= 2) {
            super.setHiddenDetection(true);
        }

        super.getLoadedTower().setImage(this.images[super.getLevel()]);

        super.setRadius(this.radii[super.getLevel()]);

        super.setPosition(oldPos);

        super.attacks.setHandleTime(this.coolDowns[super.getLevel()]);
    }
}
