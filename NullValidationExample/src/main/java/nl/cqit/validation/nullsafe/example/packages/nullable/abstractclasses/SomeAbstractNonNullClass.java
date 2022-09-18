package nl.cqit.validation.nullsafe.example.packages.nullable.abstractclasses;

import nl.cqit.validation.nullsafe.annotations.NotNull;
import nl.cqit.validation.nullsafe.annotations.Nullable;

@NotNull
public abstract class SomeAbstractNonNullClass {
    
    public abstract @NotNull String getNotNullValue(@NotNull String notNull);
    
    public abstract @NotNull(allowWidening = true) String getWidenableNotNullValue(@NotNull String notNull);
    
    public abstract String getNullableValue(String nullable);

    /**
     * Explicit @{@link Nullable} needed to specify {@link Nullable#allowNarrowing()}
     */
    public abstract String getNarrowableNullableValue(@Nullable(allowNarrowing = true) String nullable);
}
