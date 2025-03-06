package com.averagegames.ultimatetowerdefense.characters.towers.legendary;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.player.Player;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link Energizer} is considered a {@code legendary} {@link Tower} capable of dealing massive amounts of {@code damage}.
 * Possessing {@code hidden detection} capabilities at {@code level 0}, the {@link Energizer} is an extremely powerful and effective {@link Tower}.
 * This {@link Tower} can be obtained using {@code gold} by the {@link Player}.
 * @since Ultimate Tower Defense 1.0
 * @see Tower
 * @author AverageProgramer
 */
public class Energizer extends Tower {

    /**
     * The {@link Energizer}'s {@code placement cost}.
     */
    @Property
    public static final int COST = 1500;

    /**
     * The {@link Energizer}'s {@link Image}.
     */
    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/EnergizerTower.gif");

    /**
     * The {@code damage} the {@link Energizer} can do during an {@code attack}.
     */
    @Property
    private final int damage = 5;

    /**
     * The time it takes the {@link Energizer} to {@code charge} and {@code attack}.
     */
    @Property
    private final int chargeTime = 3000;

    /**
     * The time between {@code damaging} a given {@link Enemy}.
     */
    @Property
    private final int damageTime = 200;

    /**
     * The time the {@link Energizer} can {@code attack}.
     */
    @Property
    private final int attackTime = 3000;

    /**
     * The {@link Energizer}'s cool down between {@code attacks}.
     */
    @Property
    private final int coolDown = 250;

    /**
     * The {@link Energizer}'s starting {@code health}.
     */
    @Property
    private final int startHealth = 100;

    /**
     * A boolean that determines whether the {@link Energizer} should charge for an {@code attack}.
     */
    private boolean doCharge;

    /**
     * A boolean that determines whether the {@link Energizer} should {@code attack}.
     */
    private boolean doAttack;

    /**
     * A constructor that properly sets the attributes of the {@link Energizer} {@link Tower}.
     */
    public Energizer() {

        // Properly sets the energizer's image to the finalized image.
        super.image = this.image;

        // Properly sets the energizer's damage per attack to the finalized damage per attack.
        super.damage = this.damage;

        // Properly sets the energizer's cool down in between attacks.
        super.coolDown = this.coolDown;

        // Properly sets the energizer's hidden detection capabilities to true.
        super.setHiddenDetection(true);

        // Properly sets the energizer's health to the finalized starting health.
        super.setHealth(this.startHealth);

        // Properly sets the boolean that determines whether the energizer should charge to true.
        this.doCharge = true;

        // Properly sets the boolean that determines whether the energizer should attack to true.
        this.doAttack = true;
    }

    /**
     * The {@link Energizer}'s uniquely implemented {@code attack}.
     * @param enemy the {@link Enemy} to attack.
     * @throws InterruptedException when the {@code attack} is {@code interrupted}.
     */
    @Override
    protected void attack(@NotNull final Enemy enemy) throws InterruptedException {
        // TODO: Implement tower attacks
    }
}
