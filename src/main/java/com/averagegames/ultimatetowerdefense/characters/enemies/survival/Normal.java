package com.averagegames.ultimatetowerdefense.characters.enemies.survival;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.util.Type;
import com.averagegames.ultimatetowerdefense.world.maps.elements.Position;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link Normal} class is the first {@link Enemy} that player's will encounter during a game.
 * This {@link Enemy} is {@code universal} and can be found across each {@code survival difficulty} and in {@code campaign mode}.
 * @gamemode this version of the {@link Normal} class is to be used in {@code survival} mode.
 * @see Enemy
 * @author AverageProgramer
 */
public final class Normal extends Enemy {

    /**
     * The {@link Normal}'s {@link Image}.
     */
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/NormalZombie.gif");

    /**
     * The {@link Normal}'s {@link Type}.
     */
    private final Type type = Type.REGULAR;

    /**
     * The {@link Normal}'s starting {@code health}.
     */
    private final int startHealth = 5;

    /**
     * The {@code damage} the {@link Normal} can do during an {@code attack}.
     */
    private final int damage = 0;

    /**
     * The {@link Normal}'s speed in pixels per second.
     */
    private final int speed = 25;

    /**
     * A constructor that properly sets the attributes of a {@link Normal} {@link Enemy}.
     */
    public Normal() {

        // Properly sets the normal's image to the finalized image.
        super.image = this.image;

        // Properly sets the normal's type to the finalized type.
        super.type = this.type;

        // Properly sets the normal's health to the finalized starting health.
        super.health = this.startHealth;

        // Properly sets the normal's damage per attack to the finalized damage per attack.
        super.damage = this.damage;

        // Properly sets the normal's speed in pixels per second to the finalized speed in pixels per second.
        super.speed = this.speed;
    }

    /**
     * A constructor that properly sets the attributes of a {@link Normal} {@link Enemy} as well as its {@link Position}.
     * @param position the {@link Position} of the {@link Normal},
     */
    public Normal(@NotNull final Position position) {

        // Calls the normal's default no-args constructor.
        this();

        // Sets the normal's position to the newly given position.
        super.setPosition(position);
    }

    @Override
    public void onDeath() {

    }
}
