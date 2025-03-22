package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Bonus;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.maps.Base;
import com.averagegames.ultimatetowerdefense.maps.Position;
import com.averagegames.ultimatetowerdefense.scenes.game.GameScene;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.application.Platform;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Nullable;

@Bonus
public final class Medic extends Tower {

    /**
     * The {@link Medic}'s {@link Image}s per {@code level}.
     */
    @Property
    private final Image[] images = {
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/medic/MedicTower0.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/medic/MedicTower1.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/medic/MedicTower2.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/medic/MedicTower3.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/medic/MedicTower4.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/medic/MedicTower4.gif"),
    };

    /**
     * The {@link Medic}'s placement {@code cost}.
     */
    @Property
    public final int placementCost = 1500;

    /**
     * The {@link Medic}'s {@code upgrade} costs per {@code level}.
     */
    @Property
    private final int[] upgradeCosts = {200, 400, 1750, 3250, 6500};

    /**
     * The {@link Medic}'s tower health {@code bonus} for {@code level 5}.
     */
    @Property(unique = true)
    private final int towerBonus = 25;

    /**
     * The {@link Medic}'s base health {@code bonuses} per {@code level}.
     */
    @Property(unique = true)
    private final int[] baseBonuses = {1, 1, 1, 2, 3, 5};

    public Medic() {
        super.images = this.images;
        super.placementCost = this.placementCost;
        super.upgradeCosts = this.upgradeCosts;
        super.setHealth(100);
    }

    @Override
    protected void attack(@Nullable final Enemy enemy) {

    }

    @Override
    public void special() {

    }

    @Override
    public void upgrade() throws InterruptedException {
        Position oldPos = super.getPosition();

        if (super.getLevel() < 5) {
            super.setLevel(super.getLevel() + 1);
        }

        super.getLoadedTower().setImage(super.images[super.getLevel()]);

        super.setPosition(oldPos);
    }

    public void bonus() {

        // Updates the text displaying the player's current cash to now include the bonus.
        Platform.runLater(() -> GameScene.HEALTH_TEXT.setText(STR."\{(Base.health += this.baseBonuses[super.getLevel()])} HP"));
    }
}
