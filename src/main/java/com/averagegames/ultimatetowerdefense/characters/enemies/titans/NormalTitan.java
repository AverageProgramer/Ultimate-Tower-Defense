package com.averagegames.ultimatetowerdefense.characters.enemies.titans;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.characters.enemies.zombies.Normal;
import com.averagegames.ultimatetowerdefense.util.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.Property;
import javafx.scene.image.Image;

public class NormalTitan extends Enemy {

    /**
     * The {@link NormalTitan}'s {@link Image}.
     */
    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/NormalTitan.gif");

    /**
     * The {@link NormalTitan}'s {@link Type}.
     */
    @Property
    private final Type type = Type.REGULAR;

    /**
     * The {@link NormalTitan}'s speed in pixels per second.
     */
    @Property
    private final int speed = 10;

    /**
     * The money earned each time the {@link NormalTitan} is damaged.
     */
    @Property
    private final int income = 3;

    /**
     * The {@link NormalTitan}'s starting {@code health}.
     */
    @Property
    private final int startHealth = 250;

    /**
     * A constructor that properly sets the attributes of a {@link Normal} {@link Enemy}.
     */
    public NormalTitan() {

        // Properly sets the normal titan's image to the finalized image.
        super.image = this.image;

        // Properly sets the normal titan's type to the finalized type.
        super.type = this.type;

        // Properly sets the normal titan's speed in pixels per second to the finalized speed in pixels per second.
        super.speed = this.speed;

        // Properly sets the normal titan's income to the finalized income.
        super.income = this.income;

        // Properly sets the normal titan's health to the finalized starting health.
        super.setHealth(this.startHealth);
    }

    @Override
    protected void onDeath() {
        try {
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Zombie Death 5.wav");
            player.play();
        } catch (Exception ex) {
            System.out.println("Exception occurred");
        }
    }
}
