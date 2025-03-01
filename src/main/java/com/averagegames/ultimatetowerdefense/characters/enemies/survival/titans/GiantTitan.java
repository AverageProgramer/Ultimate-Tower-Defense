package com.averagegames.ultimatetowerdefense.characters.enemies.survival.titans;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Titan;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.tools.assets.AudioPlayer;
import javafx.scene.image.Image;

@Titan
@SuppressWarnings("all")
public class GiantTitan extends Enemy {
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/GiantTitan.gif");

    private final Type type = Type.REGULAR;

    private final int startHealth = 1750;

    private final int damage = 1;

    private final int speed = 8;

    private final int income = 1;

    public GiantTitan() {
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
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Zombie Death 6.wav");
            player.play();
        } catch (Exception ex) {
            System.out.println("Exception occurred");
        }
    }
}
