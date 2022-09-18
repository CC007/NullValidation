package nl.cqit.validation.nullsafe.example.packages.nullable;

import nl.cqit.validation.nullsafe.annotations.NotNull;

public class SomeNullableClass {

    private String nullable;

    @NotNull
    private String notNull;

    public SomeNullableClass(String nullable, String notNull) {
        this.nullable = nullable;
        this.notNull = notNull;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public void setNullableWithNotNull(@NotNull String notNull) {
        this.nullable = nullable;
    }

    public @NotNull String getNotNull() {
        return notNull;
    }

    public void setNotNull(@NotNull String notNull) {
        this.notNull = notNull;
    }
}
