package com.averagegames.ultimatetowerdefense.tools.development;

import java.lang.reflect.Method;

import org.jetbrains.annotations.NotNull;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

/**
 * The {@link ProhibitedAnnotation} class serves as a way to verify any method annotated by the {@link Prohibited} annotation is not called by prohibited classes.
 * @since Ultimate Tower Defense 1.0
 * @see Prohibited
 * @see ProhibitedAccessException
 * @author AverageProgramer
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProhibitedAnnotation {

    /**
     * Verifies that the calling class of a method annotated by the {@link Prohibited} annotation was not prohibited from calling the method.
     * @param caller the method's calling {@link Class}.
     * @param annotated the annotated {@link Method}.
     * @since Ultimate Tower Defense 1.0
     * @see Prohibited
     * @see ProhibitedAccessException
     */
    public static void verify(@NotNull final Class<?> caller, @NotNull final Method annotated) {

        // Asserts that the method should be annotated.
        // The reason this is being used here is that this expression should always evaluate to true.
        assert annotated.isAnnotationPresent(Prohibited.class) : STR."Method \{annotated.getName()} in class \{annotated.getDeclaringClass().getName()} is not annotated with \{Prohibited.class.getName()}";

        // A boolean value that allows for determining whether the method call was valid.
        // This value is initially true but will end up as false if the method call was ultimately prohibited.
        boolean allowed = true;

        // A label that will allow for labeled-break statements.
        verification:

        // A loop that will iterate through each class specified by the method's annotation.
        for (Class<?> prohibited : annotated.getAnnotation(Prohibited.class).value()) {

            // Determines whether the method's caller is equal to one of the specified classes to have access to the method.
            if (caller == prohibited) {

                // Sets the value for determining whether the method call was allowed to true.
                allowed = false;

                // Breaks out of the loop.
                break;
            }

            // Determines whether subclasses should be allowed.
            if (!annotated.getAnnotation(Prohibited.class).subclasses()) {

                // Jumps to the next iteration of the loop.
                continue;
            }

            // An inner loop that iterates through each subclass of the current class in the enclosing loop.
            for (Class<?> subclass : new Reflections(new ConfigurationBuilder().addScanners(Scanners.SubTypes).addUrls(ClasspathHelper.forClass(prohibited))).getSubTypesOf(prohibited)) {

                // Determines whether the method's caller is equal to one of the specified class' subclasses to have access to the method.
                if (caller == subclass) {

                    // Sets the value for determining whether the method call was allowed to true.
                    allowed = false;

                    // Breaks out of both the inner and outer loops.
                    break verification;
                }
            }
        }

        // Determines if caller was prohibited from having access by the method.
        if (!allowed) {

            // Throws an exception which causes the program to finish.
            throw new ProhibitedAccessException(STR."Class [\{caller.getName()}] is prohibited from accessing method [\{annotated.getName()}]");
        }
    }
}