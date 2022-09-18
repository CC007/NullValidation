package nl.cqit.validation.nullsafe.example;

import nl.cqit.validation.nullsafe.annotations.NotNullByDefault;

/**
 * This class and it's fields will be non-null unless otherwise specified, because the module specifies @{@link NotNullByDefault}
 */
public class SomeNotNullClassInModule {
    private String notNull;

    public SomeNotNullClassInModule(String notNull) {
        this.notNull = notNull;
    }

    public String getNotNull() {
        return notNull;
    }

    public void setNotNull(String notNull) {
        this.notNull = notNull;
    }
}
