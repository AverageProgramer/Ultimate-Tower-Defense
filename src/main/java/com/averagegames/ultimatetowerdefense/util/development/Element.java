package com.averagegames.ultimatetowerdefense.util.development;

import org.intellij.lang.annotations.Identifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Identifier
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface Element {
}
