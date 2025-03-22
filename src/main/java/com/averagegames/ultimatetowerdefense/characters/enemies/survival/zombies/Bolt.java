package com.averagegames.ultimatetowerdefense.characters.enemies.survival.zombies;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.scene.image.Image;

public class Bolt extends Enemy {

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/BoltZombie.gif");

    @Property
    private final Type type = Type.REGULAR;

    @Property
    private final int startHealth = 80;

    @Property
    private final int damage = 1;

    @Property
    private final int speed = 85;

    @Property
    private final int income = 1;

    public Bolt() {
        super.image = this.image;

        super.type = this.type;

        super.setHealth(this.startHealth);
        super.damage = this.damage;

        super.speed = this.speed;

        super.income = this.income;
    }

    @Override
    public void onDeath() {
        try {
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Zombie Death 7.wav");
            player.play();
        } catch (Exception ex) {
            System.out.println("Exception occurred");
        }
    }
}
