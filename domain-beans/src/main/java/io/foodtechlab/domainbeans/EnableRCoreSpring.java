package io.foodtechlab.domainbeans;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Включает интеграцию домена RCORE со спрингом
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({RCoreRegistar.class, AutoEventRegisterer.class})
public @interface EnableRCoreSpring {

    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    Class<?>[] anotherDomainBeans() default {};
}
