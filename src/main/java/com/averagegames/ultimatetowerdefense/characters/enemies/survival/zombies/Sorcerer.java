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
import javafx.scene.image.Image;
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

    private boolean first;

    public Sorcerer() {
        super.image = this.image;

        super.type = this.type;

        super.setHealth(this.startHealth);
        super.damage = this.damage;

        super.speed = this.speed;

        super.coolDown = this.coolDown;

        super.income = this.income;

        this.first = true;
    }

    @Override
    public void onSpawn() {
        super.startAttacking();
    }

    @Override
    public void attack(@Nullable final Tower tower) throws InterruptedException {
        if (first) {
            Thread.sleep(super.coolDown);

            first = false;
        }

        super.stopMoving();

        Thread.sleep(1500);

        Enemy[] enemies = new Enemy[5];

        for (int i = 0; i < 5; i++) {
            int enemy = (int) (Math.random() * ((GameScene.getWave() >= 20 ? 7 : 6) - 1)) + 1;

            switch (enemy) {
                case 1:
                    Normal normal = new Normal();

                    normal.setParent(super.getParent());
                    normal.setReferencePathing(GameScene.PATH);
                    normal.setPosition(super.getPosition());
                    normal.setPositionIndex(super.getPositionIndex());

                    enemies[i] = normal;

                    break;
                case 2:
                    Quick quick = new Quick();

                    quick.setParent(super.getParent());
                    quick.setReferencePathing(GameScene.PATH);
                    quick.setPosition(super.getPosition());
                    quick.setPositionIndex(super.getPositionIndex());

                    enemies[i] = quick;

                    break;
                case 3:
                    Slow slow = new Slow();

                    slow.setParent(super.getParent());
                    slow.setReferencePathing(GameScene.PATH);
                    slow.setPosition(super.getPosition());
                    slow.setPositionIndex(super.getPositionIndex());

                    enemies[i] = slow;

                    break;
                case 4:
                    Stealthy stealthy = new Stealthy();

                    stealthy.setParent(super.getParent());
                    stealthy.setReferencePathing(GameScene.PATH);
                    stealthy.setPosition(super.getPosition());
                    stealthy.setPositionIndex(super.getPositionIndex());

                    enemies[i] = stealthy;

                    break;
                case 5:
                    LootBox lootBox = new LootBox();

                    lootBox.setParent(super.getParent());
                    lootBox.setReferencePathing(GameScene.PATH);
                    lootBox.setPosition(super.getPosition());
                    lootBox.setPositionIndex(super.getPositionIndex());

                    enemies[i] = lootBox;

                    break;

                case 6:
                    Armored armored = new Armored();

                    armored.setParent(super.getParent());
                    armored.setReferencePathing(GameScene.PATH);
                    armored.setPosition(super.getPosition());
                    armored.setPositionIndex(super.getPositionIndex());

                    enemies[i] = armored;

                    break;
                default:
                    break;
            }
        }

        Spawner spawner = new Spawner(super.getPosition());

        spawner.setEnemyPathing(super.getPathing());
        spawner.setSpawnDelay(1500);

        assert super.getParent() != null;

        spawner.spawn(new Wave(enemies), super.getParent());

        Thread.sleep(1500);

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
