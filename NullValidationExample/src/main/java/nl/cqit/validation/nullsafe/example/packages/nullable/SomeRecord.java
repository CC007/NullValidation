package nl.cqit.validation.nullsafe.example.packages.nullable;

import nl.cqit.validation.nullsafe.annotations.NotNull;

import java.util.Random;

public record SomeRecord(String nullable, @NotNull String notNull, @NotNull(allowWidening = true) String notNullWithGetter) {

    /**
     * To widen the return value, you have to use {@link NotNull#allowWidening()}
     */
    @Override
    public String notNullWithGetter() {
        Random random = new Random(System.nanoTime());
        return random.nextBoolean() ? notNullWithGetter : null;
    }

    /**
     * No {@link NotNull#allowWidening()} needed for this method, because it doesn't override another method.
     */
    public String notNullOrNullable() {
        Random random = new Random(System.nanoTime());
        return random.nextBoolean() ? notNull : nullable;
    }
}
