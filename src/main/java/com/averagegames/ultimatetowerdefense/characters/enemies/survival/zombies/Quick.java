package com.averagegames.ultimatetowerdefense.characters.enemies.survival.zombies;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.EnemySpawnable;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Nullable;

@EnemySpawnable
public final class Quick extends Enemy {

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/QuickZombie.gif");

    @Property
    private final Type type = Type.REGULAR;

    @Property
    private final int startHealth = 3;

    @Property
    private final int damage = 1;

    @Property
    private final double radius = 40;

    @Property
    private final int coolDown = 7500;

    @Property
    private final int speed = 50;

    @Property
    private final int income = 1;

    private int attack;

    public Quick() {
        super.image = this.image;

        super.type = this.type;

        super.setHealth(this.startHealth);
        super.damage = this.damage;

        super.setRadius(this.radius);
        super.coolDown = this.coolDown;

        super.speed = this.speed;

        super.income = this.income;
    }

    @Override
    protected void onSpawn() {
        super.startAttacking();
    }

    @Override
    protected void onDeath() {
        try {
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Zombie Death 2.wav");
            player.play();
        } catch (Exception ex) {
            System.out.println("Exception occurred");
        }
    }

    @Override
    protected void attack(@Nullable final Tower tower) {
        if (tower == null) {
            return;
        }

        if (this.attack == 0) {
            super.stopMoving();
            super.updatePathing();

            super.attackTimer.setHandleTime(1000);

            try {
                AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Slash 1.wav");

                player.play();
            } catch (Exception ex) {
                System.out.println("Exception occurred");
            }

            tower.damage(super.damage);

            this.attack++;
        } else if (this.attack == 1) {
            this.attack++;
        } else {
            this.attack = 0;

            super.attackTimer.setHandleTime(super.coolDown);

            super.startMoving();
        }
    }
}
