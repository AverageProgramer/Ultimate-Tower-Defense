package com.averagegames.ultimatetowerdefense.characters.towers.legendary;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.player.Player;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The {@link Energizer} is considered a {@code legendary} {@link Tower} capable of dealing massive amounts of {@code damage}.
 * Possessing {@code hidden detection} capabilities at {@code level 0}, the {@link Energizer} is an extremely powerful and effective {@link Tower}.
 * This {@link Tower} can be obtained using {@code gold} by the {@link Player}.
 * @since Ultimate Tower Defense 1.0
 * @see Tower
 * @author AverageProgramer
 */
public final class Energizer extends Tower {

    /**
     * The {@link Energizer}'s {@code placement cost}.
     */
    @Property
    public static final int COST = 2500;

    /**
     * The {@link Energizer}'s placement {@code limit}.
     */
    @Property
    public static final int LIMIT = 8;

    /**
     * The {@link Energizer}'s {@link Image}.
     */
    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/EnergizerTower.gif");

    /**
     * The {@link Energizer}'s {@code upgrade} costs per {@code level}.
     */
    @Property
    private final int[] upgradeCosts = {1200, 2250, 4000, 9500, 30000};

    /**
     * The {@code damage} the {@link Energizer} can do during an {@code attack}.
     */
    @Property
    private final int[] damages = {5, 6, 6, 8, 10, 15};

    /**
     * The time it takes the {@link Energizer} to {@code charge} and {@code attack}.
     */
    @Property
    private final int[] chargeTimes = {3000, 3000, 2750, 2500, 2000, 1500};

    /**
     * The time the {@link Energizer} can {@code attack}.
     */
    @Property
    private final int[] attackTimes = {3000, 3250, 3500, 4500, 5750, 7000};

    /**
     * The {@link Energizer}'s total time {@code attacking}.
     */
    @Property
    private int totalTime = 0;

    /**
     * The {@link Energizer}'s cool down between {@code attacks}.
     */
    @Property
    private final int[] coolDowns = {200, 200, 200, 200, 200, 200};

    /**
     * The {@link Energizer}'s cool down between being able to {@code attack} and {@code charge}.
     */
    @Property
    private final int chargeCoolDown = 2500;

    /**
     * The {@link Energizer}'s starting {@code health}.
     */
    @Property
    private final int startHealth = 100;

    /**
     * The {@link Energizer}'s {@code range} radius in pixels.
     */
    @Property
    private final double[] radii = {125, 125, 130, 130, 135, 170};

    /**
     * A boolean that determines whether the {@link Energizer} should charge for an {@code attack}.
     */
    private boolean doCharge;

    /**
     * The {@link Thread} responsible for handling {@code charging}.
     */
    @NotNull
    private Thread chargeThread;

    /**
     * A constructor that properly sets the attributes of the {@link Energizer} {@link Tower}.
     */
    public Energizer() {

        // Properly sets the energizer's image to the finalized image.
        super.image = this.image;

        // Properly sets the energizer's upgrade costs to the finalized costs.
        super.upgradeCosts = this.upgradeCosts;

        // Properly sets the energizer's damage per attack to the finalized damage per attack.
        super.damages = this.damages;

        // Properly sets the energizer's cool down in between attacks.
        super.coolDowns = this.coolDowns;

        // Properly sets the energizer's health to the finalized starting health.
        super.setHealth(this.startHealth);

        // Properly sets the energizer's range to have the finalized radius.
        super.setRadius(this.radii[0]);

        // Properly sets the boolean that determines whether the energizer should charge to true.
        this.doCharge = true;

        // Initializes the thread that the energizer will use to charge.
        this.chargeThread = new Thread(() -> {
            // This thread does nothing by default.
        });
    }

    /**
     * The {@link Energizer}'s uniquely implemented {@code attack}.
     * @param enemy the {@link Enemy} to attack.
     * @throws InterruptedException when the {@code attack} is {@code interrupted}.
     */
    @Override
    protected void attack(@Nullable final Enemy enemy) throws InterruptedException {
        if (enemy == null && !this.doCharge) {
            this.chargeThread.interrupt();
            this.doCharge = true;

            Thread.sleep(this.chargeCoolDown);
        }

        if (enemy != null && this.doCharge && !this.chargeThread.isAlive()) {
            this.totalTime = 0;

            (this.chargeThread = new Thread(() -> {
                try {
                    Thread.sleep(this.chargeTimes[super.getLevel()]);

                    this.doCharge = false;
                } catch (Exception ex) {
                    // Ignore
                }
            })).start();
        }

        if (enemy != null && !this.doCharge) {
            while (true) {
                AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Energy Gun 2.wav");
                try {
                    player.play();
                } catch (Exception ex) {
                    // Ignore
                }

                enemy.damage(super.damages[super.getLevel()]);

                this.totalTime += super.coolDowns[super.getLevel()];

                if (this.totalTime >= this.attackTimes[super.getLevel()]) {
                    this.doCharge = true;

                    Thread.sleep(this.chargeCoolDown);

                    break;
                }

                if (!enemy.isAlive()) {
                    break;
                }

                Thread.sleep(super.coolDowns[super.getLevel()]);
            }
        }
    }

    @Override
    public void upgrade() throws InterruptedException {
        super.setLevel(super.getLevel() + 1);

        if (super.getLevel() >= 1) {
            super.setHiddenDetection(true);
        }

        super.setRadius(this.radii[super.getLevel()]);
    }
}