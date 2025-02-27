package com.averagegames.ultimatetowerdefense.characters.enemies.survival;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.tools.assets.AudioPlayer;
import javafx.scene.image.Image;

public final class Slow extends Enemy {
    
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/SlowZombie.gif");

    private final Type type = Type.REGULAR;

    private final int startHealth = 10;

    private final int damage = 3;

    private final int speed = 15;

    private final int income = 1;

    public Slow() {
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
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Zombie Death 3.wav");
            player.play();
        } catch (Exception e) {
            System.out.println("Exception occurred");
        }
    }
}
