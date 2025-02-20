package com.averagegames.ultimatetowerdefense.tools;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@link StrictHashMap} class functions almost identically to a standard {@link HashMap} the only difference being that a {@link StrictHashMap} forbids duplicate {@code keys} and duplicate {@code values}.
 * There can not be two identical {@code keys} within a {@link StrictHashMap} and there can not be two identical {@code values} in a {@link StrictHashMap}.
 * @param <K> the {@code key}.
 * @param <V> the {@code value}.
 * @since Ultimate Tower Defense 1.0
 * @author AverageProgramer
 */
@NoArgsConstructor
public class StrictHashMap<K, V> extends HashMap<K, V> {

    /**
     * The {@link StrictHashMap}'s serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 3205997124733580291L;

    /**
     * A constructor that sets a capacity but does not set a load factor for the {@link StrictHashMap}.
     * @param initialCapacity the {@link StrictHashMap}'s initial capacity.
     * @since Ultimate Tower Defense 1.0
     */
    public StrictHashMap(final int initialCapacity) {

        // Initializes the map using a given map as a blueprint.
        super(initialCapacity);
    }

    /**
     * A constructor that sets a capacity as well as a load factor for the {@link StrictHashMap}.
     * @param initialCapacity the {@link StrictHashMap}'s initial capacity.
     * @param loadFactor the {@link StrictHashMap}'s load factor.
     * @since Ultimate Tower Defense 1.0
     */
    public StrictHashMap(final int initialCapacity, final float loadFactor) {

        // Initializes the map using an initial capacity and load factor.
        super(initialCapacity, loadFactor);
    }

    /**
     * A constructor that sets the {@link StrictHashMap} to behave in similar ways to a given {@link Map}.
     * @param map the {@link Map} to use as a blueprint.
     * @since Ultimate Tower Defense 1.0
     */
    public StrictHashMap(@NotNull final Map<? extends K, ? extends V> map) {

        // Initializes the map using a given map as a blueprint.
        super(map);
    }

    /**
     * Puts a {@code key-value} pair into the {@link StrictHashMap}.
     * If a duplicate of either the {@code key} or the {@code value} is found, the pair will not be added to the {@link StrictHashMap}.
     * @param key the {@code key} with which the specified {@code value} is to be associated
     * @param value the {@code value} to be associated with the specified {@code key}
     * @return the value if there were no duplicates found in the {@link StrictHashMap}, {@code null} otherwise.
     * @defaultValue the default return value is {@code null}.
     * @since Ultimate Tower Defense 1.0
     */
    @Override
    public @Nullable V put(final K key, final V value) {

        // Determines if the map already contains the given key.
        if (this.containsKey(key)) {

            // Returns a null value indicating that the key-value pair was not added to the map.
            return null;
        }

        // Determines if the map already contains the given value.
        if (this.containsValue(value)) {

            // Returns a null value indicating that the key-value pair was not added to the map.
            return null;
        }

        // Returns the value received from calling the super class method indicating that the key-value pair was successfully added to the map.
        return super.putIfAbsent(key, value);
    }

    /**
     * Adds the {@code key-value} pairs of a given {@link Map} to the {@link StrictHashMap}.
     * The duplication rules still apply an if any duplicate {@code keys} or {@code values} or found in the given {@link Map} they will not be added.
     * @param map the {@code mappings} to be stored in this {@link Map}
     * @since Ultimate Tower Defense 1.0
     */
    @Override
    public void putAll(@NotNull final Map<? extends K, ? extends V> map) {

        // A loop that iterates through each key-value pair in the given map.
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {

            // Adds the current key-value pair to the map if no duplicate keys or values were found.
            this.put(entry.getKey(), entry.getValue());
        }
    }
}
