package com.averagegames.ultimatetowerdefense.elements.enemies.survival;

import com.averagegames.ultimatetowerdefense.elements.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.elements.enemies.tools.Survival;
import com.averagegames.ultimatetowerdefense.elements.enemies.tools.Type;
import javafx.scene.image.Image;

@Survival
public final class Fast extends Enemy {

    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/FastZombie.gif");

    private final Type type = Type.REGULAR;

    private final int startHealth = 3;

    private final int damage = 1;

    private final int speed = 50;

    public Fast() {
        super.image = this.image;

        super.type = this.type;

        super.health = this.startHealth;
        super.damage = this.damage;

        super.speed = this.speed;
    }
}
