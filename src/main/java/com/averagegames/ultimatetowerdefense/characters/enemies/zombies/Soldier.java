package com.averagegames.ultimatetowerdefense.characters.enemies.zombies;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.util.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.Property;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Nullable;

public final class Soldier extends Enemy {
    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/SoldierZombie.gif");

    @Property
    private final Type type = Type.REGULAR;

    @Property
    private final int startHealth = 50;

    @Property
    private final int startShield = 20;

    @Property
    private final int damage = 5;

    @Property
    private final double speed = 17.5;

    @Property
    private final int income = 1;

    private int shot = -1;

    public Soldier() {
        super.image = this.image;

        super.type = this.type;

        super.setHealth(this.startHealth);
        super.setShield(this.startShield);

        super.coolDown = 5000;

        super.setRadius(125);

        super.speed = this.speed;

        super.income = this.income;

        super.shieldBreak = 5;
    }

    @Override
    public void onSpawn() {
        super.startAttacking();
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

    @Override
    public void attack(@Nullable final Tower tower) throws InterruptedException {
        if (tower == null) {
            this.shot = -1;

            if (super.attacks.getHandleTime() == 750) {
                super.attacks.setHandleTime(super.coolDown);

                super.startMoving();
            }

            return;
        }

        if (shot == -1) {
            super.stopMoving();

            super.attacks.setHandleTime(750);

            super.updatePathing();

            this.shot++;
        } else if (this.shot < 5 && this.shot >= 0) {
            try {
                AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Gunshot 1.wav");

                player.play();
            } catch (Exception ex) {
                System.out.println("Exception occurred");
            }

            tower.damage(this.damage);

            if (!tower.isAlive()) {
                this.shot = 5;
            } else {
                this.shot++;
            }
        } else if (this.shot == 5) {
            this.shot++;
        } else if (this.shot == 6) {
            this.shot = -1;

            super.attacks.setHandleTime(super.coolDown);

            super.startMoving();
        }
    }
}
