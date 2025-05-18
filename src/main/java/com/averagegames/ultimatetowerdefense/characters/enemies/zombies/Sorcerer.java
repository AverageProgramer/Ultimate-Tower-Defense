package com.averagegames.ultimatetowerdefense.characters.enemies.zombies;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Summoner;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.player.Player;
import com.averagegames.ultimatetowerdefense.util.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.Property;
import javafx.application.Platform;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Nullable;

@Summoner
public class Sorcerer extends Enemy {

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/SorcererZombie.gif");

    @Property
    private final Type type = Type.REGULAR;

    @Property
    private final int startHealth = 135;

    @Property
    private final int speed = 20;

    @Property
    private final int income = 2;

    @Property
    private final int coolDown = 15000;

    private int spawned;

    public Sorcerer() {
        super.image = this.image;

        super.type = this.type;

        super.setHealth(this.startHealth);

        super.speed = this.speed;

        super.income = this.income;

        super.coolDown = this.coolDown;

        this.spawned = -2;
    }

    @Override
    public void onSpawn() {
        super.startAttacking();
    }

    @Override
    protected void attack(@Nullable final Tower tower) {
        if (this.spawned == -2) {
            this.spawned++;

            return;
        }

        if (super.attacks.getHandleTime() == super.coolDown) {
            super.attacks.setHandleTime(1500);
        }

        if (this.spawned == -1) {
            super.stopMoving();

            super.updatePathing();

            this.spawned++;

            return;
        }

        if (this.spawned < 5) {
            int enemy = (int) (Math.random() * ((Player.wave >= 20 ? 7 : 6) - 1)) + 1;

            Enemy e = switch (enemy) {
                case 1 ->
                        new Normal();
                case 2 ->
                        new Quick();
                case 3 ->
                        new Slow();
                case 4 ->
                        new Stealthy();
                case 5 ->
                        new LootBox();
                case 6 ->
                        new Armored();
                default ->
                        null;
            };

            if (e == null) {
                return;
            }

            e.setParent(super.getParent());
            e.setPathing(super.getPathing());
            e.setReferencePathing(super.getReferencePathing());
            e.setPosition(super.getPosition());
            e.setPositionIndex(super.getPositionIndex());
            Platform.runLater(() -> {
                e.spawn();
                e.startMoving();
            });

            this.spawned++;

            return;
        } else if (this.spawned == 5) {
            this.spawned++;

            return;
        }

        this.spawned = -1;

        super.attacks.setHandleTime(super.coolDown);

        super.startMoving();
    }

    @Override
    protected void onDeath() {
        try {
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Zombie Death 1.wav");
            player.play();
        } catch (Exception ex) {
            System.out.println("Exception occurred");
        }
    }
}
