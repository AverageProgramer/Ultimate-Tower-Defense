package com.averagegames.ultimatetowerdefense.characters.enemies.survival.zombies;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.EnemySpawnable;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Nullable;

/**
 * The {@link Normal} class is the first {@link Enemy} that player's will encounter during a game.
 * This {@link Enemy} is {@code universal} and can be found across each {@code survival difficulty} and in {@code campaign mode}.
 * @gamemode this version of the {@link Normal} class is to be used in {@code survival} mode.
 * @see Enemy
 * @author AverageProgramer
 */
@EnemySpawnable
public final class Normal extends Enemy {

    /**
     * The {@link Normal}'s {@link Image}.
     */
    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/NormalZombie.gif");

    /**
     * The {@link Normal}'s {@link Type}.
     */
    @Property
    private final Type type = Type.REGULAR;

    /**
     * The {@code damage} the {@link Normal} can do during an {@code attack}.
     */
    @Property
    private final int damage = 1;

    @Property
    private final double radius = 20;

    @Property
    private final int coolDown = 7500;

    /**
     * The {@link Normal}'s speed in pixels per second.
     */
    @Property
    private final int speed = 25;

    /**
     * The money earned each time the {@link Normal} is damaged.
     */
    @Property
    private final int income = 1;

    /**
     * The {@link Normal}'s starting {@code health}.
     */
    @Property
    private final int startHealth = 5;

    private int attack;

    /**
     * A constructor that properly sets the attributes of a {@link Normal} {@link Enemy}.
     */
    public Normal() {

        // Properly sets the normal's image to the finalized image.
        super.image = this.image;

        // Properly sets the normal's type to the finalized type.
        super.type = this.type;

        // Properly sets the normal's damage per attack to the finalized damage per attack.
        super.damage = this.damage;

        super.setRadius(this.radius);

        super.coolDown = this.coolDown;

        // Properly sets the normal's speed in pixels per second to the finalized speed in pixels per second.
        super.speed = this.speed;

        // Properly sets the normal's income to the finalized income.
        super.income = this.income;

        // Properly sets the normal's health to the finalized starting health.
        super.setHealth(this.startHealth);
    }

    @Override
    protected void onSpawn() {
        super.startAttacking();
    }

    /**
     * The {@link Normal}'s uniquely implemented {@code death} event.
     * @since Ultimate Tower Defense 1.0
     */
    @Override
    protected void onDeath() {

        // A try-catch statement that will catch any exceptions that occur when playing an audio file.
        try {

            // Creates a new audio player that will play an audio file.
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Zombie Death 1.wav");

            // Plays the previously given audio file.
            player.play();
        } catch (Exception ex) {
            // The exception does not need to be handled.
        }
    }

    @Override
    protected void attack(@Nullable final Tower tower) {
        if (tower == null && this.attack < 1) {
            return;
        }

        if (this.attack == 0) {
            super.stopMoving();
            super.updatePathing();

            super.attackTimer.setHandleTime(1000);

            try {
                AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Slash 1.wav");

                player.play();
            } catch (Exception ex) {
                System.out.println("Exception occurred");
            }

            tower.damage(super.damage);

            this.attack++;
        } else if (this.attack == 1) {
            this.attack++;
        } else {
            this.attack = 0;

            super.attackTimer.setHandleTime(super.coolDown);

            super.startMoving();
        }
    }
}
