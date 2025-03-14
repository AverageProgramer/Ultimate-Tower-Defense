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
import org.jetbrains.annotations.Nullable;

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

                    switch (enemy) {
                        case 1:
                            Normal normal = new Normal();

                            normal.setParent(super.getParent());
                            normal.setPathing(super.getPathing());
                            normal.setReferencePathing(super.getReferencePathing());
                            normal.setPosition(super.getPosition());
                            normal.setPositionIndex(super.getPositionIndex());
                            Platform.runLater(normal::spawn);
                            normal.startMoving();

                            break;
                        case 2:
                            Quick quick = new Quick();

                            quick.setParent(super.getParent());
                            quick.setPathing(super.getPathing());
                            quick.setReferencePathing(super.getReferencePathing());
                            quick.setPosition(super.getPosition());
                            quick.setPositionIndex(super.getPositionIndex());
                            Platform.runLater(quick::spawn);
                            quick.startMoving();

                            break;
                        case 3:
                            Slow slow = new Slow();

                            slow.setParent(super.getParent());
                            slow.setPathing(super.getPathing());
                            slow.setReferencePathing(super.getReferencePathing());
                            slow.setPosition(super.getPosition());
                            slow.setPositionIndex(super.getPositionIndex());
                            Platform.runLater(slow::spawn);
                            slow.startMoving();

                            break;
                        case 4:
                            Stealthy stealthy = new Stealthy();

                            stealthy.setParent(super.getParent());
                            stealthy.setPathing(super.getPathing());
                            stealthy.setReferencePathing(super.getReferencePathing());
                            stealthy.setPosition(super.getPosition());
                            stealthy.setPositionIndex(super.getPositionIndex());
                            Platform.runLater(stealthy::spawn);
                            stealthy.startMoving();

                            break;
                        case 5:
                            LootBox lootBox = new LootBox();

                            lootBox.setParent(super.getParent());
                            lootBox.setPathing(super.getPathing());
                            lootBox.setReferencePathing(super.getReferencePathing());
                            lootBox.setPosition(super.getPosition());
                            lootBox.setPositionIndex(super.getPositionIndex());
                            Platform.runLater(lootBox::spawn);
                            lootBox.startMoving();

                            break;

                        case 6:
                            Armored armored = new Armored();

                            armored.setParent(super.getParent());
                            armored.setPathing(super.getPathing());
                            armored.setReferencePathing(super.getReferencePathing());
                            armored.setPosition(super.getPosition());
                            armored.setPositionIndex(super.getPositionIndex());
                            Platform.runLater(armored::spawn);
                            armored.startMoving();

                            break;
                        default:
                            break;
                    }

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
