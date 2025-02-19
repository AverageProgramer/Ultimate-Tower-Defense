package com.averagegames.ultimatetowerdefense.tools.annotations;

import com.averagegames.ultimatetowerdefense.tools.exceptions.NumberBelowMinimumException;
import com.averagegames.ultimatetowerdefense.tools.annotations.verification.GreaterThanAnnotation;

import java.lang.annotation.*;

/**
 * The {@link GreaterThan} is meant to be annotated by a number.
 * If the annotated number is less than or equal to the given value, a {@link NumberBelowMinimumException} will be thrown.
 * @since Ultimate Tower Defense 1.0
 * @see Annotation
 * @see GreaterThanAnnotation
 * @see NumberBelowMinimumException
 * @author AverageProgramer
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Inherited
public @interface GreaterThan {

    /**
     * Gets the value that the annotated number must be greater than.
     * @return the value that the number must be greater than.
     * @since Ultimate Tower Defense 1.0
     */
    double value();
}
