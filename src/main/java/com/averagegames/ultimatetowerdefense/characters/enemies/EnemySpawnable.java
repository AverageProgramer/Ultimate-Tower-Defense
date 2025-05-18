package com.averagegames.ultimatetowerdefense.characters.enemies;

import com.averagegames.ultimatetowerdefense.characters.enemies.zombies.LootBox;
import com.averagegames.ultimatetowerdefense.characters.enemies.zombies.Sorcerer;
import com.averagegames.ultimatetowerdefense.player.Player;
import org.intellij.lang.annotations.Identifier;

import java.lang.annotation.*;

/**
 * The {@link EnemySpawnable} annotation is meant to annotate classes extending the {@link Enemy} class.
 * Any {@link Enemy} annotated with the {@link EnemySpawnable} annotation can be {@code spawned} by another {@link Enemy}.
 * This annotation is useful for determining what a {@link LootBox} or {@link Sorcerer} can {@code spawn}.
 * @implSpec The {@link EnemySpawnable} annotation does nothing by default and will need manual {@code processing}.
 * @since Ultimate Tower Defense 1.0
 * @see Enemy
 * @author AverageProgramer
 */
@Documented
@Identifier
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnemySpawnable {

    /**
     * Gets what {@link Wave} the {@link Player} needs to be on for the {@link Enemy} to be {@code spawnable}.
     * @return the integer representing the {@link Wave} the {@link Player} needs to be on for the {@link Enemy} to be {@code spawnable}.
     */
    int wave() default 1;
}
