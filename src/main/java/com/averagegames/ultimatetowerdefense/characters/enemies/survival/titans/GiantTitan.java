package com.averagegames.ultimatetowerdefense.characters.enemies.survival.titans;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Titan;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import com.averagegames.ultimatetowerdefense.util.development.Specific;
import com.averagegames.ultimatetowerdefense.util.development.SpecificAnnotation;
import javafx.scene.image.Image;

@Titan
public class GiantTitan extends Enemy {

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/GiantTitan.gif");

    @Property
    private final Type type = Type.REGULAR;

    @Property
    private final int startHealth = 1750;

    @Property
    private final int damage = 1;

    @Property
    private final int speed = 8;

    @Property
    private final int income = 1;

    public GiantTitan() {
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

        try {
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Zombie Death 6.wav");
            player.play();
        } catch (Exception ex) {
            System.out.println("Exception occurred");
        }
    }
}
