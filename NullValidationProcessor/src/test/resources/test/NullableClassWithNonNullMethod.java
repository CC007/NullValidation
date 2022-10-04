package nl.cqit.validation.nullsafe.example.packages.nullable;

import nl.cqit.validation.nullsafe.annotations.NotNull;

public class NullableClassWithNonNullMethod {

    private String nullable;

    @NotNull
    private String notNull;

    public @NotNull String getNotNullFromField() {
        return notNull;
    }
}
