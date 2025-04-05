package com.averagegames.ultimatetowerdefense.util.development;

import org.intellij.lang.annotations.Identifier;

import java.lang.annotation.*;

/**
 * The {@link Constant} annotation is meant to be annotated by numerical {@code fields} within classes that represent a {@code constant}.
 * @since Ultimate Tower Defense 1.0
 * @author AverageProgramer
 */
@Documented
@Identifier
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface Constant {
    // This annotation does not do anything and merely serves as an identifier.
}
