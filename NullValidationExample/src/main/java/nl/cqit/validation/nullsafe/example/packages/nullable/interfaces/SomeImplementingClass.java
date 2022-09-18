package nl.cqit.validation.nullsafe.example.packages.nullable.interfaces;

import nl.cqit.validation.nullsafe.annotations.NotNull;
import nl.cqit.validation.nullsafe.annotations.Nullable;

public class SomeImplementingClass implements SomeNonNullInterface {

    /**
     * {@link NotNull} cannot be widened for return values by default for return values
     * {@link NotNull} can always be widened for method parameters
     */
    @Override
    public @NotNull String abstractGetNotNullValue(String nullable) {
        // return nullable;      COMPILER ERROR: nullable variable returned where non-null return value was specified
        return nullable != null ? nullable : "default value";
    }

    /**
     * {@link NotNull} can be widened for return values if the superclass specifies {@link NotNull#allowWidening()} to be true.
     * {@link NotNull} can always be widened for method parameters
     */
    @Override
    public String abstractGetWidenableNotNullValue(String nullable) {
        return nullable;
    }

    /**
     * (implicit) {@link Nullable} can always be narrowed for return values
     * (implicit) {@link Nullable} cannot be narrowed for method parameters by default
     */
    @Override
    public @NotNull String abstractGetNullableValue(String nullable) {
        // return nullable;      COMPILER ERROR: nullable variable returned where non-null return value was specified
        return nullable != null ? nullable : "default value";
    }

    /**
     * (implicit) {@link Nullable} can always be narrowed for return values
     * {@link Nullable} can be narrowed for method parameters if the superclass specifies {@link Nullable#allowNarrowing()} to be true.
     */
    @Override
    public @NotNull String abstractGetNarrowableNullableValue(@NotNull String notNull) {
        return notNull;
    }
}
