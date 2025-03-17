package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.player.Player;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * The {@link Scout} is one of the two {@code starter} {@link Tower}'s that the {@link Player} will receive when first starting the game.
 * While the {@link Scout} may prove useful in early game strategy, it will not be able to get the {@link Player} all the way to a {@code victory}.
 * @since Ultimate Tower Defense 1.0
 * @see Tower
 * @author AverageProgramer
 */
public final class Scout extends Tower {

    /**
     * The {@link Scout}'s {@code placement cost}.
     */
    @Property
    public static final int COST = 200;

    /**
     * The {@link Scout}'s {@link Image}.
     */
    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/ScoutTower.gif");

    /**
     * The {@link Scout}'s {@code upgrade} costs per {@code level}.
     */
    @Property
    private final int[] upgradeCosts = {25, 250, 750, 1525, 2575};

    /**
     * The {@code damage} the {@link Scout} can do during an {@code attack}.
     */
    @Property
    private final int[] damages = {1, 1, 2, 3, 4, 5};

    /**
     * The {@link Scout}'s cool down between {@code attacks}.
     */
    @Property
    private final int[] coolDowns = {2000, 1750, 1500, 750, 600, 300};

    /**
     * The {@link Scout}'s starting {@code health}.
     */
    @Property
    private final int startHealth = 100;

    /**
     * The {@link Scout}'s {@code range} radius in pixels.
     */
    @Property
    private final double[] radii = {100, 100, 105, 110, 115, 120};

    /**
     * A constructor that properly sets the attributes of the {@link Scout} {@link Tower}.
     */
    public Scout() {

        // Properly sets the scout's image to the finalized image.
        super.image = this.image;

        // Properly sets the scout's upgrade costs to the finalized costs.
        super.upgradeCosts = this.upgradeCosts;

        // Properly sets the scout's damage per attack to the finalized damage per attack.
        super.damages = this.damages;

        // Properly sets the scout's cool down in between attacks.
        super.coolDowns = this.coolDowns;

        // Properly sets the scout's health to the finalized starting health.
        super.setHealth(this.startHealth);

        // Properly sets the scout's range to have the finalized radius.
        super.setRadius(this.radii[0]);
    }

    /**
     * The {@link Scout}'s uniquely implemented {@code attack}.
     * @param enemy the {@link Enemy} to attack.
     * @throws InterruptedException when the {@code attack} is {@code interrupted}.
     * @since Ultimate Tower Defense 1.0
     */
    @Override
    protected void attack(@Nullable final Enemy enemy) throws InterruptedException {

        // Determines whether the enemy is null and whether the scout is alive.
        if (enemy == null || !super.isAlive()) {

            // Prevents the scout from attacking a null enemy or attacking when eliminated.
            return;
        }

        // A try-catch statement that will catch any exceptions that occur when playing an audio file.
        try {

            // Creates a new audio player that will play an audio file.
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Gunshot 1.wav");

            // Plays the previously given audio file.
            player.play();
        } catch (Exception ex) {
            // The exception does not need to be handled.
        }

        // Damages the given enemy.
        enemy.damage(super.damages[super.getLevel()]);
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
