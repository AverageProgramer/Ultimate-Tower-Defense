package com.averagegames.ultimatetowerdefense.characters.enemies.survival;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.maps.Position;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

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
     * The {@code damage} the {@link Normal} can do during an {@code attack}.
     */
    private final int damage = 0;

    /**
     * The {@link Normal}'s speed in pixels per second.
     */
    private final int speed = 25;

    /**
     * The {@link Normal}'s starting {@code health}.
     */
    private final int startHealth = 5;

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

        // Properly sets the normal's speed in pixels per second to the finalized speed in pixels per second.
        super.speed = this.speed;

        // Properly sets the normal's health to the finalized starting health.
        super.setHealth(this.startHealth);
    }

    @Override
    protected void onDeath() {
        try {
            File f = new File("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Zombie Death 1.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            System.out.println("Exception occurred");
        }
    }
}
