package com.averagegames.ultimatetowerdefense.characters.enemies.survival.zombies;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.characters.enemies.Zombie;
import com.averagegames.ultimatetowerdefense.maps.Path;
import com.averagegames.ultimatetowerdefense.maps.Position;
import com.averagegames.ultimatetowerdefense.scenes.GameScene;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Objects;

@Zombie
public class LootBox extends Enemy {

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/LootBoxZombie.gif");

    @Property
    private final Type type = Type.REGULAR;

    @Property
    private final int startHealth = 30;

    @Property
    private final int damage = 1;

    @Property
    private final int speed = 30;

    @Property
    private final int income = 0;

    public LootBox() {
        super.image = this.image;

        super.type = this.type;

        super.setHealth(this.startHealth);
        super.damage = this.damage;

        super.speed = this.speed;

        super.income = this.income;
    }

    @Override
    protected void onDeath() {
        try {
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Zombie Death 4.wav");
            player.play();
        } catch (Exception ex) {
            System.out.println("Exception occurred");
        }

        ArrayList<Position> positions = new ArrayList<>();

        for (int i = 0; i < Objects.requireNonNull(this.getReferencePathing()).positions().length; i++) {
            if (i < this.getPositionIndex()) {
                continue;
            }
            positions.add(this.getReferencePathing().positions()[i]);
        }

        Path path = new Path(positions.toArray(Position[]::new));

        int enemy = (int) (Math.random() * ((GameScene.getWave() >= 20 ? 6 : 5) - 1)) + 1;

        Enemy e = null;

        switch (enemy) {
            case 1:
                Normal normal = new Normal();

                normal.setParent(this.getParent());
                normal.setPathing(path);
                normal.setReferencePathing(GameScene.PATH);
                normal.setPositionIndex(this.getPositionIndex());
                normal.spawn(this.getPosition());
                normal.startMoving();

                break;
            case 2:
                Quick quick = new Quick();

                quick.setParent(this.getParent());
                quick.setPathing(path);
                quick.setReferencePathing(GameScene.PATH);
                quick.setPositionIndex(this.getPositionIndex());
                quick.spawn(this.getPosition());
                quick.startMoving();

                break;
            case 3:
                Slow slow = new Slow();

                slow.setParent(this.getParent());
                slow.setPathing(path);
                slow.setReferencePathing(GameScene.PATH);
                slow.setPositionIndex(this.getPositionIndex());
                slow.spawn(this.getPosition());
                slow.startMoving();

                break;
            case 4:
                Stealthy stealthy = new Stealthy();

                stealthy.setParent(this.getParent());
                stealthy.setPathing(path);
                stealthy.setReferencePathing(GameScene.PATH);
                stealthy.setPositionIndex(this.getPositionIndex());
                stealthy.spawn(this.getPosition());
                stealthy.startMoving();

                break;

            case 5:
                Armored armored = new Armored();

                armored.setParent(this.getParent());
                armored.setPathing(path);
                armored.setReferencePathing(GameScene.PATH);
                armored.setPositionIndex(this.getPositionIndex());
                armored.spawn(this.getPosition());
                armored.startMoving();

                break;
            default:
                break;
        }
    }
}
