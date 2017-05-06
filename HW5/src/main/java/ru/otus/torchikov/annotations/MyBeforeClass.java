package ru.otus.torchikov.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Torchikov Sergei on 06.05.2017.
 * It marks method which is executed before all tests in class.
 * Method must be public and returned type must be void.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyBeforeClass {
}
