package test;

import nl.cqit.validation.nullsafe.annotations.NotNull;
import test.subpackage.ClassWithMethod;

public class NullableClassWithNonNullMethod {

    private String nullable;

    @NotNull
    private String notNull;

    public @NotNull String getNotNullFromField() {
        var foo = "hello";
        foo = getHello("foobar");
        var bar = ClassWithMethod.getHelloStatic("foobar");
        return notNull;
    }
    
    private @NotNull String getHello(String name) {
        return "Hello " + name + this.nullable;
    }
}
