package com.averagegames.ultimatetowerdefense.characters.enemies.titans;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.util.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.Property;
import javafx.scene.image.Image;

public class GiantTitan extends Enemy {

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/GiantTitan.gif");

    @Property
    private final Type type = Type.REGULAR;

    @Property
    private final int startHealth = 1750;

    @Property
    private final int speed = 8;

    @Property
    private final int income = 3;

    public GiantTitan() {
        super.image = this.image;

        super.type = this.type;

        super.setHealth(this.startHealth);

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
