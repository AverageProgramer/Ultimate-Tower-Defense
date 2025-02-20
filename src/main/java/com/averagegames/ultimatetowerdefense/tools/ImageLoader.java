package com.averagegames.ultimatetowerdefense.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
}
