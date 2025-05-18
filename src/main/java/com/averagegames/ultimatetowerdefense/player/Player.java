package com.averagegames.ultimatetowerdefense.player;

import com.averagegames.ultimatetowerdefense.characters.enemies.Wave;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.util.Property;

/**
 * The {@link Player} class serves as a way to encapsulate and represent all game {@code data} that a {@link Player} might have.
 * This data can include anything from in-game {@code cash} to menu {@code silver} and {@code gold} coins.
 * @since Ultimate Tower Defense 1.0
 * @author AverageProgramer
 */
public final class Player {

    /**
     * The amount of {@link Tower}'s the {@link Player} can place during a game.
     */
    @Property
    public static final int TOWER_LIMIT = 40;

    /**
     * The {@link Player}'s current amount of in-game {@code cash}.
     */
    @Property(mutable = true)
    public static int cash;

    /**
     * The {@link Player}'s current in-game {@link Wave}
     */
    @Property(mutable = true)
    public static int wave;

    /**
     * The {@link Player}'s {@link Inventory}
     */
    @Property
    public static Inventory inventory;

    /**
     * The {@link Player}'s current amount of {@code silver} coins.
     */
    @Property(mutable = true)
    public static int silver;

    /**
     * The {@link Player}'s current amount of {@code gold} coins.
     */
    @Property(mutable = true)
    public static int gold;
}
