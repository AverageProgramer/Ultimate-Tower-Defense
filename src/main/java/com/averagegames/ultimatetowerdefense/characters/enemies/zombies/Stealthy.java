package com.averagegames.ultimatetowerdefense.characters.enemies.zombies;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.EnemySpawnable;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.util.Property;
import javafx.scene.image.Image;

@EnemySpawnable
public class Stealthy extends Enemy {

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/StealthyZombie.gif");

    @Property
    private final Type type = Type.HIDDEN;

    @Property
    private final int startHealth = 25;

    @Property
    private final int speed = 25;

    @Property
    private final int income = 1;

    public Stealthy() {
        super.image = this.image;

        super.type = this.type;

        super.setHealth(this.startHealth);

        super.speed = this.speed;

        super.income = this.income;
    }

    @Override
    public void onDeath() {

    }
}
