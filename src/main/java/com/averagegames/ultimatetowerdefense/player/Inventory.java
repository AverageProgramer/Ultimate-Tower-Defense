package com.averagegames.ultimatetowerdefense.player;

import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link Inventory} record serves as a way to easily store and manage the {@link Tower}'s the {@link Player} chooses to bring into the game.
 * @param towers An array of the {@link Tower}s the {@link Player} wants to bring into the game.
 * @since Ultimate Tower Defense 1.0
 * @see Tower
 * @author AverageProgramer
 */
public record Inventory(@NotNull Tower[] towers) {}
