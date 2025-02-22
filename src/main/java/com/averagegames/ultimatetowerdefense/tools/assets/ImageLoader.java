package com.averagegames.ultimatetowerdefense.tools.assets;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.tools.animation.TranslationHandler;
import org.jetbrains.annotations.NotNull;

import javafx.beans.DefaultProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.NoArgsConstructor;

/**
 * The {@link ImageLoader} class is meant to be an easy way to load and add any {@link Image} to a {@link Group} or {@link Scene}.
 * @since Ultimate Tower Defense 1.0
 * @see Image
 * @see ImageView
 * @author AverageProgramer
 */
@DefaultProperty("image")
@NoArgsConstructor
public class ImageLoader extends ImageView {

    /**
     * A constructor that loads an {@link Image} found at a given URL.
     * @param uri the path to the file.
     * @throws FileNotFoundException if the URL does not lead to a file.
     * @since Ultimate Tower Defense 1.0
     */
    @SuppressWarnings("unused")
    public ImageLoader(@NotNull final String uri) throws FileNotFoundException {
        
        // Initializes the image using a constructor that takes an image object as a parameter from the inherited super class.
        super(new Image(new FileInputStream(uri)));
    }

    /**
     * Gets the {@link ImageLoader}'s current {@code x} coordinate.
     * @return the {@link ImageLoader}'s current {@code x}.
     * @since Ultimate Tower Defense 1.0
     * @see Enemy
     * @see TranslationHandler
     */
    public final double getCurrentX() {

        // Returns the image's current x coordinate.
        return super.getX() + super.getTranslateX();
    }

    /**
     * Gets the {@link ImageLoader}'s current {@code y} coordinate.
     * @return the {@link ImageLoader}'s current {@code y}.
     * @since Ultimate Tower Defense 1.0
     * @see Enemy
     * @see TranslationHandler
     */
    public final double getCurrentY() {

        // Returns the image's current y coordinate.
        return super.getY() + super.getTranslateY();
    }
}
