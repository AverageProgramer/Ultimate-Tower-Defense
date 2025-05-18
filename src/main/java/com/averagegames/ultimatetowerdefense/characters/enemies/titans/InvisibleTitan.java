package com.averagegames.ultimatetowerdefense.characters.enemies.titans;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.util.Property;
import javafx.scene.image.Image;

public final class InvisibleTitan extends Enemy {

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/InvisibleTitan.gif");

    @Property
    private final Type type = Type.HIDDEN;

    @Property
    private final int startHealth = 825;

    @Property
    private final int speed = 35;

    @Property
    private final int income = 2;

    public InvisibleTitan() {
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
