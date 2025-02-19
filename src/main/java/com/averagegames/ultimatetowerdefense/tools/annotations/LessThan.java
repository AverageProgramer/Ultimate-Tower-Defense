package com.averagegames.ultimatetowerdefense.tools.annotations;

import com.averagegames.ultimatetowerdefense.tools.annotations.verification.LessThanAnnotation;
import com.averagegames.ultimatetowerdefense.tools.exceptions.NumberAboveMaximumException;

import java.lang.annotation.*;

/**
 * The {@link LessThan} is meant to be annotated by a number.
 * If the annotated numberis greater than or equal to the given value, an {@link NumberAboveMaximumException} will be thrown.
 * @since Ultimate Tower Defense 1.0
 * @see Annotation
 * @see LessThanAnnotation
 * @see NumberAboveMaximumException
 * @author AverageProgramer
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Inherited
public @interface LessThan {

    /**
     * Gets the value that the annotated number must be less than.
     * @return the value that the number must be less than.
     * @since Ultimate Tower Defense 1.0
     */
    double value();
}
