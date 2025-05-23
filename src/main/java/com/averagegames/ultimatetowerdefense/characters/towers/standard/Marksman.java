package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.util.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.Property;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Nullable;

public final class Marksman extends Tower {

    public static final int COST = 300;

    @Property
    private final Image[] images = {
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/marksman/MarksmanTower0.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/marksman/MarksmanTower1.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/marksman/MarksmanTower2.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/marksman/MarksmanTower3.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/marksman/MarksmanTower4.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/marksman/MarksmanTower5.gif"),
    };

    @Property
    private final int placementCost = 300;

    @Property
    private final int[] upgradeCosts = {150, 500, 1500, 2250, 4000};

    @Property
    private final int[] damages = {4, 6, 12, 20, 25, 30};

    @Property
    private final int[] coolDowns = {5000, 4500, 4500, 4000, 3750, 3500};

    @Property
    private final double[] radii = {175, 175, 180, 190, 200, 220};

    @Property
    private final int startHealth = 100;

    public Marksman() {
        super.images = this.images;
        super.placementCost = this.placementCost;
        super.upgradeCosts = this.upgradeCosts;
        super.coolDowns = this.coolDowns;
        super.setHealth(this.startHealth);
        super.setRadius(this.radii[0]);
        super.setSpaceLength(30);
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
        if (super.getLevel() < 5) {
            super.setLevel(super.getLevel() + 1);
        }

        if (super.getLevel() >= 4) {
            super.setHiddenDetection(true);
        }

        super.getLoadedTower().setImage(this.images[super.getLevel()]);

        super.setRadius(this.radii[super.getLevel()]);

        super.attacks.setHandleTime(this.coolDowns[super.getLevel()]);
    }
}
