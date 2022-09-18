package nl.cqit.validation.nullsafe.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that the annotated field, record component, method parameter, method return value or local variable
 * cannot contain a null value.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.RECORD_COMPONENT, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface NotNull {

    /**
     * When a method is overridden by a subclass, this will specify if the method in the subclass can 
     * widen (@{@link NotNull} -> @{@link Nullable}) the return value. 
     * 
     * When an annotated class implements an interface or extends another class, this will specify if the nullability 
     * of the class itself can be widened.
     *
     * Widening is only applicable to annotated return values of overriding methods and annotated classes that extend 
     * another class or implement an interface. It will be ignored on all other uses of the annotation.
     * 
     * Method parameters can ALWAYS be widened, regardless of this value, similar to how parameter types can be widened.
     */
    boolean allowWidening() default false;
}
