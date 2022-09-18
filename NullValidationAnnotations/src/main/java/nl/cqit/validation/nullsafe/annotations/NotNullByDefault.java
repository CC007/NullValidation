package nl.cqit.validation.nullsafe.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that field, record component, method parameter, method return value or local variable are non-null by default.
 * This annotation can be used on a class, a package or a whole module.
 *
 * When used on a class, this annotation overrides any @{@link NullableByDefault} on the package or module level for this class.
 * When used on a package, this annotation overrides any @{@link NullableByDefault} on the super-package or module level of this package.
 * Modules are implicitly marked @{@link NullableByDefault}
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.MODULE, ElementType.PACKAGE, ElementType.TYPE})
public @interface NotNullByDefault {

    /**
     * Specify if this annotation should apply to all sub-packages.
     */
    boolean applyToSubPackages() default true;
}
