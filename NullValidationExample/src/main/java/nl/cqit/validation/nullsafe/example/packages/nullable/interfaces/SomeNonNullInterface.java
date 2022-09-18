package nl.cqit.validation.nullsafe.example.packages.nullable.interfaces;

import nl.cqit.validation.nullsafe.annotations.NotNull;
import nl.cqit.validation.nullsafe.annotations.Nullable;

@NotNull
public interface SomeNonNullInterface {

    @NotNull String abstractGetNotNullValue(@NotNull String notNull);

    @NotNull(allowWidening = true) String abstractGetWidenableNotNullValue(@NotNull String notNull);

    String abstractGetNullableValue(String nullable);

    /**
     * Explicit @{@link Nullable} needed to specify {@link Nullable#allowNarrowing()}
     */
    String abstractGetNarrowableNullableValue(@Nullable(allowNarrowing = true) String nullable);
}
