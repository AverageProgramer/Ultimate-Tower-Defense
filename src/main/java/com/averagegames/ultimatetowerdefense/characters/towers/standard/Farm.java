package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.towers.Bonus;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.maps.Position;
import com.averagegames.ultimatetowerdefense.player.Player;
import com.averagegames.ultimatetowerdefense.scenes.GameScene;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.application.Platform;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Bonus
public final class Farm extends Tower {

    /**
     * The {@link Farm}'s {@link Image}s per {@code level}.
     */
    @Property
    private final Image[] images = {
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/farm/FarmTower0.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/farm/FarmTower1.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/farm/FarmTower2.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/farm/FarmTower3.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/farm/FarmTower4.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/farm/FarmTower5.gif"),
    };

    /**
     * The {@link Farm}'s placement {@code cost}.
     */
    @Property
    public final int placementCost = 250;

    /**
     * The {@link Farm}'s placement {@code limit}.
     */
    @Property
    public final int placementLimit = 8;

    /**
     * The {@link Farm}'s {@code upgrade} costs per {@code level}.
     */
    @Property
    private final int[] upgradeCosts = {200, 550, 1000, 2500, 5000};

    /**
     * The {@link Farm}'s cash {@code bonuses} per {@code level}.
     */
    @Property(unique = true)
    private final int[] cashBonuses = {50, 100, 250, 500, 750, 1500};

    public Farm() {
        super.images = this.images;
        super.placementCost = this.placementCost;
        super.placementLimit = this.placementLimit;
        super.upgradeCosts = this.upgradeCosts;
        super.setHealth(100);
    }

    @Override
    public void setPosition(@NotNull final Position position) {

        this.getLoadedTower().setX(position.x() - (this.images[super.getLevel()] != null ? Objects.requireNonNull(this.images[super.getLevel()]).getWidth() / 2 : 0));
        this.getLoadedTower().setY(position.y() - (this.images[super.getLevel()] != null ? Objects.requireNonNull(this.images[super.getLevel()]).getHeight() / 2 : 0));
    }

    @Override
    public @NotNull Position getPosition() {
        return new Position(super.getLoadedTower().getCurrentX() + (this.images[super.getLevel()] != null ? Objects.requireNonNull(this.images[super.getLevel()]).getWidth() / 2 : 0), super.getLoadedTower().getCurrentY() + (this.images[super.getLevel()] != null ? Objects.requireNonNull(this.images[super.getLevel()]).getHeight() / 2 : 0));
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

        // Adds a cash bonuses to the player's current cash.
        Player.cash += this.cashBonuses[super.getLevel()];

        // Updates the text displaying the player's current cash to now include the bonus.
        Platform.runLater(() -> GameScene.CASH_TEXT.setText(STR."$\{Player.cash}"));

        // A try-catch statement that will allow an audio player to play an audio file.
        try {

            // Creates a new audio player that will play an audio file.
            AudioPlayer effectPlayer = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Farm Income 1.wav");

            // Plays the audio file.
            effectPlayer.play();
        } catch (Exception ex) {
            // The exception does not need to be handled.
        }
    }
}
