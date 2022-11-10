package nl.cqit.validation.nullsafe.processor.internal.nullstate;

import nl.cqit.validation.nullsafe.annotations.NotNull;

public enum NullState {
    NULL,
    NULLABLE,
    NOT_NULL;

    /**
     * Determine the null state after a branch merge, where a and b specify the state in/after either branch
     * 
     * @param a the null state in/after the first branch
     * @param b the null state in/after the second branch
     * @return the overall null state after the branches merge again
     */
    public static NullState or(@NotNull NullState a, @NotNull NullState b) {        
        // if both have the same state, the result is that state.
        if (a == b) {
            return a;
        }
        
        // if one of the 2 is NULLABLE, then the result is NULLABLE
        // if one is NULL and the other is NOT_NULL, then the result is NULLABLE
        return NULLABLE;
    } 
    
    public NullState or(@NotNull NullState b) {
        return NullState.or(this, b);
    }
}
