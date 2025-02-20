package com.averagegames.ultimatetowerdefense.characters.towers;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.world.maps.elements.Position;
import com.averagegames.ultimatetowerdefense.tools.ImageLoader;
import com.averagegames.ultimatetowerdefense.characters.towers.util.Targeting;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

import static com.averagegames.ultimatetowerdefense.world.data.Towers.LIST_OF_ACTIVE_TOWERS;

public abstract class Tower {

    /**
     * The {@link Tower}'s parent {@link Group}.
     */
    private Group parent;

    /**
     * The {@link Tower}'s {@link Image} loaded using an {@link ImageLoader}.
     */
    private final ImageLoader loadedTower;

    /**
     * The {@link Tower}'s {@link Image}.
     */
    @NotNull
    protected Image image;

    /**
     * The {@link Tower}'s {@link Targeting}.
     */
    @NotNull
    protected Targeting targeting;

    /**
     * The {@link Tower}'s current {@code health}.
     */
    protected int health;

    /**
     * The {@code damage} the {@link Tower} can do during an {@code attack}.
     */
    protected int damage;

    /**
     * Whether the {@link Tower} can detect a {@code hidden} {@link Enemy}.
     */
    protected boolean hiddenDetection;

    /**
     * Whether the {@link Tower} can detect a {@code flying} {@link Enemy}.
     */
    protected boolean flyingDetection;

    /**
     * The {@link Tower}'s cooldown in milliseconds between {@code attacks}.
     */
    protected int cooldown;

    /**
     * The {@link Tower}'s {@code level}.
     */
    protected int level;

    /**
     * A {@link Thread} that is responsible for handling all {@link Tower} {@code attacks}.
     */
    private Thread attackThread;
    
    {

        this.loadedTower = new ImageLoader();

        this.image = new Image(InputStream.nullInputStream());

        this.targeting = Targeting.FIRST;

        // Initializes the tower's level so that it starts at 1 and not 0.
        this.level = 1;
    }

    @Deprecated
    public final void setHealth(final int health) {
        this.health = health;
    }

    public final void heal(final int amount) {
        this.health += amount;
    }

    public final void damage(final int amount) {
        this.health -= amount;
    }


    public final int getHealth() {
        return this.health;
    }

    public final void setPosition(@NotNull final Position position) {
        this.loadedTower.setX(position.x());
        this.loadedTower.setY(position.y());
    }

    @SuppressWarnings("unused")
    @Contract(" -> new")
    public final @NotNull Position getPosition() {
        return new Position(this.loadedTower.getX() + this.loadedTower.getTranslateX(), this.loadedTower.getY() + this.loadedTower.getTranslateY());
    }

    public final boolean intersects(@NotNull final Node node) {
        return this.loadedTower.intersects(node.getLayoutBounds());
    }

    public final void place(@NotNull final Group parent) {
        this.onPlace();

        LIST_OF_ACTIVE_TOWERS.add(this);

        parent.getChildren().add(this.loadedTower);

        this.parent = parent;
    }

    private boolean canAttack(final Enemy enemy) {
        boolean allowed = false;

        switch (enemy.getType()) {
            case REGULAR:
                allowed = true;

            case HIDDEN:
                if (this.hiddenDetection) {
                    allowed = true;
                }

            case FLYING:
                if (this.flyingDetection) {
                    allowed = true;
                }

            default:
                break;
        }

        return allowed;
    }

    protected final void startAttacking() {
        this.attackThread = new Thread(() -> {
            if (this.canAttack(null)) {
                // TODO: Implement attacks
            }
        });

        this.attackThread.start();
    }

    public synchronized final void eliminate() {

        this.onDeath();

        this.parent.getChildren().remove(this.loadedTower);

        this.attackThread.interrupt();

        LIST_OF_ACTIVE_TOWERS.remove(this);
    }

    protected void onPlace() {
        // This method can be overridden by a subclass so each individual tower can have unique action to do when placed.
    }

    protected void onHeal() {
        // This method can be overridden by a subclass so each individual tower can have unique action to do when healed.
    }

    protected void onDamage() {
        // This method can be overridden by a subclass so each individual tower can have unique action to do when damaged.
    }

    protected void onDeath() {
        // This method can be overridden by a subclass so each individual tower can have unique action to do when eliminated.
    }

    public abstract void upgrade() throws InterruptedException;

    protected abstract void attack(@NotNull final Enemy enemy) throws InterruptedException;

    public void special() throws InterruptedException {
        // This method can be overridden by a subclass so that each individual tower can have a unique special ability.
    }
}
