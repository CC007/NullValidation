package nl.cqit.validation.nullsafe.example.packages.nullable;

import nl.cqit.validation.nullsafe.annotations.NotNullByDefault;
import nl.cqit.validation.nullsafe.annotations.Nullable;

@NotNullByDefault
public class SomeDefaultNotNullClass {
    
    @Nullable
    private String nullable;

    private String notNull;

    public SomeDefaultNotNullClass(String nullable, String notNull) {
        this.nullable = nullable;
        this.notNull = notNull;
    }

    public @Nullable String getNullable() {
        return nullable;
    }

    public void setNullable(@Nullable String nullable) {
        this.nullable = nullable;
    }
    
    public void setNullableWithNotNull(String notNull) {
        this.nullable = nullable;
    }

    public String getNotNull() {
        return notNull;
    }

    public void setNotNull(String notNull) {
        this.notNull = notNull;
    }
}
