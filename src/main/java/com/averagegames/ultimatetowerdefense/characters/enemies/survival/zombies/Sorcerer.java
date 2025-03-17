package com.averagegames.ultimatetowerdefense.characters.enemies.survival.zombies;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.characters.enemies.Wave;
import com.averagegames.ultimatetowerdefense.characters.enemies.Zombie;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.maps.Spawner;
import com.averagegames.ultimatetowerdefense.scenes.GameScene;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.application.Platform;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

@Zombie
public class Sorcerer extends Enemy {

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/SorcererZombie.gif");

    @Property
    private final Type type = Type.REGULAR;

    @Property
    private final int startHealth = 135;

    @Property
    private final int damage = 1;

    @Property
    private final int speed = 20;

    @Property
    private final int income = 2;

    @Property
    private final int coolDown = 15000;

    @NotNull
    private Thread spawnThread;

    public Sorcerer() {
        super.image = this.image;

        super.type = this.type;

        super.setHealth(this.startHealth);
        super.damage = this.damage;

        super.speed = this.speed;

        super.income = this.income;

        spawnThread = new Thread(() -> {

        });
    }

    @Override
    public void onSpawn() {
        this.spawnThread = new Thread(() -> {
            spawning:

            while (true) {
                try {
                    Thread.sleep(this.coolDown);
                } catch (InterruptedException e) {
                    break;
                }

                super.stopMoving();

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    break;
                }

                Enemy[] enemies = new Enemy[5];

                for (int i = 0; i < 5; i++) {
                    int enemy = (int) (Math.random() * ((GameScene.getWave() >= 20 ? 7 : 6) - 1)) + 1;

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
                    Platform.runLater(e::spawn);
                    e.startMoving();

                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        break spawning;
                    }
                }

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    break;
                }

                super.startMoving();
            }
        });

        this.spawnThread.start();
    }

    @Override
    protected void onDeath() {
        this.spawnThread.interrupt();

        try {
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Zombie Death 1.wav");
            player.play();
        } catch (Exception ex) {
            System.out.println("Exception occurred");
        }
    }
}
