package com.averagegames.ultimatetowerdefense.characters.enemies.survival.zombies;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.characters.enemies.Zombie;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import com.averagegames.ultimatetowerdefense.util.development.Specific;
import com.averagegames.ultimatetowerdefense.util.development.SpecificAnnotation;
import javafx.scene.image.Image;

@Zombie
public class Stealthy extends Enemy {

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/StealthyZombie.gif");

    @Property
    private final Type type = Type.HIDDEN;

    @Property
    private final int startHealth = 25;

    @Property
    private final int damage = 1;

    @Property
    private final int speed = 25;

    @Property
    private final int income = 1;

    public Stealthy() {
        super.image = this.image;

        super.type = this.type;

        super.setHealth(this.startHealth);
        super.damage = this.damage;

        super.speed = this.speed;

        super.income = this.income;
    }

    @Override
    @Specific(value = Enemy.class, subclasses = true)
    public void onDeath() {
        SpecificAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());
    }
}
