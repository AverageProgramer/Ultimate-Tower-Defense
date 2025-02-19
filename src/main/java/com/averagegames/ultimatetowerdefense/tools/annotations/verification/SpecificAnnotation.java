package com.averagegames.ultimatetowerdefense.tools.annotations.verification;

import com.averagegames.ultimatetowerdefense.tools.annotations.Specific;
import com.averagegames.ultimatetowerdefense.tools.exceptions.UnspecifiedAccessException;
import com.averagegames.ultimatetowerdefense.tools.annotations.NotInstantiable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;

/**
 * The {@link SpecificAnnotation} class serves as a way to verify any method annotated by the {@link Specific} annotation is not called by non-specified classes.
 * @since Ultimate Tower Defense 1.0
 * @see Specific
 * @see UnspecifiedAccessException
 * @author AverageProgramer
 */
@NotInstantiable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SpecificAnnotation {

    /**
     * Verifies that the calling class of a method annotated by the {@link Specific} annotation was given access to use the method.
     * @param caller the method's calling {@link Class}.
     * @param annotated the annotated {@link Method}.
     * @since Ultimate Tower Defense 1.0
     * @see Specific
     * @see UnspecifiedAccessException
     */
    public static void verify(@NotNull final Class<?> caller, @NotNull final Method annotated) {

        // Asserts that the method should be annotated.
        // The reason assertions are being used here is that this expression will always evaluate to true.
        assert annotated.isAnnotationPresent(Specific.class) : STR."Method \{annotated.getName()} in class \{annotated.getDeclaringClass().getName()} is not annotated with \{Specific.class.getName()}";

        // A boolean value that allows for determining whether the method call was valid.
        // This value is initially false but will end up as true if the method call was ultimately valid.
        boolean allowed = false;

        // A label that will allow for labeled-break statements.
        verification:

        // A loop that will iterate through each class specified by the method's annotation.
        for (var specified : annotated.getAnnotation(Specific.class).value()) {

            // Determines whether the method's caller is equal to one of the specified classes to have access to the method.
            if (caller == specified) {

                // Sets the value for determining whether the method call was allowed to true.
                allowed = true;

                // Breaks out of the loop.
                break;
            }

            // Determines whether subclasses should be allowed.
            if (!annotated.getAnnotation(Specific.class).subclasses()) {

                // Jumps to the next iteration of the loop.
                continue;
            }

            // An inner loop that iterates through each subclass of the current class in the enclosing loop.
            for (var subclass : new Reflections(new ConfigurationBuilder().addScanners(Scanners.SubTypes).addUrls(ClasspathHelper.forClass(specified))).getSubTypesOf(specified)) {

                // Determines whether the method's caller is equal to one of the specified class' subclasses to have access to the method.
                if (caller == subclass) {

                    // Sets the value for determining whether the method call was allowed to true.
                    allowed = true;

                    // Breaks out of both the outer and inner loops.
                    break verification;
                }
            }
        }

        // Determines if the caller was specified to have access by the method.
        if (!allowed) {

            // Throws an exception which causes the program to finish.
            throw new UnspecifiedAccessException(STR."Class [\{caller.getName()}] does not have specified access to method [\{annotated.getName()}]");
        }
    }
}
