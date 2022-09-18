package nl.cqit.validation.nullsafe.example.packages.nullable.abstractclasses;

import nl.cqit.validation.nullsafe.annotations.NotNull;
import nl.cqit.validation.nullsafe.annotations.Nullable;

import java.util.Random;

/**
 * This class will inherit the @{@link NotNull} annotation from its superclass.
 * 
 * Both abstract methods and implemented methods from the superclass support widening of return values 
 * and narrowing of method parameters if specified
 */
public class SomeExtendingClass extends SomeAbstractNonNullClass {

    /**
     * {@link NotNull} cannot be widened for return values by default for return values
     * {@link NotNull} can always be widened for method parameters
     */
    @Override
    public @NotNull String getNotNullValue(String nullable) {
        // return nullable;      COMPILER ERROR: nullable variable returned where non-null return value was specified
        return nullable != null ? nullable : "default value";
    }

    /**
     * {@link NotNull} can be widened for return values if the superclass specifies {@link NotNull#allowWidening()} to be true.
     * {@link NotNull} can always be widened for method parameters
     */
    @Override
    public String getWidenableNotNullValue(String nullable) {
        return nullable;
    }

    /**
     * (implicit) {@link Nullable} can always be narrowed for return values
     * (implicit) {@link Nullable} cannot be narrowed for method parameters by default
     */
    @Override
    public @NotNull String getNullableValue(String nullable) {
        // return nullable;      COMPILER ERROR: nullable variable returned where non-null return value was specified
        return nullable != null ? nullable : "default value";
    }

    /**
     * (implicit) {@link Nullable} can always be narrowed for return values
     * {@link Nullable} can be narrowed for method parameters if the superclass specifies {@link Nullable#allowNarrowing()} to be true.
     */
    @Override
    public @NotNull String getNarrowableNullableValue(@NotNull String notNull) {
        return notNull;
    }
}
