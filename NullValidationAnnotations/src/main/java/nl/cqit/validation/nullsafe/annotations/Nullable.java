package nl.cqit.validation.nullsafe.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that the annotated field, record component, method parameter, method return value or local variable
 * can contain a null value.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.RECORD_COMPONENT, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface Nullable {

    /**
     * When a method is overridden by a subclass, this will specify if the method in the subclass can 
     * narrow (@{@link Nullable} -> @{@link NotNull}) the method parameters. 
     * 
     * Narrowing is only applicable to method parameters and will be ignored on all other uses of the annotation.
     * 
     * Method return values can ALWAYS be narrowed, regardless of this value, similar to how parameter types can be narrowed.
     *
     * When an annotated class implements an interface or extends another class, it can always be narrowed.
     */
    boolean allowNarrowing() default false;
}
