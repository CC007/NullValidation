package nl.cqit.validation.nullsafe.example.packages.nullable;

import nl.cqit.validation.nullsafe.annotations.NotNull;

@NotNull
public enum SomeEnum {
    WITH_NULL(null, "nonNull"),
    WITHOUT_NULL("nullable", "nonNull");

    private final String nullable;
    
    @NotNull
    private final String notNull;
    SomeEnum(String nullable, @NotNull String notNull) {
        this.nullable = nullable;
        this.notNull = notNull;
    }

    public String getNullable() {
        return nullable;
    }

    @NotNull
    public String getNotNull() {
        return notNull;
    }
}
