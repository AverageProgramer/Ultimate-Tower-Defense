package com.averagegames.ultimatetowerdefense.characters.enemies.zombies;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.EnemySpawnable;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.util.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.Property;
import javafx.scene.image.Image;

@EnemySpawnable(wave = 20)
public class Armored extends Enemy {

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/ArmoredZombie.gif");

    @Property
    private final Type type = Type.REGULAR;

    @Property
    private final int startHealth = 15;

    @Property
    private final int startShield = 50;

    @Property
    private final double speed = 12.5;

    @Property
    private final int income = 1;

    public Armored() {
        super.image = this.image;

        super.type = this.type;

        super.setHealth(this.startHealth);
        super.setShield(this.startShield);

        super.speed = this.speed;

        super.income = this.income;

        super.shieldBreak = 5;
    }

    @Override
    public void onDamaged() {
        if (super.getShield() > 0) {
            try {
                AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Shield 2.wav");
                player.play();
            } catch (Exception ex) {
                // Ignore
            }
        }
    }

    @Override
    public void onDeath() {
        try {
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Zombie Death 1.wav");
            player.play();
        } catch (Exception ex) {
            System.out.println("Exception occurred");
        }
    }
}
