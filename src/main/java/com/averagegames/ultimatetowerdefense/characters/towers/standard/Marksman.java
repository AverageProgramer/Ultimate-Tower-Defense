package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import com.averagegames.ultimatetowerdefense.util.development.Specific;
import com.averagegames.ultimatetowerdefense.util.development.SpecificAnnotation;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

public class Marksman extends Tower {

    public static final int COST = 300;

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/MarksmanTower.gif");

    @Property
    private final int damage = 4;

    @Property
    private final int coolDown = 5000;

    public Marksman() {
        super.image = this.image;
        super.damage = this.damage;
        super.coolDown = this.coolDown;
    }

    @Override
    @Specific(value = Tower.class, subclasses = true)
    protected void attack(@NotNull final Enemy enemy) throws InterruptedException {
        SpecificAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());

        try {
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Gunshot 2.wav");
            player.play();
        } catch (Exception ex) {
            System.out.println("Exception occurred");
        }

        enemy.damage(this.damage);
    }
}
