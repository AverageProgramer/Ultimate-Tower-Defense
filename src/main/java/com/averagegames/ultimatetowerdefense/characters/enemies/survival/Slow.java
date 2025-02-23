package com.averagegames.ultimatetowerdefense.characters.enemies.survival;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import javafx.scene.image.Image;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public final class Slow extends Enemy {
    
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/SlowZombie.gif");

    private final Type type = Type.REGULAR;

    private final int startHealth = 10;

    private final int damage = 3;

    private final int speed = 15;

    public Slow() {
        super.image = this.image;

        super.type = this.type;

        super.setHealth(this.startHealth);
        super.damage = this.damage;

        super.speed = this.speed;
    }

    @Override
    public void onDeath() {
        try {
            File f = new File("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Zombie Death 3.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            System.out.println("Exception occurred");
        }
    }
}
